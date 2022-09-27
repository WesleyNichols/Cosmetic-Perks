package cosmetic.perks.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.structures.CustomItem;
import cosmetic.perks.cosmeticperks.structures.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;


public class CosmeticsMenu extends Methods {

    /**
     * Method to display the cosmetic menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayCosmeticsMenu(Player player){
        ChestGui gui = new ChestGui(3, "Cosmetics Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);

        //region Player Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.LEATHER_BOOTS)
                        .amount(1)
                        .name(Component.text(ChatColor.GREEN + "Player Trails"))
                        .enchantments(enchantArray(Enchantment.DURABILITY), levelArray(1))
                        .hideEnchants(true)
                        .armorColor(Color.GREEN)
                        .build(),
                event -> displayPlayerMenu(player))
        );
       //endregion

       //region Projectile Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.SPECTRAL_ARROW)
                        .amount(25)
                        .name(Component.text(ChatColor.GOLD + "Projectile Trails"))
                        .build(),
                event -> displayProjectileMenu(player))
        );
        //endregion

        //region Elytra Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ELYTRA)
                        .name(Component.text(ChatColor.LIGHT_PURPLE + "Elytra Trails"))
                        .enchantments(enchantArray(Enchantment.DURABILITY), levelArray(1))
                        .hideEnchants(true)
                        .build(),
                event -> displayElytraMenu(player))
        );
        //endregion

        gui.addPane(navigationPane);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the player trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayPlayerMenu(Player player) {
        ChestGui gui = new ChestGui( 6, "Player Trails");
        PaginatedPane pages = new PaginatedPane(0, 0, 9, Math.min(5, (int)Math.floor(PlayerTrails.values().length/9))+1);

        List<GuiItem> guiItems = new ArrayList<>();
        guiItems.add(getDefaultGuiItem(player, pages, gui, "player-trail"));

        for (PlayerTrails playerTrails : PlayerTrails.values()) {
            GuiItem item = new GuiItem(playerTrails.getItem());
            item.setAction(event -> {
                pages.getItems().forEach(this::disableItem);
                enableItem(item);
                gui.update();
            });
            guiItems.add(item);
        }

        pages.populateWithGuiItems(guiItems);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, gui.getRows(), Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);
        gui.addPane(pages);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the projectile trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayProjectileMenu(Player player) {
        ChestGui gui = new ChestGui(3, "Projectile Trails");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the elytra trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayElytraMenu(Player player) {
        ChestGui gui = new ChestGui(3, "Elytra Trails");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);
        gui.update();
        gui.show(player);
    }

    //  Unused Toggle function, decided against it but wanted to retain the semi-functional method (doesn't actually ever toggle off enchantment)
//    public GuiItem toggleSelected(GuiItem guiItem, Player player, PlayerTrails playerTrails, String key) {
//        ItemMeta itemMeta = guiItem.getItem().getItemMeta();
//
//        if (itemMeta.hasEnchants()) {
//            disableItem(guiItem);
//            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key), PersistentDataType.STRING, "NONE" );
//        } else {
//            enableItem(guiItem);
//            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key), PersistentDataType.STRING, playerTrails.name());
//        }
//        return guiItem;
//    }

    public GuiItem enableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        item.getItem().setItemMeta(itemMeta);
        return item;
    }

    public GuiItem disableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.removeEnchant(Enchantment.DURABILITY);
        item.getItem().setItemMeta(itemMeta);
        return item;
    }


    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(new ItemStack(Material.BARRIER));
        item.setAction(event -> {
            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key), PersistentDataType.STRING, "NONE");
            pages.getItems().forEach(this::disableItem);
            enableItem(item);
            gui.update();
        });
        return item;
    }
}
