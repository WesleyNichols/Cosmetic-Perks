package me.wesleynichols.cosmeticperks.structures;

/**
 * Represents an animation sequence with formulas and offsets,
 * managing the animation progress over time.
 */
public class Animations {

    private final String[] equationList;
    private final double amountToAdd;
    private final double maxDistance;
    private final boolean reversingAnimation;
    private double currentDistance = 0.0;
    private int currentStep = 0;
    private boolean reversed = false;
    private final int tickToComplete;
    private final double[] offset;
    private final double[] angleOffset;

    /**
     * Constructs an Animations instance.
     *
     * @param equationList       List of equations/formulas driving the animation.
     * @param tickToComplete     Number of ticks for one animation cycle.
     * @param maxDistance        Maximum distance parameter, multiplied by π internally.
     * @param offset             XYZ offset for particle positioning.
     * @param angleOffset        Angle offset for the animation.
     * @param reversingAnimation Whether the animation reverses after completing.
     * @throws IllegalArgumentException if tickToComplete is zero or negative, or offsets are null.
     */
    public Animations(String[] equationList, int tickToComplete, double maxDistance, double[] offset, double[] angleOffset, boolean reversingAnimation) {
        if (tickToComplete <= 0) {
            throw new IllegalArgumentException("tickToComplete must be positive");
        }
        if (offset == null || angleOffset == null) {
            throw new IllegalArgumentException("offset and angleOffset cannot be null");
        }

        this.equationList = equationList != null ? equationList.clone() : new String[0];
        this.maxDistance = maxDistance * Math.PI;
        this.tickToComplete = tickToComplete;
        this.reversingAnimation = reversingAnimation;
        this.amountToAdd = this.maxDistance / this.tickToComplete;
        this.offset = offset.clone();
        this.angleOffset = angleOffset.clone();
    }

    /**
     * Advances the animation by one step, updating distance and reversing if necessary.
     */
    public void addToCurrentDistance() {
        currentStep += reversed ? -1 : 1;

        if (reversingAnimation) {
            if (currentStep >= tickToComplete) {
                reversed = true;
            } else if (currentStep <= 0) {
                reversed = false;
            }
        } else {
            if (currentStep >= tickToComplete) {
                currentStep = 0;
            }
        }

        currentDistance = amountToAdd * currentStep;
    }

    /** Resets the animation to the initial state. */
    public void reset() {
        currentStep = 0;
        currentDistance = 0;
        reversed = false;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public int getTickToComplete() {
        return tickToComplete;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public String[] getEquationList() {
        return equationList.clone();
    }

    public double[] getOffset() {
        return offset.clone();
    }

    public double[] getAngleOffset() {
        return angleOffset.clone();
    }

    public boolean isReversingAnimation() {
        return reversingAnimation;
    }
}
