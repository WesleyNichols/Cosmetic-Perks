package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;

import java.util.HashMap;

public class TrailManager {

    private static final HashMap<String, CustomTrail> trailList = new HashMap<>();

    public static HashMap<String, CustomTrail> getTrailList() {
        return new HashMap<>(trailList);
    }

    public static CustomTrail getTrail(String name) {
        return trailList.get(name);
    }

    public static void addTrail(String name, CustomTrail trail) {
        trailList.put(name, trail);
    }

    public static void removeTrail(String name) {
        trailList.remove(name);
    }

}
