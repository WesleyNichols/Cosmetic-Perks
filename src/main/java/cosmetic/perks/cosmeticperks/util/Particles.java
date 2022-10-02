package cosmetic.perks.cosmeticperks.util;

import org.bukkit.Particle;

public class Particles {
    private final Particle ParticleEffect;
    private final double[][] ParticleValues;

    public Particles(Particle particleEffect, double[][] particleValues) {
        this.ParticleEffect = particleEffect;
        this.ParticleValues = particleValues;
    }

    public Particle getParticleEffect() {
        return ParticleEffect;
    }

    public double[][] getParticleValues() {
        return ParticleValues;
    }
}
