package me.wesleynichols.cosmeticperks.trails;

import java.util.Arrays;
import java.util.Optional;

public enum TrailType {
    PLAYER("player"),
    ELYTRA("elytra"),
    PROJECTILE("projectile");

    private final String name;

    TrailType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns Optional containing TrailType matching input string (case-insensitive),
     * or empty Optional if no match found.
     */
    public static Optional<TrailType> fromString(String input) {
        if (input == null) return Optional.empty();
        return Arrays.stream(values())
                .filter(t -> t.name.equalsIgnoreCase(input))
                .findFirst();
    }

    /**
     * Returns TrailType matching input string (case-insensitive),
     * or throws IllegalArgumentException if invalid.
     */
    public static TrailType fromStringOrThrow(String input) {
        return fromString(input)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid TrailType: " + input + ". Must be one of player, elytra, or projectile."
                ));
    }

    public static boolean isValid(String input) {
        return fromString(input).isPresent();
    }
}
