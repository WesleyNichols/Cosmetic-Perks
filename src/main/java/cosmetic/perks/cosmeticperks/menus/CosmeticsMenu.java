package cosmetic.perks.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import cosmetic.perks.cosmeticperks.structures.CustomItem;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import cosmetic.perks.cosmeticperks.managers.TrailMethods;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CosmeticsMenu extends TrailMethods {

    public final ItemStack filler = new CustomItem.ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).build();

    /**
     * Method to display the cosmetic menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayCosmeticsMenu(Player player){
        ChestGui gui = new ChestGui(4, "Cosmetics Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 3, 9, 1, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(filler));
        background.setRepeat(true);
        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);

        //  region Player Trails
        String playerTrail = getActiveTrail(player, "player");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.LEATHER_BOOTS)
                        .name(Component.text(ChatColor.GREEN + "" + ChatColor.BOLD + "Player Trails"))
                        .armorColor(Color.GREEN)
                        .lore(Arrays.asList(
                                Component.text(""),
                                Component.text(ChatColor.YELLOW + "Current:"),
                                Component.text(ChatColor.DARK_GREEN + (playerTrail.substring(0, 1).toUpperCase() + playerTrail.substring(1).toLowerCase()))))
                        .build(),
                event -> {
                    if (!player.hasPermission("group.donator")) { player.performCommand("shop cosmetic"); }
                    else { displayPlayerMenu(player); }
                })
        );
       //   endregion

       //   region Projectile Trails
        String projTrail = getActiveTrail(player, "projectile");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.SPECTRAL_ARROW)
                        .name(Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Projectile Trails"))
                        .lore(Arrays.asList(
                                Component.text(""),
                                Component.text(ChatColor.YELLOW + "Current:"),
                                Component.text(ChatColor.DARK_GREEN + (projTrail.substring(0, 1).toUpperCase() + projTrail.substring(1).toLowerCase()))))
                        .build(),
                event -> {
                    if (!player.hasPermission("group.donator")) { player.performCommand("shop cosmetic"); }
                    else { displayProjectileMenu(player); }
                })
        );
        //  endregion

        //  region Elytra Trails
        String elytraTrail = getActiveTrail(player, "elytra");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ELYTRA)
                        .name(Component.text(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Elytra Trails"))
                        .enchantments(CustomItem.enchantArray(Enchantment.DURABILITY), CustomItem.levelArray(1))
                        .hideEnchants(true)
                        .lore(Arrays.asList(
                                Component.text(""),
                                Component.text(ChatColor.YELLOW + "Current:"),
                                Component.text(ChatColor.DARK_GREEN + (elytraTrail.substring(0, 1).toUpperCase() + elytraTrail.substring(1).toLowerCase()))))
                        .build(),
                event -> {
                    if (!player.hasPermission("group.donator")) { player.performCommand("shop cosmetic"); }
                    else { displayElytraMenu(player); }
                })
        );
        //  endregion

        StaticPane selectionPane = new StaticPane(0, 3, 9, 1);

        //  region Shop
        selectionPane.addItem(new GuiItem(
                        new CustomItem.ItemBuilder(Material.CHEST)
                                .name(Component.text(ChatColor.WHITE + "Buy Cosmetics"))
                                .lore(Arrays.asList(
                                        Component.text(""),
                                        Component.text(ChatColor.RED + "Click to visit the Store")))
                                .build(),
                        event -> player.performCommand("shop cosmetic"))
                , 1, 0);
        //  endregion

        //  region Deselect Trails
        selectionPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text(ChatColor.WHITE + "Deselect All"))
                        .lore(Arrays.asList(
                                Component.text(""),
                                Component.text(ChatColor.RED + "Disables all active trails")))
                        .build(),
                event -> {
                    removeActiveTrails(player);
                    displayCosmeticsMenu(player);
                })
        , 7, 0);
        // endregion

        gui.addPane(navigationPane);
        gui.addPane(selectionPane);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the player trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayPlayerMenu(Player player) {
        ChestGui gui = getTrailSelectionMenu(player, "player");
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the projectile trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayProjectileMenu(Player player) {
        ChestGui gui = getTrailSelectionMenu(player, "projectile");
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the elytra trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayElytraMenu(Player player) {
        ChestGui gui = getTrailSelectionMenu(player, "elytra");
        gui.update();
        gui.show(player);
    }

    /**
     * Creates a basic navigation menu with main menu, previous page, and next page buttons
     *
     * @return The StaticPane containing basic navigation options
     */
    public StaticPane navigationPane(ChestGui gui, PaginatedPane pages, Player player) {
        StaticPane navigation = new StaticPane(0, gui.getRows()-1, 9, 1);
        Label prevPage = new Label(3, gui.getRows()-1, 1, 1, Font.OAK_PLANKS);
        Label nextPage = new Label(5, gui.getRows()-1, 1, 1, Font.OAK_PLANKS);

        //  region Background
        OutlinePane background = new OutlinePane(0, gui.getRows()-1, 9, 1, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(filler));
        background.setRepeat(true);
        gui.addPane(background);
        //  endregion

        //  region Main Menu
        navigation.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ARROW)
                        .name(Component.text(ChatColor.WHITE + "Main Menu"))
                        .build(),
                event -> displayCosmeticsMenu(player)), 0, 0
        );
        //  endregion

        //  region Previous Page
        prevPage.setText("←");
        prevPage.setVisible(false);
        prevPage.setOnClick(event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                prevPage.setVisible(pages.getPage() > 0);
                nextPage.setVisible(pages.getPage() < pages.getPages());
                gui.update();
            }
        });
        gui.addPane(prevPage);
        //  endregion

        //  region Next Page
        nextPage.setText("→");
        nextPage.setVisible(pages.getPages() > 1);
        nextPage.setOnClick(event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                prevPage.setVisible(pages.getPage() > 0);
                nextPage.setVisible(pages.getPage() < pages.getPages() - 1);
                gui.update();
            }
        });
        gui.addPane(nextPage);
        //  endregion

        return navigation;
    }

    /**
     * The GuiItem to disable your selection in a menu
     */
    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text(ChatColor.WHITE + "" + ChatColor.BOLD + "None"))
                        .lore(Arrays.asList(
                                Component.text(""),
                                Component.text(ChatColor.RED + "Click to Select")))
                        .build()
        );
        item.setAction(event -> {
            removeActiveTrail(player, key);
            if(AnimationManager.hasActiveAnimation(player)){
                AnimationManager.removeParticleAnimation(player.getUniqueId());
            }
            enableItem(item);
            pages.getItems().forEach(this::disableItem);
            gui.update();
        });
        return item;
    }

    /**
     * Creates a ChestGui to offer selections in a given menu type
     *
     * @param player The player to read from
     * @param type The type name of the trails (must match a config name)
     * @return The ChestGui with collection of GuiItems from a CustomTrail type
     */
    public ChestGui getTrailSelectionMenu(Player player, String type) {
        List<CustomTrail> trails = TrailManager.getTrailType(type);

        ChestGui gui = new ChestGui(6, ChatColor.GOLD + type.substring(0, 1).toUpperCase() + type.substring(1) + " Trails");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        PaginatedPane pages = new PaginatedPane(0, 0, 9, gui.getRows()-1);

        List<GuiItem> items = new ArrayList<>(Collections.singletonList(getDefaultGuiItem(player, pages, gui, type)));
        for (CustomTrail trailEnum : trails) {
            GuiItem item = new GuiItem(trailEnum.getItem());
            item.setAction(event -> {
                if(AnimationManager.hasActiveAnimation(player)){
                    AnimationManager.removeParticleAnimation(player.getUniqueId());
                }
                setActiveTrail(trailEnum.getTrailName(), player, type);
                if(trailEnum.getAnimation() != null){
                    AnimationManager.attachParticleAnimation(player, player.getUniqueId(), type, trailEnum);
                }
                pages.getItems().forEach(this::disableItem);
                enableItem(item);
                gui.update();
            });
            if (trailEnum.getTrailName().equals(getActiveTrail(player, type))) { enableItem(item); }
            items.add(item);
        }

        pages.populateWithGuiItems(items);
        gui.setRows(Math.min(6, ((items.size() / 9) + ((items.size() % 9) == 0 ? 0 : 1) + 1)));
        gui.addPane(pages);

        gui.addPane(navigationPane(gui, pages, player));
        gui.update();
        return gui;
    }

    /**
     * "Enables" a GuiItem (enchanted)
     *
     * @param item The GuiItem to enchant
     */
    public void enableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) {
            itemLore.remove(itemLore.size()-1);
            itemLore.add(Component.text(ChatColor.GREEN + "Selected"));
            itemMeta.lore(itemLore);
        }

        item.getItem().setItemMeta(itemMeta);
    }

    /**
     * "Disables" a GuiItem (disenchanted)
     *
     * @param item The GuiItem to disenchant
     */
    public void disableItem(GuiItem item) {
        ItemMeta itemMeta = item.getItem().getItemMeta();
        itemMeta.removeEnchant(Enchantment.DURABILITY);

        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) {
            itemLore.remove(itemLore.size()-1);
            itemLore.add(Component.text(ChatColor.RED + "Click to Select"));
            itemMeta.lore(itemLore);
        }

        item.getItem().setItemMeta(itemMeta);
    }

}
