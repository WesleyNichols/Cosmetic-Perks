package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.AnimationValues;

import java.util.HashMap;

public class AnimationValueManager {
    private static final HashMap<String, AnimationValues> animationValueList = new HashMap<>();

    public static void clearAnimationValueList() {
        animationValueList.clear();
    }

    public static AnimationValues getAnimationValues(String name) {
        return animationValueList.get(name);
    }

    public static void addParticleAnimation(String name, AnimationValues equationValues) {
        animationValueList.put(name, equationValues);
    }
}
