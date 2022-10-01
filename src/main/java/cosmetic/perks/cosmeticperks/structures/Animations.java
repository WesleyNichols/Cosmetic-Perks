package cosmetic.perks.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class Animations {

    private final String[] EquationList;
    private final double DistanceToAdd;
    private final double MaxDistance;
    private double CurrentDistance;

    public Animations(String[] equationList, int distanceToAdd, double maxDistance) {
        this.EquationList = equationList;
        Bukkit.broadcast(Component.text(distanceToAdd));
        this.DistanceToAdd = Math.PI/distanceToAdd;
        Bukkit.broadcast(Component.text(DistanceToAdd));
        this.MaxDistance = maxDistance * Math.PI;
        this.CurrentDistance = 0.0;
    }

    public void addToCurrentDistance() {
        CurrentDistance += DistanceToAdd;
    }

    public void resetCurrentDistance() {
        CurrentDistance = 0.0;
    }

    public double getMaxDistance() {
        return MaxDistance;
    }

    public double getCurrentDistance() {
        return CurrentDistance;
    }

    public String[] getEquationList() {
        return EquationList;
    }
}
