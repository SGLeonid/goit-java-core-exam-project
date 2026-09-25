package org.forestwizard.cities.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourceLoader {
    private ResourceLoader() {}

    public static byte[] loadBytes(String path) throws ResourceLoaderException {
        try (InputStream stream = getInputStream(path)) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new ResourceLoaderException(e.getMessage(), e);
        }
    }

    public static List<String> loadTextLines(String path) throws ResourceLoaderException {
        return new String(loadBytes(path)).lines().toList();
    }

    public static BufferedImage loadImage(String path) throws ResourceLoaderException {
        try (InputStream stream = getInputStream(path)) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new ResourceLoaderException(e.getMessage(), e);
        }
    }

    private static InputStream getInputStream(String path) throws ResourceLoaderException {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new ResourceLoaderException("Cannot find resource " + path);
        }
        return stream;
    }
}
