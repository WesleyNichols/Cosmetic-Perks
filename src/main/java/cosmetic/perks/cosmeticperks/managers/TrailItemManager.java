package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.enums.ArrowTrails;

import java.util.HashMap;
import java.util.UUID;

public class TrailItemManager {
    private static HashMap<UUID, ArrowTrails> arrowTrailList = new HashMap<>();

    public static HashMap<UUID, ArrowTrails> getArrowTrailList() {
        return new HashMap<>(arrowTrailList);
    }

    public static void addArrowTrail(UUID arrow, ArrowTrails trailMap) {
        arrowTrailList.put(arrow, trailMap);
    }

    public static void removeSavedArrowTrail(UUID arrow) {
        arrowTrailList.remove(arrow);
    }
}