package org.forestwizard.cities.utils;

public class CityNameNormalizer {
    private CityNameNormalizer() {}

    public static String normalizeName(String name) {
        return name.trim().toLowerCase().chars().filter(Character::isLetter).collect(
                StringBuilder::new,
                (builder, c) -> builder.append((char)c),
                StringBuilder::append
        ).toString();
    }
}
