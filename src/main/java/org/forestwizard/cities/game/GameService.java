package org.forestwizard.cities.game;

import org.forestwizard.cities.forms.DialogForm;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameService {
    private static final String WIN_MESSAGE_FORMAT = "Гру закінчено!\nТвій рекорд: %s \nРекорд комп'ютера: %s";
    private static final String GAME_OVER_MESSAGE_FORMAT = "Ти переміг!\nТвій рекорд: %s\nРекорд комп'ютера: %s";
    private static final String USER_GIVE_UP_ANSWER = "здаюсь";
    private static final Set<Character> invalidNameEndings = Set.of('Ь', 'Й');
    private final CityRepository cityRepository;
    private final Set<String> enteredCities;
    private boolean isEnabled;
    private String lastComputerAnswer;
    private int playerScore;
    private int computerScore;

    public GameService() {
        this.cityRepository = new CityRepository();
        this.enteredCities = new HashSet<>();
        this.isEnabled = true;
        this.lastComputerAnswer = null;
        this.playerScore = 0;
        this.computerScore = 0;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String doTurn(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Комп'ютер: Будь-ласка, введи назву міста";
        }

        text = text.trim();
        if (text.equalsIgnoreCase(USER_GIVE_UP_ANSWER)) {
            DialogForm.showMessageDialog(String.format(WIN_MESSAGE_FORMAT, playerScore, computerScore));
            isEnabled = false;
            return "Гру закінчено!";
        }

        Character nameBegin = getUpperCaseLastValidChar(text);
        if (nameBegin == null) {
            return "Комп'ютер: Введи допустиму назву міста";
        }

        if (enteredCities.contains(text)) {
            return "Комп'ютер: Це ім'я вже використане";
        }

        if (lastComputerAnswer != null && text.charAt(0) != getUpperCaseLastChar(lastComputerAnswer)) {
            return "Комп'ютер: Введи назву на останню літеру відповіді";
        }

        if (!cityRepository.getAll().contains(text)) {
            return "Комп'ютер: Я не знаю таку назву міста";
        }

        playerScore++;
        String finalText = text;
        Optional<String> answerOptional = cityRepository.getAll().stream()
                .filter(str -> str.startsWith(String.valueOf(nameBegin))
                        && !enteredCities.contains(str)
                        && !str.equals(finalText))
                .findFirst();

        if (answerOptional.isPresent()) {
            String answer = answerOptional.get();
            enteredCities.add(text);
            enteredCities.add(answer);
            lastComputerAnswer = answer;
            computerScore++;
            return "Комп'ютер: " + answer;
        } else {
            DialogForm.showMessageDialog(String.format(GAME_OVER_MESSAGE_FORMAT, playerScore, computerScore));
            isEnabled = false;
            return "Комп'ютер: Здаюсь!";
        }
    }

    private Character getUpperCaseLastValidChar(String text) {
        int index = text.length() - 1;
        while (index != 0) {
            char c = Character.toUpperCase(text.charAt(index));
            if (Character.isAlphabetic(c) && !invalidNameEndings.contains(c)) {
                return c;
            }
            index--;
        }
        return null;
    }

    private char getUpperCaseLastChar(String text) {
        return Character.toUpperCase(text.charAt(text.length() - 1));
    }
}
