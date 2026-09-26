package org.forestwizard.cities.game;

import org.forestwizard.cities.forms.MoveResult;
import org.forestwizard.cities.forms.MoveResultType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameService {
    private static final String USER_GIVE_UP_ANSWER = "здаюсь";
    private static final Set<Character> invalidNameEndings = Set.of('ь', 'й');
    private final CityRepository cityRepository;
    private Set<String> enteredCities;
    private String lastComputerAnswer;
    private int playerScore;
    private int computerScore;

    public GameService(CityRepository repository) {
        this.cityRepository = repository;
        reset();
    }

    public void reset() {
        enteredCities = new HashSet<>();
        lastComputerAnswer = null;
        playerScore = 0;
        computerScore = 0;
    }

    public MoveResult doTurn(String text) {
        if (text == null || normalizeCityName(text).isEmpty()) {
            return new MoveResult(MoveResultType.CONTINUE, "Комп'ютер: Будь-ласка, введи назву міста");
        }

        String finalText = normalizeCityName(text);
        if (finalText.equalsIgnoreCase(USER_GIVE_UP_ANSWER)) {
            return new MoveResult(MoveResultType.GAME_OVER, "Гру закінчено!", playerScore, computerScore);
        }

        Character nameBegin = getLastValidChar(finalText);
        if (nameBegin == null) {
            return new MoveResult(MoveResultType.CONTINUE, "Комп'ютер: Введи допустиму назву міста");
        }

        if (enteredCities.stream().anyMatch(item -> item.equalsIgnoreCase(finalText))) {
            return new MoveResult(MoveResultType.CONTINUE, "Комп'ютер: Це ім'я вже використане");
        }

        if (lastComputerAnswer != null) {
            Character answerNameBegin = getLastValidChar(lastComputerAnswer);
            if (answerNameBegin != null && finalText.charAt(0) != answerNameBegin) {
                return new MoveResult(
                        MoveResultType.CONTINUE,
                        "Комп'ютер: Введи назву на останню літеру відповіді");
            }
        }

        if (cityRepository.getAll().stream()
                .map(this::normalizeCityName)
                .noneMatch(item -> item.equalsIgnoreCase(finalText))
        ) {
            return new MoveResult(MoveResultType.CONTINUE, "Комп'ютер: Я не знаю таку назву міста");
        }

        playerScore++;
        Optional<String> answerOptional = cityRepository.getAll().stream()
                .map(this::normalizeCityName)
                .filter(str -> str.startsWith(String.valueOf(nameBegin))
                        && !enteredCities.contains(str)
                        && !str.equals(finalText)
                ).findFirst();

        if (answerOptional.isPresent()) {
            String answer = answerOptional.get();
            enteredCities.add(finalText);
            enteredCities.add(answer);
            lastComputerAnswer = answer;
            computerScore++;
            return new MoveResult(MoveResultType.CONTINUE, "Комп'ютер: " + answer);
        } else {
            return new MoveResult(MoveResultType.WIN, "Комп'ютер: Здаюсь!", playerScore, computerScore);
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

    private String normalizeCityName(String text) {
        return text.trim().toLowerCase().replaceAll("[^a-zа-яєії]", "");
    }
}
