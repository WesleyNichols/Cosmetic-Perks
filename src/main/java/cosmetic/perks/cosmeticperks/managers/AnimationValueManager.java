package cosmetic.perks.cosmeticperks.managers;

import java.util.HashMap;

public class AnimationValueManager {
    private static HashMap<String, double[][][]> equationValueList = new HashMap<>();

    public static HashMap<String, double[][][]> getEquationValueList() {
        return new HashMap<>(equationValueList);
    }

    public static double[][][] getEquationValues(String name) {
        return equationValueList.get(name);
    }

    public static void addParticleAnimation(String name, double[][][] equationValues) {
        equationValueList.put(name, equationValues);
    }
}
