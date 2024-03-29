package me.wesleynichols.cosmeticperks.config;

import me.wesleynichols.cosmeticperks.managers.AnimationValueManager;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.wesleynichols.cosmeticperks.structures.Animations;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.styles.Styles;
import me.wesleynichols.cosmeticperks.util.AnimationValueInitialize;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigParser {

    public static void parseConfig(FileConfiguration config, String trailType) {
        List<CustomTrail> trailList = new ArrayList<>();
        for(String trailName: config.getKeys(false)) {
            AnimationValues animation = checkForAnimation(trailName, config);
            Particle trailEffect = checkForEffect(trailName, config);
            Material displayMaterial = checkForMaterial(trailName, config);
            double[] offset = checkForOffset(trailName, config);
            double speed = checkForSpeed(trailName, config);
            int amount = checkForAmount(trailName, config);
            boolean limited = checkForLimited(trailName, config);
            CustomTrail trail = new CustomTrail.CustomTrailBuilder(trailType, trailName)
                    .animation(animation)
                    .trailEffect(trailEffect)
                    .displayMaterial(displayMaterial)
                    .offset(offset)
                    .particleSpeed(speed)
                    .particleAmount(amount)
                    .limitedItem(limited)
                    .build();
            TrailManager.addTrail(trailName, trail);
            trailList.add(trail);
        }
        TrailManager.addTrailType(trailType, trailList);
    }

    private static boolean checkForLimited(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".limited") || config.get(trailName + ".limited") == null) {return false;}
        return Boolean.parseBoolean(config.getString(trailName + ".limited"));
    }

    private static int checkForAmount(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".particle_amount") || config.get(trailName + ".particle_amount") == null) {return 0;}
        int amount;
        try {
            amount = Integer.parseInt(config.getString(trailName + ".particle_amount"));
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[CosmeticPerks] An error occurred when trying to parse the particle amount for the trail named " + trailName);
            return 0;
        }
        return amount;
    }

    private static double checkForSpeed(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".particle_speed") || config.get(trailName + ".particle_speed") == null) {return 0.0;}
        double speed;
        try {
            speed = Double.parseDouble(config.getString(trailName + ".particle_speed"));
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[CosmeticPerks] An error occurred when trying to parse the particle speed for the trail named " + trailName);
            return 0.0;
        }
        return speed;
    }

    private static double[] checkForOffset(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".offset") || config.get(trailName + ".offset") == null) {return null;}
        String offset = Objects.requireNonNull(config.getString(trailName + ".offset"));
        return stringArrToDouble(offset.substring(1, offset.length()-1).split(";"));
    }

    private static Material checkForMaterial(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".display_material") || config.get(trailName + ".display_material") == null) {return null;}
        Material material;
        try {
            material = Material.getMaterial(config.getString(trailName + ".display_material"));
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[CosmeticPerks] An error occurred when trying to parse the material for the trail named " + trailName);
            return null;
        }
        return material;
    }

    private static Particle checkForEffect(String trailName, FileConfiguration config) {
        if(!config.contains(trailName + ".particle") || config.get(trailName + ".particle") == null) {return null;}
        Particle particle;
        try {
            particle = Particle.valueOf(config.getString(trailName + ".particle"));
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[CosmeticPerks] An error occurred when trying to parse the particle enum for the trail named " + trailName);
            return null;
        }
        return particle;
    }

    private static AnimationValues checkForAnimation(String trailName, FileConfiguration config) {
        if(!(config.contains(trailName + ".equations") || config.contains(trailName + ".styles"))) {return null;}

        if(config.contains(trailName + ".equations") && config.contains(trailName + ".styles")) {
            new AnimationValueInitialize(trailName, styleCase(config.getStringList(trailName + ".styles")), getEquation(config.getStringList(trailName + ".equations")));
        } else if (config.contains(trailName + ".equations")) {
            new AnimationValueInitialize(trailName, getEquation(config.getStringList(trailName + ".equations")));
        } else if (config.contains(trailName + ".styles")) {
            new AnimationValueInitialize(trailName, styleCase(config.getStringList(trailName + ".styles")));
        }
        return AnimationValueManager.getAnimationValues(trailName);
    }

    private static Animations[] getEquation(List<String> args) {
        Animations[] animations = new Animations[args.size()];
        for(int i = 0; i < args.size(); i++) {
            String[] argList = args.get(i).replaceAll("\\s", "").split(",");
            checkListError(argList, 6, "equation");
            double[] offset = argList[3].equals("null") ? new double[]{0,0,0} : stringArrToDouble(argList[3].substring(1, argList[3].length()-1).split(";"));
            double[] angleOffset = argList[4].equals("null") ? new double[]{0,0} : stringArrToDouble(argList[4].substring(1, argList[4].length() - 1).split(";"));
            animations[i] =  new Animations(argList[0].substring(1, argList[0].length()-1).split(";"), Integer.parseInt(argList[1]),
                    Double.parseDouble(argList[2]), offset, angleOffset, Boolean.parseBoolean(argList[5]));
        }
        return animations;
    }

    private static double[][] styleCase(@NotNull List<String> args) {
        double[][][] values = new double[args.size()][][];
        for (int i = 0; i < args.size(); i++) {
            String[] argList = args.get(i).replaceAll("\\s", "").split(",");
            double[][] value;
            double[] offset = argList[1].equals("null") ? null : stringArrToDouble(argList[1].substring(1, argList[1].length() - 1).split(";"));
            double[] angleOffset = argList[2].equals("null") ? null : stringArrToDouble(argList[2].substring(1, argList[2].length() - 1).split(";"));
            switch (argList[0]) {
                case "circle" -> {
                    checkListError(argList, 5, "circle");
                    value = Styles.circle(offset, angleOffset, Double.parseDouble(argList[3]), Integer.parseInt(argList[4]));
                }
                case "square" -> {
                    checkListError(argList, 5, "square");
                    value = Styles.square(offset, angleOffset, Double.parseDouble(argList[3]), Integer.parseInt(argList[4]));
                }
                default -> throw new IllegalArgumentException("The style provided was not found!");
            }
            values[i] = value;
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

    private static void checkListError(String[] list, int neededArgLength, String type) {
        if(list.length != neededArgLength) {
            throw new IllegalArgumentException("Not enough or too many arguments to create the '" + type + "'."
                    + " Expected " + neededArgLength + " values, but was given " + list.length + " values.");
        }
    }
}