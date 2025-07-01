package me.wesleynichols.cosmeticperks.structures;

import me.wesleynichols.cosmeticperks.menus.CosmeticsMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CustomTrail implements Comparable<CustomTrail>{

    private final String TrailType;
    private final Material DisplayMaterial;
    private final String EffectName;
    private final Particle TrailEffect;
    private final double[] Offset;
    private final double ParticleSpeed;
    private final int ParticleAmount;
    private final boolean LimitedItem;
    private final AnimationValues Animation;

    public CustomTrail(CustomTrailBuilder builder) {
        this.TrailType = builder.trailType;
        this.DisplayMaterial = builder.displayMaterial;
        this.EffectName = builder.effectName;
        this.TrailEffect = builder.trailEffect;
        this.Offset = builder.offset;
        this.ParticleSpeed = builder.particleSpeed;
        this.ParticleAmount = builder.particleAmount;
        this.LimitedItem = builder.limitedItem;
        this.Animation = builder.animation;
    }

    public ItemStack getItem() {
        return new CustomItem.ItemBuilder(DisplayMaterial)
                .name(Component.text(EffectName).color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                .lore(Arrays.asList(
                        Component.empty()
                                .append(Component.text(getAnimation() != null ? "Animated " : "", NamedTextColor.GOLD))
                                .append(Component.text(CosmeticsMenu.capitalize(TrailType) + " Trail", NamedTextColor.YELLOW))
                                .decorate(TextDecoration.ITALIC),
                        Component.empty(),
                        Component.text("Click to Select", NamedTextColor.RED)))
                .build();
    }

    public String getTrailName() {
        return EffectName;
    }

    public Particle getTrailEffect() {
        return TrailEffect;
    }

    public double[] getOffset() {
        return Offset;
    }

    public double getParticleSpeed() {
        return ParticleSpeed;
    }

    public int getParticleAmount() {
        return ParticleAmount;
    }

    public AnimationValues getAnimation() {
        return Animation;
    }

    public boolean isLimitedItem() {
        return LimitedItem;
    }

    @Override
    public int compareTo(@NotNull CustomTrail o) {
        return this.getTrailName().compareTo(o.getTrailName());
    }

    public static class CustomTrailBuilder {
        private final String trailType;
        private Material displayMaterial;
        private final String effectName;
        private Particle trailEffect;
        private double[] offset;
        private double particleSpeed;
        private int particleAmount;
        private boolean limitedItem;
        private AnimationValues animation;

        public CustomTrailBuilder(String trailType, String effectName) {
            this.trailType = trailType;
            this.effectName = effectName;
        }

        public CustomTrailBuilder displayMaterial(Material material) {
            this.displayMaterial = material;
            return this;
        }

        public CustomTrailBuilder trailEffect(Particle trailEffect) {
            this.trailEffect = trailEffect;
            return this;
        }

        public CustomTrailBuilder offset(double[] offset) {
            this.offset = offset;
            return this;
        }

        public CustomTrailBuilder particleSpeed(double speed) {
            this.particleSpeed = speed;
            return this;
        }

        public CustomTrailBuilder particleAmount(int amount) {
            this.particleAmount = amount;
            return this;
        }

        public CustomTrailBuilder limitedItem(boolean value) {
            this.limitedItem = value;
            return this;
        }

        public CustomTrailBuilder animation(AnimationValues values) {
            this.animation = values;
            return this;
        }

        public CustomTrail build() {
            if(this.trailEffect == null) {this.trailEffect = Particle.SMOKE;}
            if(this.displayMaterial == null) {this.displayMaterial = Material.WHITE_WOOL;}
            if(this.offset == null) {this.offset = new double[]{0,0,0};}
            return new CustomTrail(this);
        }
    }
}

