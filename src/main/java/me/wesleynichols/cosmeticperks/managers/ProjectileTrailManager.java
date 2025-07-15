package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.structures.CustomTrail;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectileTrailManager {

    // Use ConcurrentHashMap for thread-safe concurrent access (e.g., from async tasks)
    private final Map<UUID, CustomTrail> projTrailMap = new ConcurrentHashMap<>();

    public void clearProjTrailList() {
        projTrailMap.clear();
    }

    /**
     * Returns an unmodifiable view of the current projectile trails
     * to prevent external modification of internal state.
     */
    public Map<UUID, CustomTrail> getProjTrailList() {
        return Collections.unmodifiableMap(projTrailMap);
    }

    public void addProjTrail(UUID projId, CustomTrail trail) {
        projTrailMap.put(projId, trail);
    }

    public void removeProjTrail(UUID projId) {
        projTrailMap.remove(projId);
    }
}
