package org.forestwizard.cities.forms;

import org.forestwizard.cities.game.GameService;
import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import javax.swing.*;
import java.awt.*;

public class DialogForm extends JFrame {
    private static final String WINDOW_TITLE = "Міста";
    private final JTextField cityTextField;
    private final JLabel answerLabel;
    private final transient GameService gameService;

    public DialogForm() {
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

        this.gameService = new GameService();
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
        JButton submitButton = new JButton("Зробити хід");
        submitButton.addActionListener(e -> {
            if (gameService.isEnabled()) {
                String answer = gameService.doTurn(cityTextField.getText());
                answerLabel.setText(answer);
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

    public static void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}