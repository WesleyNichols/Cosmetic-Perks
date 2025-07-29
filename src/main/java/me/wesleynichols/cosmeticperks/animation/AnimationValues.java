package me.wesleynichols.cosmeticperks.animation;

/**
 * Holds precomputed animation data including style and equation values,
 * and tracks the current step of the animation.
 */
public class AnimationValues {

    private final double[][][] equationValues;
    private final int equationValuesLength;
    private final double[][] styleValues;
    private final int styleValuesLength;
    private int currentStep = 0;

    /**
     * Constructs AnimationValues.
     *
     * @param styleValues    2D array of style values.
     * @param equationValues 3D array of equation values.
     * @throws IllegalArgumentException if styleValues or equationValues are null.
     */
    public AnimationValues(double[][] styleValues, double[][][] equationValues) {
        if (styleValues == null) {
            throw new IllegalArgumentException("styleValues cannot be null");
        }
        if (equationValues == null) {
            throw new IllegalArgumentException("equationValues cannot be null");
        }

        // Defensive copy
        this.styleValues = deepCopy(styleValues);
        this.styleValuesLength = styleValues.length;

        this.equationValues = deepCopy(equationValues);
        this.equationValuesLength = equationValues.length;
    }

    /**
     * Returns a defensive copy of the 3D equation values array.
     */
    public double[][][] getEquationValues() {
        return deepCopy(equationValues);
    }

    /**
     * Returns the length of the equationValues array.
     */
    public int getEquationValuesLength() {
        return equationValuesLength;
    }

    /**
     * Returns the length of the styleValues array.
     */
    public int getStyleValuesLength() {
        return styleValuesLength;
    }

    /**
     * Returns a defensive copy of the 2D style values array.
     */
    public double[][] getStyleValues() {
        return deepCopy(styleValues);
    }

    /**
     * Returns the current animation step.
     */
    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Advances the current step by one. Loops back to zero if at the end.
     */
    public void addStep() {
        currentStep++;
        if (currentStep >= equationValues[0].length) {
            currentStep = 0;
        }
    }

    /*
     * Helper method for deep copying 2D arrays.
     */
    private static double[][] deepCopy(double[][] original) {
        double[][] copy = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    /*
     * Helper method for deep copying 3D arrays.
     */
    private static double[][][] deepCopy(double[][][] original) {
        double[][][] copy = new double[original.length][][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = deepCopy(original[i]);
        }
        return copy;
    }
}
