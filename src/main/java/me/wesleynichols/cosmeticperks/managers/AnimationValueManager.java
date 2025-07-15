package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.structures.AnimationValues;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnimationValueManager {

    private final Map<String, AnimationValues> animationValueMap = new HashMap<>();

    public void clearAnimationValueList() {
        animationValueMap.clear();
    }

    public AnimationValues getAnimationValues(String name) {
        return animationValueMap.get(name);
    }

    public void addAnimationValues(String name, AnimationValues animationValues) {
        animationValueMap.put(name, animationValues);
    }

    public Map<String, AnimationValues> getAllAnimationValues() {
        return Collections.unmodifiableMap(animationValueMap);
    }
}
