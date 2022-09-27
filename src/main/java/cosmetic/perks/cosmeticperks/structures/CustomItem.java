package cosmetic.perks.cosmeticperks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;


public class CustomItem {

    private Material material;
    private int amount;
    private Component name;
    private List<Component> lore;
    private Enchantment[] enchantments;
    private int[] levels;
    private boolean hideEnchants;
    private org.bukkit.Color armorColor;

    private CustomItem(ItemBuilder builder) {
        this.material = builder.material;
        this.amount = builder.amount;
        this.name = builder.name;
        this.lore = builder.lore;
        this.enchantments = builder.enchantments;
        this.levels = builder.levels;
        this.hideEnchants = builder.hideEnchants;
        this.armorColor = builder.armorColor;
    }

    public ItemStack itemStack() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);

        if (lore != null) {
            itemMeta.lore(lore);
        }

        if (enchantments != null) {
            for (int i=0; i<=enchantments.length-1; i++) {
                try {
                    itemMeta.addEnchant(enchantments[i], levels[i], true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (hideEnchants) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        if (material == Material.LEATHER_BOOTS || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_HELMET) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
            try {
                leatherArmorMeta.setColor(armorColor);
                leatherArmorMeta.addItemFlags(ItemFlag.HIDE_DYE);
                itemMeta = leatherArmorMeta;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static class ItemBuilder {
        private Material material;
        private int amount;
        private Component name;
        private List<Component> lore;
        private Enchantment[] enchantments;
        private int[] levels;
        private boolean hideEnchants;
        private org.bukkit.Color armorColor;

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

        public ItemBuilder lore(List<Component> lore) {
            this.lore = lore;
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

        public ItemBuilder armorColor(Color armorColor) {
            this.armorColor = armorColor;
            return this;
        }

        public ItemStack build() {
            CustomItem item = new CustomItem(this);
            return item.itemStack();
        }
    }
}