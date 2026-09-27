package org.forestwizard.cities.game;

import org.forestwizard.cities.utils.CityNameNormalizer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameService {
    private static final String NAME_EMPTY_OR_NULL_TEXT = "Комп'ютер: Будь-ласка, введи назву міста";
    private static final String NAME_INVALID_TEXT = "Комп'ютер: Введи допустиму назву міста";
    private static final String NAME_UNKNOWN_TEXT = "Комп'ютер: Я не знаю таку назву міста";
    private static final String NAME_ALREADY_USED_TEXT = "Комп'ютер: Це ім'я вже використане";
    private static final String COMPUTER_GIVE_UP_TEXT = "Комп'ютер: Здаюсь!";
    private static final String NAME_NOT_MATCHES_LAST_LETTER = "Комп'ютер: Введи місто на літеру: ";
    private static final String COMPUTER_ANSWER_TEXT = "Комп'ютер: ";
    private static final String GAME_OVER_TEXT = "Гру закінчено!";
    private static final String USER_GIVE_UP_ANSWER_TEXT = "здаюсь";
    private static final Set<Character> invalidNameEndings = Set.of('ь', 'й');
    private final CityRepository cityRepository;
    private GameStatus status;
    private Set<String> enteredCities;
    private String lastComputerAnswer;
    private int playerScore;
    private int computerScore;

    public GameService(CityRepository repository) {
        this.cityRepository = repository;
        reset();
    }

    public void reset() {
        status = GameStatus.IN_PROGRESS;
        enteredCities = new HashSet<>();
        lastComputerAnswer = null;
        playerScore = 0;
        computerScore = 0;
    }

    public MoveResult doTurn(String text) {
        if (status != GameStatus.IN_PROGRESS) {
            return null;
        }

        if (text == null) {
            return new MoveResult(status, NAME_EMPTY_OR_NULL_TEXT);
        }

        String finalText = CityNameNormalizer.normalizeName(text);
        if (finalText.isEmpty()) {
            return new MoveResult(status, NAME_EMPTY_OR_NULL_TEXT);
        }

        if (finalText.equalsIgnoreCase(USER_GIVE_UP_ANSWER_TEXT)) {
            status = GameStatus.COMPUTER_WON;
            return new MoveResult(status, GAME_OVER_TEXT, playerScore, computerScore);
        }

        Character nameBegin = getLastValidChar(finalText);
        if (nameBegin == null) {
            return new MoveResult(status, NAME_INVALID_TEXT);
        }

        if (enteredCities.stream().anyMatch(item -> item.equalsIgnoreCase(finalText))) {
            return new MoveResult(status, NAME_ALREADY_USED_TEXT);
        }

        if (lastComputerAnswer != null) {
            Character answerNameBegin = getLastValidChar(lastComputerAnswer);
            if (answerNameBegin != null && finalText.charAt(0) != answerNameBegin) {
                return new MoveResult(status, NAME_NOT_MATCHES_LAST_LETTER + Character.toUpperCase(answerNameBegin));
            }
        }

        if (cityRepository.getAll().stream().noneMatch(item -> item.equalsIgnoreCase(finalText))) {
            return new MoveResult(status, NAME_UNKNOWN_TEXT);
        }

        playerScore++;
        enteredCities.add(finalText);
        Optional<String> answerOptional = cityRepository.getAll().stream()
                .filter(str -> str.startsWith(String.valueOf(nameBegin))
                        && !enteredCities.contains(str)
                        && !str.equals(finalText)
                ).findFirst();

        if (answerOptional.isPresent()) {
            String answer = answerOptional.get();
            enteredCities.add(answer);
            lastComputerAnswer = answer;
            computerScore++;
            return new MoveResult(status, COMPUTER_ANSWER_TEXT + answer);
        } else {
            status = GameStatus.PLAYER_WON;
            return new MoveResult(status, COMPUTER_GIVE_UP_TEXT, playerScore, computerScore);
        }
    }

    private Character getLastValidChar(String text) {
        int index = text.length() - 1;
        while (index >= 0) {
            char c = Character.toLowerCase(text.charAt(index));
            if (Character.isAlphabetic(c) && !invalidNameEndings.contains(c)) {
                return c;
            }
            index--;
        }
        return null;
    }
}
