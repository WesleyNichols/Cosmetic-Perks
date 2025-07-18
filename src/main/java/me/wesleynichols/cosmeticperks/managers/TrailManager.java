package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.structures.TrailType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrailManager {

    private final Map<String, CustomTrail> trailList = new HashMap<>();
    private final Map<TrailType, List<CustomTrail>> trailTypeList = new HashMap<>();
    private final Map<TrailType, List<CustomTrail>> trailMap = new HashMap<>();

    public void clearTrailLists() {
        trailList.clear();
        trailTypeList.clear();
        trailMap.clear();
    }

    public CustomTrail getTrail(String name) {
        return trailList.get(name);
    }

    public List<CustomTrail> getTrailType(TrailType type) {
        return trailTypeList.getOrDefault(type, Collections.emptyList());
    }

    public List<CustomTrail> getTrailsByType(TrailType type) {
        return trailMap.getOrDefault(type, Collections.emptyList());
    }

    public void addTrailType(TrailType type, List<CustomTrail> trails) {
        trailTypeList.put(type, trails);
        trailMap.put(type, trails);
    }

    public void addTrail(String name, CustomTrail trail) {
        trailList.put(name, trail);
    }

    public boolean doesTrailExist(String name) {
        return trailList.containsKey(name);
    }
}
