package cosmetic.perks.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class GuiItem {

//        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
//        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        if(material.equals(Material.LEATHER_BOOTS) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_HELMET)){
//            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
//        }

    public ItemStack guiItem(@NotNull Material material, @NotNull Component name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack guiItem(@NotNull Material material, @NotNull Component name, @NotNull Enchantment enchant, @NotNull Integer enchantLevel, @NotNull Boolean hideEnchant) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.addEnchant(enchant, enchantLevel, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(hideEnchant.equals(true)) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

}
