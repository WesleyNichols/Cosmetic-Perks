package me.wesleynichols.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import me.wesleynichols.cosmeticperks.managers.AnimationManager;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.CustomItem;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.managers.TrailMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildItemName;
import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildTrailLore;


public class CosmeticsMenu extends TrailMethods {

    public final ItemStack filler = new CustomItem.ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.empty()).build();

    /**
     * Method to display the cosmetic menu to a player.
     */
    public void displayCosmeticsMenu(Player player){
        ChestGui gui = new ChestGui(4, "Cosmetics Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnGlobalDrag(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 3, 9, 1, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(filler));
        background.setRepeat(true);
        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);

        // region Player Trails
        String playerTrail = getActiveTrail(player, "player");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.LEATHER_BOOTS)
                        .armorColor(Color.GREEN)
                        .name(buildItemName("Player Trails", NamedTextColor.GREEN, true))
                        .lore(buildTrailLore(playerTrail))
                        .build(),
                event -> {
                    if (!player.hasPermission("cosmeticperks.access")) player.performCommand("shop cosmetic");
                    else displayMenu(player, "player");
                })
        );
// endregion

// region Projectile Trails
        String projTrail = getActiveTrail(player, "projectile");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.SPECTRAL_ARROW)
                        .name(buildItemName("Projectile Trails", NamedTextColor.GOLD, true))
                        .lore(buildTrailLore(projTrail))
                        .build(),
                event -> {
                    if (!player.hasPermission("cosmeticperks.access")) player.performCommand("shop cosmetic");
                    else displayMenu(player, "projectile");
                })
        );
// endregion

// region Elytra Trails
        String elytraTrail = getActiveTrail(player, "elytra");
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ELYTRA)
                        .name(buildItemName("Elytra Trails", NamedTextColor.LIGHT_PURPLE, true))
                        .lore(buildTrailLore(elytraTrail))
                        .build(),
                event -> {
                    if (!player.hasPermission("cosmeticperks.access")) player.performCommand("shop cosmetic");
                    else displayMenu(player, "elytra");
                })
        );
// endregion

        StaticPane selectionPane = new StaticPane(0, 3, 9, 1);

        //  region Shop
        selectionPane.addItem(new GuiItem(
                        new CustomItem.ItemBuilder(Material.CHEST)
                                .name(buildItemName("Buy Cosmetics", NamedTextColor.WHITE, true))
                                .lore(Arrays.asList(
                                        Component.empty(),
                                        Component.text("Click to visit the store", NamedTextColor.AQUA)
                                                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)))
                                .build(),
                        event -> player.performCommand("shop cosmetic"))
                , 2, 0);
        //  endregion

        //  region Deselect Trails
        selectionPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(buildItemName("Deselect All", NamedTextColor.WHITE, true))
                        .lore(Arrays.asList(
                                Component.empty(),
                                Component.text("Disable active trails", NamedTextColor.RED)
                                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)))
                        .build(),
                event -> {
                    removeActiveTrails(player);
                    displayCosmeticsMenu(player);
                })
        , 6, 0);
        // endregion

        gui.addPane(navigationPane);
        gui.addPane(selectionPane);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display a given player trail menu to a player.
     */
    public void displayMenu(Player player, String type) {
        ChestGui gui = getTrailSelectionMenu(player, type);
        gui.update();
        gui.show(player);
    }

    /**
     * Create a basic navigation menu with main menu, previous page, and next page buttons
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
                        .name(Component.text("Main Menu"))
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
     * A GuiItem to disable your selection in a menu
     */
    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text("None").decorate(TextDecoration.BOLD))
                        .lore(Arrays.asList(
                                Component.empty(),
                                Component.text("Click to Select", NamedTextColor.RED)))
                        .build()
        );
        item.setAction(event -> {
            removeActiveTrail(player, key, true);
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
        Collections.sort(trails);

        String capitalizedType = type.substring(0, 1).toUpperCase() + type.substring(1);
        Component titleComponent = Component.text(capitalizedType + " Trails", NamedTextColor.GOLD);

        ChestGui gui = new ChestGui(6, LegacyComponentSerializer.legacySection().serialize(titleComponent));
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnGlobalDrag(event -> event.setCancelled(true));
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
        itemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) {
            itemLore.remove(itemLore.size()-1);
            itemLore.add(Component.text("Selected", NamedTextColor.GREEN));
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
        itemMeta.removeEnchant(Enchantment.UNBREAKING);

        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) {
            itemLore.remove(itemLore.size()-1);
            itemLore.add(Component.text("Click to Select", NamedTextColor.RED));
            itemMeta.lore(itemLore);
        }

        item.getItem().setItemMeta(itemMeta);
    }

    /**
     * Capitalize the first letter in a provided word
     */
    public static String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word; // handle null or empty strings safely
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
