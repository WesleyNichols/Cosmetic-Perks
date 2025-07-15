package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.structures.CustomTrail;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrailManager {

    private final Map<String, CustomTrail> trailList = new HashMap<>();
    private final Map<String, List<CustomTrail>> trailTypeList = new HashMap<>();

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

    public void addTrailType(String type, List<CustomTrail> trails) {
        trailTypeList.put(type, trails);
    }

    public void addTrail(String name, CustomTrail trail) {
        trailList.put(name, trail);
    }

    public void removeTrail(String name) {
        trailList.remove(name);
    }
}
