package org.forestwizard.cities.forms;

import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DialogForm extends JFrame {
    private static final String WINDOW_TITLE = "Cities";
    private static final String USER_GIVE_UP_ANSWER = "i give up";

    private final JTextField cityTextField;
    private final JLabel answerLabel;
    private final Set<String> enteredCities;
    private final Set<String> computerKnownCities;

    public DialogForm() {
        super();
        setTitle(WINDOW_TITLE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.cityTextField = new JTextField();
        this.cityTextField.setBounds(20, 20, 200, 25);
        this.answerLabel = new JLabel("");
        this.answerLabel.setBounds(230, 60, 250, 30);
        JLabel textFieldLabel = new JLabel("Enter the name of city here");
        textFieldLabel.setBounds(230, 20, 200, 25);
        JButton submitButton = new JButton("Enter");
        submitButton.setBounds(20, 60, 200, 30);
        this.enteredCities = new HashSet<>();
        this.computerKnownCities = new HashSet<>();

        try {
            List<String> list = ResourceLoader.load("cities.txt");
            this.computerKnownCities.addAll(list);
        } catch (ResourceLoaderException e) {
            JOptionPane.showMessageDialog(this, "Resource loader error: " + e.getMessage());
        }

        submitButton.addActionListener(e -> {
            String text = cityTextField.getText();
            if (text == null || text.isEmpty()) {
                answerLabel.setText("Computer: Please, enter the name of city");
                return;
            }

            if (text.trim().equalsIgnoreCase(USER_GIVE_UP_ANSWER)) {
                answerLabel.setText("Game over!");
                submitButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Game over. Your score: " + enteredCities.size());
                return;
            }

            if (enteredCities.contains(text.toLowerCase())) {
                answerLabel.setText("Computer: You've already entered this name");
                return;
            }

            String nameBegin = Character.toString(text.toCharArray()[text.length() - 1]).toUpperCase();
            Optional<String> answerOptional = computerKnownCities.stream()
                    .filter(str -> str.startsWith(nameBegin))
                    .findFirst();
            answerLabel.setText("Computer: " + answerOptional.orElse("I give up!"));
            enteredCities.add(text.toLowerCase());

            if (answerOptional.isPresent()) {
                computerKnownCities.remove(answerOptional.get());
            } else {
                submitButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "You win. Your score: " + enteredCities.size());
            }
        });

        add(cityTextField);
        add(textFieldLabel);
        add(submitButton);
        add(answerLabel);
        setLayout(null);
    }
}