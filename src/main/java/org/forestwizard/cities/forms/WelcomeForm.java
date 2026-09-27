package org.forestwizard.cities.forms;

import org.forestwizard.cities.exception.CityRepositoryException;
import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.exception.ResourceLoaderException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomeForm extends JFrame {
    private static final String WELCOME_TITLE_TEXT = "Ласкаво просимо!";
    private static final String WELCOME_LABEL_TEXT = "Ласкаво просимо у гру 'Міста'. Почнімо!";
    private final JButton button;

    public WelcomeForm() throws CityRepositoryException {
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
        button = new JButton("OK");

        add(label);
        add(button);
    }

    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }
}
