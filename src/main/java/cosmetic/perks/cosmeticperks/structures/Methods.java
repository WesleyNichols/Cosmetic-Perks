package cosmetic.perks.cosmeticperks.structures;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ElytraTrails;
import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public abstract class Methods {

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

    public boolean hasActiveAnimation(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return PlayerTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING)).getProperties().getAnimation() != null
                || ElytraTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING)).getProperties().getAnimation() != null
                || ElytraTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING)).getProperties().getAnimation() != null;
    }

    public <T extends CustomTrail> void attachParticleAnimation(Player player, UUID id, String key, T e) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
       // T particleAnimations = e.(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING));
        ParticleAnimationManager.addParticleAnimation(id, e); //TODO OnPlayerJoin
        player.sendMessage(Component.text("Animations trail attached to " + player.getName()));
    }

    public void callAttachParticleAnimation(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
        attachParticleAnimation(player, player.getUniqueId(), key, PlayerTrails.valueOf((data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING))));
    }
}
