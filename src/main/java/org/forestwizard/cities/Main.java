package org.forestwizard.cities;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.forestwizard.cities.exception.CityRepositoryException;
import org.forestwizard.cities.forms.DialogForm;
import org.forestwizard.cities.forms.WelcomeForm;
import org.forestwizard.cities.game.CityRepository;
import org.forestwizard.cities.game.GameService;

import javax.swing.*;

public class Main {
    static void main() {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            try {
                CityRepository repository = new CityRepository();
                GameService service = new GameService(repository);
                WelcomeForm welcomeForm = new WelcomeForm();
                welcomeForm.addActionListener(_ -> {
                    new DialogForm(service).setVisible(true);
                    welcomeForm.dispose();
                });
                welcomeForm.setVisible(true);
            } catch (CityRepositoryException e) {
                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage(),
                        "Error occurred",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
