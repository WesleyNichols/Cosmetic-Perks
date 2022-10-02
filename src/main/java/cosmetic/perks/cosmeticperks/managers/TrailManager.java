package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public abstract class TrailManager {

    /**
     * Sets and stores the player's active trail
     *
     * @param e      Enum for the trail
     * @param player The player to set the trail for
     * @param key    The group of the trail
     */
    public void setActiveTrail(String e, Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, e.toUpperCase());
        player.sendMessage(Component.text("Enabled the " + e + " " + key + " trail!"));
    }

    /**
     * Removes the player's active trail
     *
     * @param player The player to remove the trail for
     * @param key    The group of the trail
     */
    public void removeActiveTrail(Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, "NONE");
        player.sendMessage(Component.text("Disabled your " + key + " trail!"));
    }

    public void removeActiveTrails(Player player) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, "NONE");
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, "NONE");
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING, "NONE");
        if(AnimationManager.hasActiveAnimation(player)){
            AnimationManager.removeParticleAnimation(player.getUniqueId());
        }
        player.sendMessage(Component.text("Disabled your trails!"));
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
    public boolean hasActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().has(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }

    /**
     * Displays particles at an Entity, given the TrailProperties to show
     *
     * @param entity The entity to display from
     * @param trailProperties The trail to display
     */
    public static void spawnParticle(Entity entity, CustomTrail.TrailProperties trailProperties) {
        World world = entity.getWorld();
        world.getPlayers().stream()
                .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                .filter(e -> e.getLocation().distance(entity.getLocation()) <= 40)
                .forEach(e -> e.spawnParticle(trailProperties.getTrailEffect(), entity.getLocation(), trailProperties.getParticleAmount(),
                        trailProperties.getXOffSet(), trailProperties.getYOffSet(), trailProperties.getZOffSet(), trailProperties.getParticleSpeed())
                );
    }
}
