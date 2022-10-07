package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;

import java.util.HashMap;
import java.util.List;

public class TrailManager {

    private static final HashMap<String, CustomTrail> trailList = new HashMap<>();

    private static final HashMap<String, List<CustomTrail>> trailTypeList = new HashMap<>();

    public static HashMap<String, CustomTrail> getTrailList() {
        return new HashMap<>(trailList);
    }

    public static HashMap<String, List<CustomTrail>> getTrailTypeList() {
        return new HashMap<>(trailTypeList);
    }

    public static void clearTrailLists() {
        trailList.clear();
        trailTypeList.clear();
    }

    public static CustomTrail getTrail(String name) {
        return trailList.get(name);
    }

    public static List<CustomTrail> getTrailType(String type) {
        return trailTypeList.get(type);
    }

    public static void addTrailType(String type, List<CustomTrail> trails) {
        trailTypeList.put(type, trails);
    }

    public static void addTrail(String name, CustomTrail trail) {
        trailList.put(name, trail);
    }

    public static void removeTrail(String name) {
        trailList.remove(name);
    }

}
