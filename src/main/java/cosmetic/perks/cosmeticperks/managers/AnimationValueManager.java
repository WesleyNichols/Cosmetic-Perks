package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.AnimationValues;

import java.util.HashMap;

public class AnimationValueManager {
    private static final HashMap<String, AnimationValues> equationValueList = new HashMap<>();

    public static AnimationValues getAnimationValues(String name) {
        return equationValueList.get(name);
    }

    public static void addParticleAnimation(String name, AnimationValues equationValues) {
        equationValueList.put(name, equationValues);
    }
}
