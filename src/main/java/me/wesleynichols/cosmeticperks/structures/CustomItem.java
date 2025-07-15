package me.wesleynichols.cosmeticperks.structures;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a customizable Minecraft item with properties such as material,
 * amount, display name, lore, enchantments, and leather armor color.
 * Uses the Builder pattern for flexible construction.
 */
public class CustomItem {

    private static final Logger LOGGER = CosmeticPerks.getInstance().getLogger();

    private final Material material;
    private final int amount;
    private final Component name;
    private final List<Component> lore;
    private final Enchantment[] enchantments;
    private final int[] levels;
    private final boolean hideEnchants;
    private final Color armorColor;

    // Cache the built ItemStack for repeated calls (optional)
    private ItemStack cachedItemStack;

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

    /**
     * Builds and returns an ItemStack with the configured properties.
     * Caches the result for future calls.
     *
     * @return the constructed ItemStack
     */
    public ItemStack itemStack() {
        if (cachedItemStack != null) {
            return cachedItemStack.clone();
        }

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta, "ItemMeta cannot be null");

        itemMeta.displayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (lore != null) {
            itemMeta.lore(null);
            itemMeta.lore(lore);
        }

        if (enchantments != null && levels != null && enchantments.length == levels.length) {
            for (int i = 0; i < enchantments.length; i++) {
                try {
                    itemMeta.addEnchant(enchantments[i], levels[i], true);
                } catch (Exception e) {
                    LOGGER.warning("Failed to add enchantment: " + enchantments[i] + " level: " + levels[i] + " - " + e.getMessage());
                }
            }

            if (hideEnchants) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        if (isLeatherArmor(material)) {
            try {
                LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
                if (armorColor != null) {
                    leatherMeta.setColor(armorColor);
                }
                leatherMeta.addItemFlags(ItemFlag.HIDE_DYE);
            } catch (Exception e) {
                LOGGER.warning("Failed to set leather armor color - " + e.getMessage());
            }
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);

        cachedItemStack = itemStack.clone(); // Cache a clone to avoid side effects
        return itemStack;
    }

    private boolean isLeatherArmor(Material material) {
        return material == Material.LEATHER_BOOTS ||
                material == Material.LEATHER_LEGGINGS ||
                material == Material.LEATHER_CHESTPLATE ||
                material == Material.LEATHER_HELMET;
    }

    /**
     * Helper method to create an array of enchantments.
     */
    public static Enchantment[] enchantArray(Enchantment... enchants) {
        return enchants;
    }

    /**
     * Helper method to create an array of enchantment levels.
     */
    public static int[] levelArray(int... levels) {
        return levels;
    }

    /**
     * Builder class for creating CustomItem instances.
     */
    public static class ItemBuilder {
        private final Material material;
        private int amount = 1;
        private Component name;
        private List<Component> lore;
        private Enchantment[] enchantments;
        private int[] levels;
        private boolean hideEnchants = false;
        private Color armorColor;

        /**
         * Creates a builder with a required material.
         *
         * @param material the material type of the item
         */
        public ItemBuilder(Material material) {
            this.material = material;
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

        /**
         * Builds the CustomItem and returns the constructed ItemStack.
         *
         * @return constructed ItemStack
         */
        public ItemStack build() {
            CustomItem item = new CustomItem(this);
            return item.itemStack();
        }
    }
}
