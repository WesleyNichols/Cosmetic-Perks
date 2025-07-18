package me.wesleynichols.cosmeticperks.util;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.structures.TrailType;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class TrailUtils {

    public static void setActiveTrail(String trailName, Player player, TrailType trailType) {
        player.getPersistentDataContainer().set(
                new NamespacedKey(CosmeticPerks.getInstance(), trailType.getName() + "-trail"),
                PersistentDataType.STRING,
                trailName
        );
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1F, 1.25F);
    }

    public static void removeActiveTrail(Player player, TrailType trailType, boolean sound) {
        player.getPersistentDataContainer().set(
                new NamespacedKey(CosmeticPerks.getInstance(), trailType.getName() + "-trail"),
                PersistentDataType.STRING,
                "NONE"
        );
        if (sound) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1.25F);
        }
    }

    public static void removeActiveTrails(Player player) {
        for (TrailType trailType : TrailType.values()) {
            player.getPersistentDataContainer().set(
                    new NamespacedKey(CosmeticPerks.getInstance(), trailType.getName() + "-trail"),
                    PersistentDataType.STRING,
                    "NONE"
            );
        }
        if (CosmeticPerks.getInstance().getAnimationManager().hasActiveAnimation(player)) {
            CosmeticPerks.getInstance().getAnimationManager().removeParticleAnimation(player.getUniqueId());
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 0.75F);
    }

    public static String getActiveTrail(Player player, TrailType trailType) {
        return player.getPersistentDataContainer().get(
                new NamespacedKey(CosmeticPerks.getInstance(), trailType.getName() + "-trail"),
                PersistentDataType.STRING
        );
    }

    public static boolean hasActiveTrail(Player player, TrailType trailType) {
        return player.getPersistentDataContainer().has(
                new NamespacedKey(CosmeticPerks.getInstance(), trailType.getName() + "-trail"),
                PersistentDataType.STRING
        );
    }

    public static void spawnParticle(Entity entity, CustomTrail trailProperties) {
        World world = entity.getWorld();
        double[] offset = trailProperties.getOffset();
        world.getPlayers().stream()
                .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                .filter(e -> e.getLocation().distance(entity.getLocation()) <= 40)
                .forEach(e -> e.spawnParticle(
                        trailProperties.getTrailEffect(),
                        entity.getLocation(),
                        trailProperties.getParticleAmount(),
                        offset[0], offset[1], offset[2],
                        trailProperties.getParticleSpeed())
                );
    }

    /**
     * Sets a player's PersistentStorage value to "NONE" if they don't have an active trail
     * If they have an active animation, it attaches to them
     */
    public static void removeOrAttachAnimation(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        for (TrailType trailType : TrailType.values()) {
            String key = trailType.getName() + "-trail";
            String trailName = data.get(new NamespacedKey(CosmeticPerks.getInstance(), key), PersistentDataType.STRING);
            boolean hasTrail = hasActiveTrail(player, trailType);
            boolean trailExists = CosmeticPerks.getInstance().getTrailManager().getTrail(trailName) != null;
            boolean hasPermission = player.hasPermission("cosmeticperks.access");

            if (!hasTrail || !trailExists || !hasPermission) {
                removeActiveTrail(player, trailType, false);
            } else {
                CosmeticPerks.getInstance().getAnimationManager().callAttachParticleAnimation(player, trailType.getName());
            }
        }
    }
}
