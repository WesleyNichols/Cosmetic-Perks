package cosmetic.perks.cosmeticperks.structures;

public class Animations {

    private final String[] EquationList;
    private final double AmountToAdd;
    private final double MaxDistance;
    private final boolean ReversingAnimation;
    private double CurrentDistance = 0.0;
    private int CurrentStep = 0;
    private boolean Reversed = false;
    private final int TickToComplete;
    private final double[] Offset;
    private final double[] AngleOffset;

    public Animations(String[] equationList, int ticksToComplete, double maxDistance, double[] offset, double[] angleOffset, boolean reversingAnimation) {
        this.EquationList = equationList;
        this.MaxDistance = maxDistance * Math.PI;
        this.TickToComplete = ticksToComplete;
        this.ReversingAnimation = reversingAnimation;
        this.AmountToAdd = MaxDistance/TickToComplete;
        this.Offset = offset;
        this.AngleOffset = angleOffset;
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
    public double[] getAngleOffset() {
        return AngleOffset;
    }

    public boolean isReversingAnimation() {
        return ReversingAnimation;
    }
}
