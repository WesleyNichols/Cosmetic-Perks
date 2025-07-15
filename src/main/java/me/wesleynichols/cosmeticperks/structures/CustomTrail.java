package me.wesleynichols.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a custom particle trail effect that can be applied to players or projectiles.
 * Supports static or animated particle effects.
 */
public class CustomTrail implements Comparable<CustomTrail> {

    private final String trailType;
    private final Material displayMaterial;
    private final String effectName;
    private final Particle trailEffect;
    private final double[] offset;
    private final double particleSpeed;
    private final int particleAmount;
    private final boolean limitedItem;
    private final AnimationValues animation;

    public CustomTrail(CustomTrailBuilder builder) {
        this.trailType = Objects.requireNonNull(builder.trailType, "trailType cannot be null");
        this.displayMaterial = builder.displayMaterial != null ? builder.displayMaterial : Material.WHITE_WOOL;
        this.effectName = Objects.requireNonNull(builder.effectName, "effectName cannot be null");
        this.trailEffect = builder.trailEffect != null ? builder.trailEffect : Particle.SMOKE;
        this.offset = builder.offset != null ? builder.offset.clone() : new double[]{0.0, 0.0, 0.0};
        this.particleSpeed = builder.particleSpeed;
        this.particleAmount = builder.particleAmount;
        this.limitedItem = builder.limitedItem;
        this.animation = builder.animation;
    }

    /**
     * Creates an ItemStack representing this trail with formatted name and lore.
     *
     * @return an ItemStack for this trail.
     */
    public ItemStack getItem() {
        return new CustomItem.ItemBuilder(displayMaterial)
                .name(Component.text(effectName).color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                .lore(Arrays.asList(
                        Component.empty()
                                .append(Component.text(animation != null ? "Animated " : "", NamedTextColor.GOLD))
                                .append(Component.text(capitalize(trailType) + " Trail", NamedTextColor.YELLOW))
                                .decorate(TextDecoration.ITALIC),
                        Component.empty(),
                        Component.text("Click to Select", NamedTextColor.RED)))
                .build();
    }

    public String getTrailName() {
        return effectName;
    }

    public Particle getTrailEffect() {
        return trailEffect;
    }

    public double[] getOffset() {
        return offset.clone();
    }

    public double getParticleSpeed() {
        return particleSpeed;
    }

    public int getParticleAmount() {
        return particleAmount;
    }

    public AnimationValues getAnimation() {
        return animation;
    }

    public boolean isLimitedItem() {
        return limitedItem;
    }

    @Override
    public int compareTo(@NotNull CustomTrail other) {
        return this.getTrailName().compareTo(other.getTrailName());
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase(Locale.ENGLISH) + input.substring(1).toLowerCase(Locale.ENGLISH);
    }

    /**
     * Builder class for constructing CustomTrail instances.
     */
    public static class CustomTrailBuilder {
        private final String trailType;
        private Material displayMaterial;
        private final String effectName;
        private Particle trailEffect;
        private double[] offset;
        private double particleSpeed = 0.0;
        private int particleAmount = 1;
        private boolean limitedItem = false;
        private AnimationValues animation;

        /**
         * Creates a builder with required trailType and effectName.
         *
         * @param trailType the category or type of the trail (e.g. "player", "projectile")
         * @param effectName the display name of the trail
         */
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
            this.offset = offset != null ? offset.clone() : null;
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

        public CustomTrailBuilder limitedItem(boolean limited) {
            this.limitedItem = limited;
            return this;
        }

        public CustomTrailBuilder animation(AnimationValues animation) {
            this.animation = animation;
            return this;
        }

        /**
         * Builds and returns the CustomTrail instance.
         * If optional values are not set, reasonable defaults are applied.
         *
         * @return a new CustomTrail instance
         */
        public CustomTrail build() {
            return new CustomTrail(this);
        }
    }
}
