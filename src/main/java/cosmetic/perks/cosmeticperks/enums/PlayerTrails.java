package cosmetic.perks.cosmeticperks.enums;

import cosmetic.perks.cosmeticperks.structures.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public enum PlayerTrails {
    FIREWORKS       (Material.FIREWORK_ROCKET, 3500, "Firework Spark", Particle.FIREWORKS_SPARK,
            .1, .1, .1, .05, 3, false),
    MAGMA           (Material.MAGMA_CREAM, 3501, "Hot Magma", Particle.DRIP_LAVA,
            .2, .2, .2, 0, 10, false),
    MONSOON         (Material.WATER_BUCKET, 3502, "Monsoon", Particle.DRIP_WATER,
            .2, .2, .2, 0, 10, false),
    CORRUPTION      (Material.PURPLE_WOOL, 3503, "Corruption", Particle.SPELL_WITCH,
            .2, .2, .2, 0, 10, false),
    RAINBOW         (Material.CAULDRON, 3504, "Rainbow", Particle.SPELL_MOB,
            .2, .2, .2, .005, 15, false),
    FADING_RAINBOW  (Material.GOLD_NUGGET, 3505, "Fading Rainbow", Particle.SPELL_MOB_AMBIENT,
            .2, .2, .2, .005, 25, false),
    EMERALD         (Material.EMERALD, 3506, "Emerald", Particle.VILLAGER_HAPPY,
            .2, .2, .2, 0, 15, false),
    ORCHESTRA       (Material.NOTE_BLOCK, 3507, "Orchestra", Particle.NOTE,
            .3, .3, .3, 1, 5, false),
    FLARE           (Material.BLAZE_POWDER, 3508, "Flare", Particle.FLAME,
            .2, .2, .2, .05, 10, false),
    BRIMSTONE       (Material.LAVA_BUCKET, 3509, "Brimstone", Particle.LAVA,
            0, 0, 0, 0, 3, false),
    GUST            (Material.WHITE_WOOL, 3510, "Gust", Particle.CLOUD,
            .2, .2, .2, .05, 5, false),
    STICKY          (Material.HONEY_BLOCK, 3511, "Sticky", Particle.FALLING_HONEY,
            .2, .2, .2, 0, 8, false),
    CUPID           (Material.RED_WOOL, 3512, "Cupid", Particle.HEART,
            .2, .2, .2, 0, 1, false),
    DRAGON_BREATH   (Material.DRAGON_BREATH, 3513, "Dragon's Breath", Particle.DRAGON_BREATH,
            .2, .2, .2, 0.05, 10, false),
    STAR_LIGHT      (Material.FIRE_CHARGE, 3514, "Star Light", Particle.END_ROD,
            .2, .2, .2, 0.1, 5, false),
    ;

    private final Material DisplayMaterial;
    private final int PackageID;
    private final String EffectName;
    private final Particle TrailEffect;
    private final double XOffSet;
    private final double YOffSet;
    private final double ZOffSet;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;

    PlayerTrails(Material material, int packageID, String effectName, Particle trailEffect, double xOffSet,  double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.DisplayMaterial = material;
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
        return new CustomItem.ItemBuilder(DisplayMaterial).name(Component.text(ChatColor.WHITE + EffectName)).build();
    }
    public int getPackageID() {
        return PackageID;
    }

    public String getEffectName() {
        return EffectName;
    }

    public Particle getTrailEffect() {
        return TrailEffect;
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
