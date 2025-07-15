package me.wesleynichols.cosmeticperks.structures;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

    public ItemStack itemStack() {
        if (cachedItemStack != null) {
            return cachedItemStack.clone();
        }

        ItemStack itemStack;

        // Handle music discs specially to remove song lore (annoying)
        if (material.name().startsWith("MUSIC_DISC")) {
            try {
                itemStack = Bukkit.getItemFactory().createItemStack("minecraft:" + material.name().toLowerCase() + "[!jukebox_playable]");
                itemStack.setAmount(amount);
            } catch (Exception e) {
                LOGGER.warning("Failed to create music disc without tooltip for material: " + material +
                        " - " + e.getMessage() + ". Falling back to default ItemStack.");
                itemStack = new ItemStack(material, amount);
            }
        } else {
            itemStack = new ItemStack(material, amount);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta, "ItemMeta cannot be null");

        itemMeta.displayName(name);

        if (lore != null) {
            itemMeta.lore(lore);
        }

        if (enchantments != null && levels != null && enchantments.length == levels.length) {
            for (int i = 0; i < enchantments.length; i++) {
                try {
                    itemMeta.addEnchant(enchantments[i], levels[i], true);
                } catch (Exception e) {
                    LOGGER.warning("Failed to add enchantment: " + enchantments[i] +
                            " level: " + levels[i] + " - " + e.getMessage());
                }
            }

            if (hideEnchants) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        if (isDyeable(material)) {
            try {
                LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
                if (armorColor != null) {
                    leatherMeta.setColor(armorColor);
                }
            } catch (Exception e) {
                LOGGER.warning("Failed to set leather armor color - " + e.getMessage());
            }
        }
        
        itemMeta.addItemFlags(
                ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                ItemFlag.HIDE_ARMOR_TRIM,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_STORED_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE
        );

        itemStack.setItemMeta(itemMeta);
        cachedItemStack = itemStack.clone();
        return itemStack;
    }

    private boolean isDyeable(Material material) {
        return switch (material) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, LEATHER_HORSE_ARMOR -> true;
            default -> false;
        };
    }

    public static Enchantment[] enchantArray(Enchantment... enchants) {
        return enchants;
    }

    public static int[] levelArray(int... levels) {
        return levels;
    }

    public static class ItemBuilder {
        private final Material material;
        private int amount = 1;
        private Component name;
        private List<Component> lore;
        private Enchantment[] enchantments;
        private int[] levels;
        private boolean hideEnchants = false;
        private Color armorColor;

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

        public ItemStack build() {
            CustomItem item = new CustomItem(this);
            return item.itemStack();
        }
    }
}
