package org.forestwizard.cities.forms;

import javax.swing.*;

public class WelcomeForm extends JFrame {
    private static final String WELCOME_TITLE_TEXT = "Welcome!";
    private static final String WELCOME_LABEL_TEXT = "Welcome to the Cities game. Let's begin!";

    public WelcomeForm() {
        super();
        setTitle(WELCOME_TITLE_TEXT);
        setSize(400, 100);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel(WELCOME_LABEL_TEXT);
        label.setBounds(20, 20, 240,20);
        JButton button = new JButton("OK");
        button.setBounds(260, 15, 100, 30);
        button.addActionListener(e -> {
            DialogForm dialogForm = new DialogForm();
            dialogForm.setVisible(true);
            setVisible(false);
        });

        add(label);
        add(button);
    }
}
