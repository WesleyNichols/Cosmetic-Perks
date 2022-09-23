package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.enums.ArrowTrails;

import java.util.HashMap;
import java.util.UUID;

public class TrailManager {

    private static HashMap<UUID, ArrowTrails> savedArrowTrails = new HashMap<>();

    public static void addSavedArrowTrail(UUID playerId, ArrowTrails trailMap) {
        savedArrowTrails.put(playerId, trailMap);
    }

    public static boolean hasSavedArrowTrail(UUID playerId) {
        return savedArrowTrails.containsKey(playerId);
    }

    public static ArrowTrails getSavedArrowTrail(UUID playerId) {
        return savedArrowTrails.get(playerId);
    }

    public static void removeSavedArrowTrail(UUID playerId) {
        savedArrowTrails.remove(playerId);
    }
}
