package org.forestwizard.cities.utils;

import org.forestwizard.cities.Main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ResourceLoader {
    private ResourceLoader() {}

    public static List<String> load(String filename) throws ResourceLoaderException {
        try (FileReader reader = new FileReader(new File(Main.class.getClassLoader().getResource(filename).toURI()))) {
            return reader.readAllLines();
        } catch (IOException | URISyntaxException e) {
            throw new ResourceLoaderException(e.getMessage(), e);
        }
    }
}
