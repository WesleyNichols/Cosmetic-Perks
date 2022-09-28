package cosmetic.perks.cosmeticperks.enums;

import cosmetic.perks.cosmeticperks.structures.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public enum ParticleAnimations {
    FIREWORKS       (Material.FIREWORK_ROCKET, Math.PI/37, 0.0, Math.PI,
            new String[]{"(x) * cos(16x)", "x/5 + 0.5", "(x) * sin(16x)", "-(x) * cos(16x)", "x/5 + 0.5", "-(x) * sin(16x)"},
            3500, "Firework Spark", "player-animation", Particle.TOTEM,
            0, 0.5, 0, 0, 1, false),
    YES       (Material.FIREWORK_ROCKET, Math.PI/12, 0.0, Math.PI/2,
            new String[]{"(x) * cos(16x)", "x/5", "(x) * sin(16x)", "-(x) * cos(16x)", "x/5", "-(x) * sin(16x)"},
            3500, "Firework Spark", "projectile-animation", Particle.TOTEM,
            0, 0.5, 0, 0, 1, false)

    ;

    private final Material DisplayMaterial;
    private final double DistanceBetweenParticles;
    private double CurrentDistance;
    private final double MaxDistance;
    private final String[] EquationList;
    private final int PackageID;
    private final String EffectName;
    private final String Key;
    private final Particle TrailEffect;
    private final double XOffSet;
    private final double YOffSet;
    private final double ZOffSet;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;

    ParticleAnimations(Material material, double distanceBetweenParticles, double currentDistance, double maxDistance, String[] equationList, int packageID, String effectName, String key, Particle trailEffect, double xOffSet, double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
        this.DisplayMaterial = material;
        this.DistanceBetweenParticles = distanceBetweenParticles;
        this.CurrentDistance = currentDistance;
        this.MaxDistance = maxDistance;
        this.EquationList = equationList;
        this.PackageID = packageID;
        this.EffectName = effectName;
        this.Key = key;
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

    public int getPackageID() {
        return this.PackageID;
    }

    public String getEffectName() {
        return EffectName;
    }

    public String getKey() {
        return this.Key;
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
