package org.forestwizard.cities.forms;

import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import javax.swing.*;
import java.awt.*;

public class WelcomeForm extends JFrame {
    private static final String WELCOME_TITLE_TEXT = "Ласкаво просимо!";
    private static final String WELCOME_LABEL_TEXT = "Ласкаво просимо у гру 'Міста'. Почнімо!";

    public WelcomeForm() {
        super();
        setTitle(WELCOME_TITLE_TEXT);
        setSize(400, 100);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setIconImage(ResourceLoader.loadImage("icon.png"));
        } catch (ResourceLoaderException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        JLabel label = new JLabel(WELCOME_LABEL_TEXT);
        JButton button = new JButton("OK");
        button.addActionListener(event -> {
            DialogForm dialogForm = new DialogForm();
            dialogForm.setVisible(true);
            setVisible(false);
            try {
                String text = ResourceLoader.loadTextLines("rules.txt").stream().collect(
                        StringBuilder::new,
                        (builder, str) -> builder.append(str).append("\n"),
                        StringBuilder::append
                ).toString();
                JOptionPane.showMessageDialog(this, text);
            } catch (ResourceLoaderException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }

        });

        add(label);
        add(button);
    }
}
