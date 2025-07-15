package me.wesleynichols.cosmeticperks.config;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.wesleynichols.cosmeticperks.structures.Animations;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.styles.Styles;
import me.wesleynichols.cosmeticperks.util.AnimationValueInitialize;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigParser {

    private static final CosmeticPerks plugin = CosmeticPerks.getInstance();
    private static final Logger LOGGER = plugin.getLogger();

    public static void parseConfig(FileConfiguration config, String trailType) {
        List<CustomTrail> trailList = new ArrayList<>();

        for (String trailName : config.getKeys(false)) {
            CustomTrail trail = new CustomTrail.CustomTrailBuilder(trailType, trailName)
                    .animation(checkForAnimation(trailName, config))
                    .trailEffect(checkForEffect(trailName, config))
                    .displayMaterial(checkForMaterial(trailName, config))
                    .offset(checkForOffset(trailName, config))
                    .particleSpeed(checkForSpeed(trailName, config))
                    .particleAmount(checkForAmount(trailName, config))
                    .limitedItem(checkForLimited(trailName, config))
                    .build();

            plugin.getTrailManager().addTrail(trailName, trail);
            trailList.add(trail);
        }

        plugin.getTrailManager().addTrailType(trailType, trailList);
    }

    private static boolean checkForLimited(String trailName, FileConfiguration config) {
        return config.getBoolean(trailName + ".limited_item", false);
    }

    private static int checkForAmount(String trailName, FileConfiguration config) {
        try {
            return Integer.parseInt(config.getString(trailName + ".particle_amount", "0"));
        } catch (NumberFormatException e) {
            LOGGER.warning("Failed to parse particle amount for trail: " + trailName);
            return 0;
        }
    }

    private static double checkForSpeed(String trailName, FileConfiguration config) {
        try {
            return Double.parseDouble(config.getString(trailName + ".particle_speed", "0.0"));
        } catch (NumberFormatException e) {
            LOGGER.warning("Failed to parse particle speed for trail: " + trailName);
            return 0.0;
        }
    }

    private static double[] checkForOffset(String trailName, FileConfiguration config) {
        if (!config.contains(trailName + ".offset")) return null;
        String offset = Objects.requireNonNull(config.getString(trailName + ".offset"));
        return stringArrToDouble(offset.substring(1, offset.length() - 1).split(";"));
    }

    private static Material checkForMaterial(String trailName, FileConfiguration config) {
        try {
            return Material.valueOf(Objects.requireNonNull(config.getString(trailName + ".display_material")));
        } catch (Exception e) {
            LOGGER.warning("Failed to parse material for trail: " + trailName);
            return null;
        }
    }

    private static Particle checkForEffect(String trailName, FileConfiguration config) {
        try {
            return Particle.valueOf(Objects.requireNonNull(config.getString(trailName + ".particle")));
        } catch (Exception e) {
            LOGGER.warning("Failed to parse particle for trail: " + trailName);
            return null;
        }
    }

    private static AnimationValues checkForAnimation(String trailName, FileConfiguration config) {
        boolean hasEquations = config.contains(trailName + ".equations");
        boolean hasStyles = config.contains(trailName + ".styles");

        if (!hasEquations && !hasStyles) return null;

        if (hasEquations && hasStyles) {
            new AnimationValueInitialize(plugin, trailName,
                    styleCase(config.getStringList(trailName + ".styles")),
                    getEquation(config.getStringList(trailName + ".equations"))
            );
        } else if (hasEquations) {
            new AnimationValueInitialize(plugin, trailName,
                    getEquation(config.getStringList(trailName + ".equations"))
            );
        } else {
            new AnimationValueInitialize(plugin, trailName,
                    styleCase(config.getStringList(trailName + ".styles"))
            );
        }

        return plugin.getAnimationValueManager().getAnimationValues(trailName);
    }

    private static Animations[] getEquation(List<String> args) {
        Animations[] animations = new Animations[args.size()];
        for (int i = 0; i < args.size(); i++) {
            String[] parts = args.get(i).replaceAll("\\s", "").split(",");
            checkListError(parts, 6, "equation");

            double[] offset = parts[3].equals("null") ? new double[]{0, 0, 0}
                    : stringArrToDouble(parts[3].substring(1, parts[3].length() - 1).split(";"));

            double[] angleOffset = parts[4].equals("null") ? new double[]{0, 0}
                    : stringArrToDouble(parts[4].substring(1, parts[4].length() - 1).split(";"));

            animations[i] = new Animations(
                    parts[0].substring(1, parts[0].length() - 1).split(";"),
                    Integer.parseInt(parts[1]),
                    Double.parseDouble(parts[2]),
                    offset,
                    angleOffset,
                    Boolean.parseBoolean(parts[5])
            );
        }
        return animations;
    }

    private static double[][] styleCase(List<String> args) {
        double[][][] values = new double[args.size()][][];

        for (int i = 0; i < args.size(); i++) {
            String[] parts = args.get(i).replaceAll("\\s", "").split(",");
            String type = parts[0];
            checkListError(parts, 5, type);

            double[] offset = parts[1].equals("null") ? null
                    : stringArrToDouble(parts[1].substring(1, parts[1].length() - 1).split(";"));

            double[] angleOffset = parts[2].equals("null") ? null
                    : stringArrToDouble(parts[2].substring(1, parts[2].length() - 1).split(";"));

            double radius = Double.parseDouble(parts[3]);
            int points = Integer.parseInt(parts[4]);

            values[i] = switch (type) {
                case "circle" -> Styles.circle(offset, angleOffset, radius, points);
                case "square" -> Styles.square(offset, angleOffset, radius, points);
                default -> throw new IllegalArgumentException("Unknown style: " + type);
            };
        }

        return Styles.styleValues(values);
    }

    private static double[] stringArrToDouble(String[] s) {
        double[] result = new double[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Double.parseDouble(s[i]);
        }
        return result;
    }

    private static void checkListError(String[] list, int expectedLength, String type) {
        if (list.length != expectedLength) {
            throw new IllegalArgumentException("Incorrect number of arguments for " + type +
                    ": expected " + expectedLength + ", got " + list.length);
        }
    }
}
