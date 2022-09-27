package cosmetic.perks.cosmeticperks.enums;

import cosmetic.perks.cosmeticperks.structures.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public enum ParticleAnimations {
    FIREWORKS       (Material.FIREWORK_ROCKET, Math.PI/8, new String[]{"cos(x)", "sin(x) + 1", "sin(x)"}, 3500, "Firework Spark", Particle.TOTEM,
            .1, .1, .1, .05, 3, false)
    ;

    private final Material DisplayMaterial;
    private final double DistanceBetweenParticles;
    private final String[] EquationList;
    private final int PackageID;
    private final String EffectName;
    private final Particle TrailEffect;
    private final double XOffSet;
    private final double YOffSet;
    private final double ZOffSet;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;

    ParticleAnimations(Material material, double distanceBetweenParticles, String[] equationList, int packageID, String effectName, Particle trailEffect, double xOffSet, double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.DisplayMaterial = material;
        this.DistanceBetweenParticles = distanceBetweenParticles;
        this.EquationList = equationList;
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

    public double getDistanceBetweenParticles() {
        return this.DistanceBetweenParticles;
    }

    public String[] getEquationList() {
        return this.EquationList;
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
