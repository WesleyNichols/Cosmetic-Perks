package cosmetic.perks.cosmeticperks.structures;

import javax.annotation.Nullable;

public class Animations {

    private final String[] EquationList;
    private final double AmountToAdd;
    private final double MaxDistance;
    private final boolean ReversingAnimation;
    private double CurrentDistance;
    private int CurrentStep;
    private boolean Reversed;
    private final int TickToComplete;
    private final double[] Offset;
    private final boolean Rotating;
    private final double[][] StyleValues;

    public Animations(String[] equationList, int ticksToComplete, double maxDistance, double[] offset, boolean reversingAnimation, boolean rotating, @Nullable double[][] styleValues) {
        this.EquationList = equationList;
        this.MaxDistance = maxDistance * Math.PI;
        this.TickToComplete = ticksToComplete;
        this.ReversingAnimation = reversingAnimation;
        this.AmountToAdd = MaxDistance/TickToComplete;
        this.CurrentDistance = 0.0;
        this.CurrentStep = 0;
        this.Offset = offset;
        this.Reversed = false;
        this.Rotating = rotating;
        this.StyleValues = styleValues;
    }

    public void addToCurrentDistance() {
        CurrentStep += Reversed ? -1 : 1;

        if(ReversingAnimation) {
            if (CurrentStep >= TickToComplete) {
                Reversed = true;
            } else if (CurrentStep <= 0) {
                Reversed = false;
            }
        } else {
            if(CurrentStep >= TickToComplete) {
                CurrentStep = 0;
            }
        }
        CurrentDistance = AmountToAdd * CurrentStep;
    }

    public double getMaxDistance() {
        return MaxDistance;
    }

    public int getTickToComplete() {
        return TickToComplete;
    }

    public double getCurrentDistance() {
        return CurrentDistance;
    }

    public String[] getEquationList() {
        return EquationList;
    }

    public double[] getOffset() {
        return Offset;
    }

    public boolean isReversingAnimation() {
        return ReversingAnimation;
    }

    public boolean isRotating() {
        return Rotating;
    }

    public double[][] getStyleValues() {
        return StyleValues;
    }
}
