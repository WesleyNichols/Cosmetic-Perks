package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;

import java.util.HashMap;
import java.util.UUID;

public class ProjectileTrailManager {

    private static HashMap<UUID, ProjectileTrails> projTrailList = new HashMap<>();

    public static HashMap<UUID, ProjectileTrails> getProjTrailList() {
        return new HashMap<>(projTrailList);
    }

    public static void addProjTrail(UUID proj, ProjectileTrails trailMap) {
        projTrailList.put(proj, trailMap);
    }

    public static void removeProjTrail(UUID proj) {
        projTrailList.remove(proj);
    }

}
