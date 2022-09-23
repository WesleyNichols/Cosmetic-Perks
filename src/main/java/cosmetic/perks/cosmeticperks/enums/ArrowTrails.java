package cosmetic.perks.cosmeticperks.enums;

import org.bukkit.Particle;

public enum ArrowTrails {
    FIREWORKS       (3500, "Firework Spark", Particle.FIREWORKS_SPARK,
            .1, .1, .1, .05, 3, false),
    MAGMA           (3501, "Hot Magma", Particle.DRIP_LAVA,
            .2, .2, .2, 0, 10, false),
    MONSOON         (3502, "Monsoon", Particle.DRIP_WATER,
            .2, .2, .2, 0, 10, false),
    CORRUPTION      (3503, "Corruption", Particle.SPELL_WITCH,
            .2, .2, .2, 0, 10, false),
    RAINBOW         (3504, "Rainbow", Particle.SPELL_MOB,
            .2, .2, .2, .005, 15, false),
    FADING_RAINBOW  (3505, "Fading Rainbow", Particle.SPELL_MOB_AMBIENT,
            .2, .2, .2, .005, 25, false),
    EMERALD         (3506, "Emerald", Particle.VILLAGER_HAPPY,
            .2, .2, .2, 0, 15, false),
    ORCHESTRA       (3507, "Orchestra", Particle.NOTE,
            .3, .3, .3, 1, 5, false),
    FLARE           (3508, "Flare", Particle.FLAME,
            .2, .2, .2, .05, 10, false),
    BRIMSTONE       (3509, "Brimstone", Particle.LAVA,
            0, 0, 0, 0, 3, false),
    GUST            (3510, "Gust", Particle.CLOUD,
            .2, .2, .2, .05, 5, false),
    STICKY          (3511, "Sticky", Particle.SLIME,
            .2, .2, .2, 0, 8, false),
    CUPID           (3512, "Cupid", Particle.HEART,
            .2, .2, .2, 0, 1, true),
    DRAGON_BREATH   (3513, "Dragon's Breath", Particle.DRAGON_BREATH,
            .2, .2, .2, 0.05, 10, false),
    STAR_LIGHT      (3514, "Star Light", Particle.END_ROD,
            .2, .2, .2, 0.1, 5, false),
    ;

    private final int PackageID;
    private final String EffectName;
    private final Particle TrailEffect;
    private final double XOffSet;
    private final double YOffSet;
    private final double ZOffSet;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;

    ArrowTrails(int packageID, String effectName, Particle trailEffect, double xOffSet,  double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.PackageID = packageID;
        this.EffectName = effectName;
        this.TrailEffect = trailEffect;
        this.XOffSet = xOffSet;
        this.YOffSet = yOffSet;
        this.ZOffSet = zOffSet;
        this.ParticleSpeed = ParticleSpeed;
        this.ParticleAmount = ParticleAmount;
        this.LimitedItem = limitedItem;
    }

    public int getPackageID() {
        return this.PackageID;
    }

    public String getEffectName() {
        return EffectName;
    }

    public Particle getTrailEffect() {
        return this.TrailEffect;
    }

    public double getXOffSet() {
        return XOffSet;
    }

    public double getYOffSet() {
        return YOffSet;
    }

    public double getZOffSet() {
        return ZOffSet;
    }

    public double getParticleSpeed() {
        return ParticleSpeed;
    }

    public int getParticleAmount() {
        return ParticleAmount;
    }

    public boolean isLimitedItem() {
        return LimitedItem;
    }

}
