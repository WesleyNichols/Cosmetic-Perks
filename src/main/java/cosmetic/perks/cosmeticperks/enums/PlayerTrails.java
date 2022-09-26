package cosmetic.perks.cosmeticperks.enums;

import cosmetic.perks.cosmeticperks.structures.CustomItem;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public enum PlayerTrails {
    FIREWORKS       (new CustomItem.ItemBuilder(Material.FIREWORK_ROCKET).build(), 3500, "Firework Spark", Particle.FIREWORKS_SPARK,
            .1, .1, .1, .05, 3, false),
    MAGMA           (new CustomItem.ItemBuilder(Material.MAGMA_CREAM).build(), 3501, "Hot Magma", Particle.DRIP_LAVA,
            .2, .2, .2, 0, 10, false),
    MONSOON         (new CustomItem.ItemBuilder(Material.WATER_BUCKET).build(), 3502, "Monsoon", Particle.DRIP_WATER,
            .2, .2, .2, 0, 10, false),
    CORRUPTION      (new CustomItem.ItemBuilder(Material.PURPLE_WOOL).build(), 3503, "Corruption", Particle.SPELL_WITCH,
            .2, .2, .2, 0, 10, false),
    RAINBOW         (new CustomItem.ItemBuilder(Material.NETHER_PORTAL).build(), 3504, "Rainbow", Particle.SPELL_MOB,
            .2, .2, .2, .005, 15, false),
    FADING_RAINBOW  (new CustomItem.ItemBuilder(Material.END_PORTAL).build(), 3505, "Fading Rainbow", Particle.SPELL_MOB_AMBIENT,
            .2, .2, .2, .005, 25, false),
    EMERALD         (new CustomItem.ItemBuilder(Material.EMERALD).build(), 3506, "Emerald", Particle.VILLAGER_HAPPY,
            .2, .2, .2, 0, 15, false),
    ORCHESTRA       (new CustomItem.ItemBuilder(Material.NOTE_BLOCK).build(), 3507, "Orchestra", Particle.NOTE,
            .3, .3, .3, 1, 5, false),
    FLARE           (new CustomItem.ItemBuilder(Material.FIREWORK_STAR).build(), 3508, "Flare", Particle.FLAME,
            .2, .2, .2, .05, 10, false),
    BRIMSTONE       (new CustomItem.ItemBuilder(Material.LAVA_BUCKET).build(), 3509, "Brimstone", Particle.LAVA,
            0, 0, 0, 0, 3, false),
    GUST            (new CustomItem.ItemBuilder(Material.WHITE_WOOL).build(), 3510, "Gust", Particle.CLOUD,
            .2, .2, .2, .05, 5, false),
    STICKY          (new CustomItem.ItemBuilder(Material.HONEY_BLOCK).build(), 3511, "Sticky", Particle.FALLING_HONEY,
            .2, .2, .2, 0, 8, false),
    CUPID           (new CustomItem.ItemBuilder(Material.RED_WOOL).build(), 3512, "Cupid", Particle.HEART,
            .2, .2, .2, 0, 1, false),
    DRAGON_BREATH   (new CustomItem.ItemBuilder(Material.DRAGON_BREATH).build(), 3513, "Dragon's Breath", Particle.DRAGON_BREATH,
            .2, .2, .2, 0.05, 10, false),
    STAR_LIGHT      (new CustomItem.ItemBuilder(Material.NETHER_STAR).build(), 3514, "Star Light", Particle.END_ROD,
            .2, .2, .2, 0.1, 5, false),
    ;

    private final ItemStack Item;
    private final int PackageID;
    private final String EffectName;
    private final Particle TrailEffect;
    private final double XOffSet;
    private final double YOffSet;
    private final double ZOffSet;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;

    PlayerTrails(ItemStack item, int packageID, String effectName, Particle trailEffect, double xOffSet,  double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.Item = item;
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

    public ItemStack getItem() {
        return this.Item;
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
