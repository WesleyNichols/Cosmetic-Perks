package cosmetic.perks.cosmeticperks.enums;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum PlayerTrails implements CustomTrail{
    FIREWORKS       (Material.FIREWORK_ROCKET, "Firework Spark", Particle.FIREWORKS_SPARK,
            .1, .1, .1, .05, 3, false),
    MAGMA           (Material.MAGMA_CREAM, "Hot Magma", Particle.DRIP_LAVA,
            .2, .2, .2, 0, 10, false),
    MONSOON         (Material.WATER_BUCKET, "Monsoon", Particle.DRIP_WATER,
            .2, .2, .2, 0, 10, false),
    CORRUPTION      (Material.PURPLE_WOOL, "Corruption", Particle.SPELL_WITCH,
            .2, .2, .2, 0, 10, false),
    RAINBOW         (Material.CAULDRON, "Rainbow", Particle.SPELL_MOB,
            .2, .2, .2, .005, 15, false),
    FADING_RAINBOW  (Material.GOLD_NUGGET, "Fading Rainbow", Particle.SPELL_MOB_AMBIENT,
            .2, .2, .2, .005, 25, false),
    EMERALD         (Material.EMERALD, "Emerald", Particle.VILLAGER_HAPPY,
            .2, .2, .2, 0, 15, false),
    ORCHESTRA       (Material.NOTE_BLOCK, "Orchestra", Particle.NOTE,
            .3, .3, .3, 1, 5, false),
    FLARE           (Material.BLAZE_POWDER, "Flare", Particle.FLAME,
            .2, .2, .2, .05, 10, false),
    BRIMSTONE       (Material.LAVA_BUCKET, "Brimstone", Particle.LAVA,
            0, 0, 0, 0, 3, false),
    GUST            (Material.WHITE_WOOL, "Gust", Particle.CLOUD,
            .2, .2, .2, .05, 5, false),
    STICKY          (Material.HONEY_BLOCK, "Sticky", Particle.FALLING_HONEY,
            .2, .2, .2, 0, 8, false),
    CUPID           (Material.RED_WOOL, "Cupid", Particle.HEART,
            .2, .2, .2, 0, 1, false),
    DRAGON_BREATH   (Material.DRAGON_BREATH, "Dragon's Breath", Particle.DRAGON_BREATH,
            .2, .2, .2, 0.05, 10, false),
    STAR_LIGHT      (Material.FIRE_CHARGE, "Star Light", Particle.END_ROD,
            .2, .2, .2, 0.1, 5, false),
    ;

    private final CustomTrail.TrailProperties properties;

    PlayerTrails(Material material, String effectName, Particle trailEffect, double xOffSet, double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.properties = new CustomTrail.ImmutableProperties("player", material, effectName, trailEffect, xOffSet,  yOffSet, zOffSet, ParticleSpeed, ParticleAmount, limitedItem);
    }

    public CustomTrail.TrailProperties getProperties() {
        return properties;
    }

}
