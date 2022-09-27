package cosmetic.perks.cosmeticperks.structures;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import com.sun.tools.javac.code.Attribute;
import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
    public void setActiveTrail(PlayerTrails e, Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, e.name().toUpperCase());
        player.sendMessage(Component.text("Enabled the " + e.getEffectName() + " " + key + " trail!"));
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
     * Enables an item in the GUI(enchants it)
     *
     * @param item The GuiItem to enchant
     */
    public void enableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.getItem().setItemMeta(itemMeta);
    }

    /**
     * Disables an item in the GUI(disenchants it)
     *
     * @param item The GuiItem to disenchant
     */
    public GuiItem disableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.removeEnchant(Enchantment.DURABILITY);
        item.getItem().setItemMeta(itemMeta);
        return item;
    }

    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text(ChatColor.WHITE + "None"))
                        .build()
        );
        item.setAction(event -> {
            removeActiveTrail(player, key);
            enableItem(item);
            pages.getItems().forEach(this::disableItem);
            gui.update();
        });
        return item;
    }
}
