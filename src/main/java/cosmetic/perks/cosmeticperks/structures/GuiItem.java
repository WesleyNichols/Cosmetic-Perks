package cosmetic.perks.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Builder;


public class GuiItem {

    private Material material;
    private int amount;
    private Component name;
    private Enchantment[] enchantments;
    private int[] levels;

    public GuiItem(ItemBuilder builder) {
        this.material = builder.material;
        this.amount = builder.amount;
        this.enchantments = builder.enchantments;
        this.levels = builder.levels;
    }

    public ItemStack itemStack() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

//        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
//        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        if(material.equals(Material.LEATHER_BOOTS) || material.equals(Material.LEATHER_LEGGINGS) || material.equals(Material.LEATHER_CHESTPLATE) || material.equals(Material.LEATHER_HELMET)){
//            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
//        }

//    public ItemStack guiItem(@NotNull Material material, @NotNull Component name) {
//        ItemStack item = new ItemStack(material);
//        ItemMeta itemMeta = item.getItemMeta();
//        itemMeta.displayName(name);
//        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        item.setItemMeta(itemMeta);
//        return item;
//    }
//
//    public ItemStack guiItem(@NotNull Material material, @NotNull Component name, @NotNull Enchantment enchant, @NotNull Integer enchantLevel, @NotNull Boolean hideEnchant) {
//        ItemStack item = new ItemStack(material);
//        ItemMeta itemMeta = item.getItemMeta();
//        itemMeta.displayName(name);
//        itemMeta.addEnchant(enchant, enchantLevel, true);
//        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        if(hideEnchant.equals(true)) {
//            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        }
//        item.setItemMeta(itemMeta);
//        return item;
//    }
//
//    public

}

class ItemBuilder {
    public Material material;
    public int amount;
    public Component name;
    public Enchantment[] enchantments;
    public int[] levels;
    public boolean hideEnchants;

    public ItemBuilder(Material material) {
        this.material = material;
        this.amount = 1;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public ItemBuilder enchantments(Enchantment[] enchantments, int[] levels) {
        this.enchantments = enchantments;
        this.levels = levels;
        return this;
    }

    public ItemBuilder hideEnchants(boolean hideEnchants) {
        this.hideEnchants = hideEnchants;
        return this;
    }

    public GuiItem build() {
        GuiItem item = new GuiItem(this);
        return item;
    }
}