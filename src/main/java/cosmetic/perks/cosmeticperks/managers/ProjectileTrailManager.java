package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;

import java.util.HashMap;
import java.util.UUID;

public class ProjectileTrailManager {

    private static final HashMap<UUID, CustomTrail> projTrailList = new HashMap<>();

    public static HashMap<UUID, CustomTrail> getProjTrailList() {
        return new HashMap<>(projTrailList);
    }

    public static void addProjTrail(UUID proj, CustomTrail trailMap) {
        projTrailList.put(proj, trailMap);
    }

    public static void removeProjTrail(UUID proj) {
        projTrailList.remove(proj);
    }

}
