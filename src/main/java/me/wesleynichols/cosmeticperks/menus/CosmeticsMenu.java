package me.wesleynichols.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.*;
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

import java.util.*;

import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildItemName;
import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildTrailLore;

public class CosmeticsMenu extends TrailMethods {

    private static final ItemStack FILLER = new CustomItem.ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .name(Component.empty()).build();

    public void displayCosmeticsMenu(Player player) {
        ChestGui gui = new ChestGui(3, "Cosmetics");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnGlobalDrag(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(FILLER));
        background.setRepeat(true);
        gui.addPane(background);

        StaticPane navigation = new StaticPane(1, 1, 7, 1);

        addTrailButton(navigation, player, "player", Material.LEATHER_BOOTS, NamedTextColor.GREEN, 2);
        addTrailButton(navigation, player, "projectile", Material.SPECTRAL_ARROW, NamedTextColor.GOLD, 3);
        addTrailButton(navigation, player, "elytra", Material.ELYTRA, NamedTextColor.LIGHT_PURPLE, 4);

        navigation.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.CHEST)
                        .name(buildItemName("Buy Cosmetics", NamedTextColor.WHITE, true))
                        .lore(List.of(
                                Component.empty(),
                                Component.text("Click to visit the store", NamedTextColor.AQUA)
                                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)))
                        .build(),
                event -> player.performCommand("shop cosmetic")), 0, 0);

        navigation.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(buildItemName("Deselect All", NamedTextColor.WHITE, true))
                        .lore(List.of(
                                Component.empty(),
                                Component.text("Disable active trails", NamedTextColor.RED)
                                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)))
                        .build(),
                event -> {
                    removeActiveTrails(player);
                    displayCosmeticsMenu(player);
                }), 6, 0);

        gui.addPane(navigation);
        gui.update();
        gui.show(player);
    }

    private void addTrailButton(StaticPane pane, Player player, String type, Material icon, NamedTextColor color, int x) {
        String trail = getActiveTrail(player, type);
        pane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(icon)
                        .armorColor(type.equals("player") ? Color.GREEN : null)
                        .name(buildItemName(capitalize(type) + " Trails", color, true))
                        .lore(buildTrailLore(trail))
                        .build(),
                event -> {
                    if (!player.hasPermission("cosmeticperks.access")) player.performCommand("shop cosmetic");
                    else displayMenu(player, type);
                }), x, 0);
    }

    public void displayMenu(Player player, String type) {
        ChestGui gui = getTrailSelectionMenu(player, type);
        gui.update();
        gui.show(player);
    }

    public StaticPane navigationPane(ChestGui gui, PaginatedPane pages, Player player) {
        StaticPane navigation = new StaticPane(0, gui.getRows() - 1, 9, 1);
        Label prevPage = new Label(3, gui.getRows() - 1, 1, 1, Font.OAK_PLANKS);
        Label nextPage = new Label(5, gui.getRows() - 1, 1, 1, Font.OAK_PLANKS);

        OutlinePane background = new OutlinePane(0, gui.getRows() - 1, 9, 1, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(FILLER));
        background.setRepeat(true);
        gui.addPane(background);

        navigation.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ARROW)
                        .name(Component.text("Main Menu"))
                        .build(),
                event -> displayCosmeticsMenu(player)), 0, 0);

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

        return navigation;
    }

    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text("None").decorate(TextDecoration.BOLD))
                        .lore(List.of(Component.empty(), Component.text("Click to Select", NamedTextColor.RED)))
                        .build());
        item.setAction(event -> {
            removeActiveTrail(player, key, true);
            if (AnimationManager.hasActiveAnimation(player)) {
                AnimationManager.removeParticleAnimation(player.getUniqueId());
            }
            enableItem(item);
            pages.getItems().forEach(this::disableItem);
            gui.update();
        });
        return item;
    }

    public ChestGui getTrailSelectionMenu(Player player, String type) {
        List<CustomTrail> trails = TrailManager.getTrailType(type);
        trails.sort(Comparator.naturalOrder());

        Component titleComponent = Component.text(capitalize(type) + " Trails", NamedTextColor.GOLD);
        ChestGui gui = new ChestGui(6, LegacyComponentSerializer.legacySection().serialize(titleComponent));
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnGlobalDrag(event -> event.setCancelled(true));

        PaginatedPane pages = new PaginatedPane(0, 0, 9, gui.getRows() - 1);
        List<GuiItem> items = new ArrayList<>(List.of(getDefaultGuiItem(player, pages, gui, type)));

        for (CustomTrail trailEnum : trails) {
            GuiItem item = new GuiItem(trailEnum.getItem());
            item.setAction(event -> {
                if (AnimationManager.hasActiveAnimation(player)) {
                    AnimationManager.removeParticleAnimation(player.getUniqueId());
                }
                setActiveTrail(trailEnum.getTrailName(), player, type);
                if (trailEnum.getAnimation() != null) {
                    AnimationManager.attachParticleAnimation(player, player.getUniqueId(), type, trailEnum);
                }
                pages.getItems().forEach(this::disableItem);
                enableItem(item);
                gui.update();
            });
            if (trailEnum.getTrailName().equals(getActiveTrail(player, type))) enableItem(item);
            items.add(item);
        }

        pages.populateWithGuiItems(items);
        gui.setRows(Math.min(6, (items.size() + 8) / 9 + 1));
        gui.addPane(pages);
        gui.addPane(navigationPane(gui, pages, player));
        gui.update();
        return gui;
    }

    public void enableItem(GuiItem item) {
        ItemMeta meta = item.getItem().getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            lore.set(lore.size() - 1, Component.text("Selected", NamedTextColor.GREEN));
            meta.lore(lore);
        }
        item.getItem().setItemMeta(meta);
    }

    public void disableItem(GuiItem item) {
        ItemMeta meta = item.getItem().getItemMeta();
        meta.removeEnchant(Enchantment.UNBREAKING);
        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            lore.set(lore.size() - 1, Component.text("Click to Select", NamedTextColor.RED));
            meta.lore(lore);
        }
        item.getItem().setItemMeta(meta);
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
