package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.structures.CustomTrail;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrailManager {

    private final Map<String, CustomTrail> trailList = new HashMap<>();
    private final Map<String, List<CustomTrail>> trailTypeList = new HashMap<>();
    private final Map<String, List<CustomTrail>> trailMap = new HashMap<>();


    public void clearTrailLists() {
        trailList.clear();
        trailTypeList.clear();
    }

    public CustomTrail getTrail(String name) {
        return trailList.get(name);
    }

    public List<CustomTrail> getTrailType(String type) {
        return trailTypeList.get(type);
    }

    public List<CustomTrail> getTrailsByType(String type) {
        return trailMap.getOrDefault(type.toLowerCase(), Collections.emptyList());
    }

    public void addTrailType(String type, List<CustomTrail> trails) {
        String key = type.toLowerCase();
        trailTypeList.put(key, trails);
        trailMap.put(key, trails);
    }

    public void addTrail(String name, CustomTrail trail) {
        trailList.put(name, trail);
    }

    public boolean doesTrailExist(String name) {
        return trailList.containsKey(name);
    }
}