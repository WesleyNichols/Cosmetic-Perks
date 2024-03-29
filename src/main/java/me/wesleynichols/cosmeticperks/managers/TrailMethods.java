package me.wesleynichols.cosmeticperks.managers;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class TrailMethods {

    /**
     * Sets and stores the player's active trail
     *
     * @param e      Enum for the trail
     * @param player The player to set the trail for
     * @param key    The group of the trail
     */
    public void setActiveTrail(String e, Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, e);
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1F, 1.25F);
    }

    /**
     * Removes the player's active trail
     *
     * @param player The player to remove the trail for
     * @param key    The group of the trail
     */
    public static void removeActiveTrail(Player player, String key, boolean sound) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, "NONE");
        if (sound) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1.25F);
        }
    }

    public void removeActiveTrails(Player player) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, "NONE");
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, "NONE");
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING, "NONE");
        if(AnimationManager.hasActiveAnimation(player)){
            AnimationManager.removeParticleAnimation(player.getUniqueId());
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 0.75F);
    }

    /**
     * Gets the player's active trail
     *
     * @param player The player to get the active trail for
     * @param key    The group of the trail
     */
    public String getActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }

    /**
     * Checks if a player has a trail or not
     *
     * @param player The player to get the active trail for
     * @param key    The group of the trail
     */
    public static boolean hasActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().has(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }

    /**
     * Displays particles at an Entity, given the TrailProperties to show
     *
     * @param entity The entity to display from
     * @param trailProperties The trail to display
     */
    public static void spawnParticle(Entity entity, CustomTrail trailProperties) {
        World world = entity.getWorld();
        double[] offset = trailProperties.getOffset();
        world.getPlayers().stream()
                .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                .filter(e -> e.getLocation().distance(entity.getLocation()) <= 40)
                .forEach(e -> e.spawnParticle(trailProperties.getTrailEffect(), entity.getLocation(), trailProperties.getParticleAmount(),
                        offset[0], offset[1], offset[2], trailProperties.getParticleSpeed())
                );
    }

    /**
     * Sets a player's PersistentStorage value to "NONE" if they do not have an active trail
     * If they have an active animation, it attaches that animation to them
     *
     * @param player The player to remove or attach a trail for
     */
    public static void removeOrAttachAnimation(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        for(String trail: new String[]{"player", "projectile", "elytra"}) {
            if (!hasActiveTrail(player, trail) ||
                    TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), trail + "-trail"), PersistentDataType.STRING)) == null ||
                    !player.hasPermission("cosmeticperks.access")) { removeActiveTrail(player, trail, false); }
            else { AnimationManager.callAttachParticleAnimation(player, trail); }
        }
    }
}
