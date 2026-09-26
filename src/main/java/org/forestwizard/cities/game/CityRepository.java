package org.forestwizard.cities.game;

import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.utils.ResourceLoaderException;

import java.util.HashSet;
import java.util.Set;

public class CityRepository {
    private final Set<String> data;

    public CityRepository() throws CityRepositoryException {
        try {
            this.data = new HashSet<>(ResourceLoader.loadTextLines("cities.txt"));
        } catch (ResourceLoaderException e) {
            throw new CityRepositoryException("Resource loader error: " + e.getMessage(), e);
        }
    }

    public Set<String> getAll() {
        return data;
    }
}
