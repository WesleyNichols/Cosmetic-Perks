package cosmetic.perks.cosmeticperks.structures;

import org.bukkit.Particle;

public class Animations {

    private final Particle ParticleEffect;
    private final String[] EquationList;
    private final double AmountToAdd;
    private final double MaxDistance;
    private final boolean ReversingAnimation;
    private double CurrentDistance;
    private int CurrentStep;
    private boolean Reversed;
    private final int TickToComplete;

    public Animations(Particle particleEffect, String[] equationList, int ticksToComplete, double maxDistance, boolean reversingAnimation) {
        this.ParticleEffect = particleEffect;
        this.EquationList = equationList;
        this.MaxDistance = maxDistance * Math.PI;
        this.TickToComplete = ticksToComplete;
        this.ReversingAnimation = reversingAnimation;
        this.AmountToAdd = MaxDistance/TickToComplete;
        this.CurrentDistance = 0.0;
        this.CurrentStep = 0;
        this.Reversed = false;
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

    public Particle getParticleEffect() {
        return ParticleEffect;
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

    public boolean isReversingAnimation() {
        return ReversingAnimation;
    }
}
