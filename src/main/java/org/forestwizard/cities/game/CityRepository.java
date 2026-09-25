package org.forestwizard.cities.game;

import org.forestwizard.cities.forms.DialogForm;
import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import java.util.HashSet;
import java.util.Set;

public class CityRepository {
    private final Set<String> data;

    public CityRepository() {
        Set<String> set = null;
        try {
            set = new HashSet<>(ResourceLoader.loadTextLines("cities.txt"));
        } catch (ResourceLoaderException e) {
            DialogForm.showMessageDialog("Resource loader error: " + e.getMessage());
        }
        this.data = set;
    }

    public Set<String> getAll() {
        return data;
    }
}
