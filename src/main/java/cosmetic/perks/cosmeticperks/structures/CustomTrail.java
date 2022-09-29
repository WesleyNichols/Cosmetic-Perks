package cosmetic.perks.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public interface CustomTrail {

    TrailProperties getProperties();

    interface TrailProperties {
        String getTrailType();
        ItemStack getItem();
        String getEffectName();
        Particle getTrailEffect();
        double getXOffSet();
        double getYOffSet();
        double getZOffSet();
        double getParticleSpeed();
        int getParticleAmount();
        boolean isLimitedItem();
    }

    final class ImmutableProperties implements TrailProperties {

        private final String TrailType;
        private final Material DisplayMaterial;
        private final String EffectName;
        private final Particle TrailEffect;
        private final double XOffSet;
        private final double YOffSet;
        private final double ZOffSet;
        private final double ParticleSpeed;
        private final int ParticleAmount;
        private final boolean LimitedItem;

        public ImmutableProperties(String type, Material material, String effectName, Particle trailEffect, double xOffSet,  double yOffSet, double zOffSet, double ParticleSpeed, int ParticleAmount, boolean limitedItem) {
            this.TrailType = type;
            this.DisplayMaterial = material;
            this.EffectName = effectName;
            this.TrailEffect = trailEffect;
            this.XOffSet = xOffSet;
            this.YOffSet = yOffSet;
            this.ZOffSet = zOffSet;
            this.ParticleSpeed = ParticleSpeed;
            this.ParticleAmount = ParticleAmount;
            this.LimitedItem = limitedItem;
        }

        @Override
        public String getTrailType() {
            return TrailType;
        }

        @Override
        public ItemStack getItem() {
            return new CustomItem.ItemBuilder(DisplayMaterial).name(Component.text(ChatColor.WHITE + EffectName)).build();
        }

        @Override
        public String getEffectName() {
            return EffectName;
        }

        @Override
        public Particle getTrailEffect() {
            return this.TrailEffect;
        }

        @Override
        public double getXOffSet() {
            return XOffSet;
        }

        @Override
        public double getYOffSet() {
            return YOffSet;
        }

        @Override
        public double getZOffSet() {
            return ZOffSet;
        }

        @Override
        public double getParticleSpeed() {
            return ParticleSpeed;
        }

        @Override
        public int getParticleAmount() {
            return ParticleAmount;
        }

        @Override
        public boolean isLimitedItem() {
            return LimitedItem;
        }
    }
}
