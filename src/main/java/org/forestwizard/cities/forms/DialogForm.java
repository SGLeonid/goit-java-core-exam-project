package org.forestwizard.cities.forms;

import org.forestwizard.cities.game.CityRepository;
import org.forestwizard.cities.game.CityRepositoryException;
import org.forestwizard.cities.game.GameService;
import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import javax.swing.*;
import java.awt.*;

public class DialogForm extends JFrame {
    private static final String GAME_OVER_MESSAGE_FORMAT = "Гру закінчено!\nТвій рекорд: %s \nРекорд комп'ютера: %s";
    private static final String WIN_MESSAGE_FORMAT = "Ти переміг!\nТвій рекорд: %s\nРекорд комп'ютера: %s";
    private static final String WINDOW_TITLE = "Міста";
    private static final String SUBMIT_BUTTON_DO_TURN_TEXT = "Зробити хід";
    private static final String SUBMIT_BUTTON_NEW_GAME_TEXT = "Нова гра";
    private final JTextField cityTextField;
    private final JLabel answerLabel;
    private final transient GameService gameService;
    private SubmitButtonState buttonState = SubmitButtonState.STATE_DO_TURN;

    public DialogForm() throws CityRepositoryException {
        CityRepository repository;

        super();
        setTitle(WINDOW_TITLE);
        setSize(500, 160);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setIconImage(ResourceLoader.loadImage("icon.png"));
        } catch (ResourceLoaderException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        repository = new CityRepository();
        this.gameService = new GameService(repository);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints constraints = new GridBagConstraints();

        this.cityTextField = new JTextField();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0f;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(cityTextField, constraints);

        JLabel textFieldLabel = new JLabel("Твій варіант назви міста");
        constraints.gridx = 1;
        constraints.weightx = 1.0f;
        panel.add(textFieldLabel, constraints);

        this.answerLabel = new JLabel("");
        this.answerLabel.setMaximumSize(new Dimension(400, 25));
        JButton submitButton = new JButton(SUBMIT_BUTTON_DO_TURN_TEXT);
        submitButton.setPreferredSize(new Dimension(100, 25));
        submitButton.addActionListener(e -> {
            if (buttonState == SubmitButtonState.STATE_DO_TURN) {
                MoveResult result = gameService.doTurn(cityTextField.getText());
                answerLabel.setText(result.getMessage());
                if (result.getType() == MoveResultType.WIN) {
                    JOptionPane.showMessageDialog(this, String.format(
                            WIN_MESSAGE_FORMAT,
                            result.getPlayerScore(),
                            result.getComputerScore()
                    ));
                    submitButton.setText(SUBMIT_BUTTON_NEW_GAME_TEXT);
                    buttonState = SubmitButtonState.STATE_NEW_GAME;
                    return;
                }

                if (result.getType() == MoveResultType.GAME_OVER) {
                    JOptionPane.showMessageDialog(this, String.format(
                            GAME_OVER_MESSAGE_FORMAT,
                            result.getPlayerScore(),
                            result.getComputerScore()
                    ));
                    submitButton.setText(SUBMIT_BUTTON_NEW_GAME_TEXT);
                    buttonState = SubmitButtonState.STATE_NEW_GAME;
                    return;
                }
            }

            if (buttonState == SubmitButtonState.STATE_NEW_GAME) {
                buttonState = SubmitButtonState.STATE_DO_TURN;
                cityTextField.setText("");
                answerLabel.setText("");
                submitButton.setText(SUBMIT_BUTTON_DO_TURN_TEXT);
                gameService.reset();
            }
        });

        constraints.weightx = 0.0f;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(submitButton, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0f;
        panel.add(answerLabel, constraints);

        add(panel);
    }
}