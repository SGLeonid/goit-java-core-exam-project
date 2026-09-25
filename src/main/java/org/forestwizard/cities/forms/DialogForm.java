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
        setSize(550, 160);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setIconImage(ResourceLoader.loadImage("icon.png"));
        } catch (ResourceLoaderException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        this.gameService = new GameService();
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.cityTextField = new JTextField();
        this.cityTextField.setPreferredSize(new Dimension(200, 25));
        this.answerLabel = new JLabel("");
        this.answerLabel.setPreferredSize(new Dimension(200, 25));
        JLabel textFieldLabel = new JLabel("Твій варіант назви міста");
        textFieldLabel.setPreferredSize(new Dimension(200, 25));
        JButton submitButton = new JButton("Зробити хід");
        submitButton.setPreferredSize(new Dimension(200, 25));
        submitButton.addActionListener(e -> {
            if (gameService.isEnabled()) {
                String answer = gameService.doTurn(cityTextField.getText());
                answerLabel.setText(answer);
            }
        });

        panel.add(cityTextField);
        panel.add(textFieldLabel);
        panel.add(submitButton);
        panel.add(answerLabel);
        add(panel);
    }

    public static void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}