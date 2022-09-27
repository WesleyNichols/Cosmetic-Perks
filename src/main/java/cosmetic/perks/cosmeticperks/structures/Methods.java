package cosmetic.perks.cosmeticperks.structures;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public abstract class Methods {

    /**
     * Returns an array of enchantments
     *
     * @param enchants Enchants
     */
    public Enchantment[] enchantArray(Enchantment... enchants) {
        return enchants;
    }

    /**
     * Returns an array of integers for the enchant levels
     *
     * @param i Integers
     */
    public int[] levelArray(int... i) {
        return i;
    }

    /**
     * Sets and stores the player's active trail
     *
     * @param e Enum for the trail
     * @param player The player to set the trail for
     * @param key The group of the trail
     */
    public void setActiveTrail(String e, Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, e.toUpperCase());
        player.sendMessage(Component.text("Enabled the " + e + " " + key + " trail!"));
    }

    /**
     * Removes the player's active trail
     *
     * @param player The player to remove the trail for
     * @param key The group of the trail
     */
    public void removeActiveTrail(Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, "NONE");
        player.sendMessage(Component.text("Disabled your " + key + " trail!"));
    }

    /**
     * Gets the player's active trail
     *
     * @param player The player to get the active trail for
     * @param key The group of the trail
     */
    public String getActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }

    /**
     * Checks if a player has a trail or not
     *
     * @param player The player to get the active trail for
     * @param key The group of the trail
     */
    public boolean hasActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().has(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }
}
