package cosmetic.perks.cosmeticperks.structures;

public class Animations {

    private final String[] EquationList;
    private double DistanceToAdd;
    private double MaxDistance;
    private double CurrentDistance = 0.0;

    public Animations(String[] equationList, double distanceToAdd, double maxDistance) {
        this.EquationList = equationList;
        this.DistanceToAdd = Math.PI/distanceToAdd;
        this.MaxDistance = maxDistance * Math.PI;
    }

    public double getDistanceBetweenParticles() {
        return this.DistanceToAdd;
    }

    public void addToCurrentDistance() {
        this.CurrentDistance += getDistanceBetweenParticles();
    }

    public void resetCurrentDistance() {
        this.CurrentDistance = 0.0;
    }

    public double getMaxDistance() {
        return this.MaxDistance;
    }

    public double getCurrentDistance() {
        return this.CurrentDistance;
    }

    public String[] getEquationList() {
        return this.EquationList;
    }
}
