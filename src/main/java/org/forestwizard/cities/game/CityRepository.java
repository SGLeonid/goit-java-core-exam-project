package org.forestwizard.cities.game;

import org.forestwizard.cities.exception.CityRepositoryException;
import org.forestwizard.cities.utils.CityNameNormalizer;
import org.forestwizard.cities.utils.ResourceLoader;
import org.forestwizard.cities.exception.ResourceLoaderException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CityRepository {
    private final Set<String> data;

    public CityRepository() throws CityRepositoryException {
        try {
            List<String> names = ResourceLoader.loadTextLines("cities.txt");
            this.data = names.stream()
                    .map(CityNameNormalizer::normalizeName)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (ResourceLoaderException e) {
            throw new CityRepositoryException("Resource loader error: " + e.getMessage(), e);
        }
    }

    public Set<String> getAll() {
        return data;
    }
}
