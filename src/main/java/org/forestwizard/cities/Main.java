package org.forestwizard.cities;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.forestwizard.cities.forms.WelcomeForm;
import javax.swing.*;

public class Main {
    static void main() {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            WelcomeForm form = new WelcomeForm();
            form.setVisible(true);
        });
    }
}
