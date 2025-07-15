package me.wesleynichols.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.CustomItem;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.util.TrailUtils;
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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildItemName;
import static me.wesleynichols.cosmeticperks.util.ItemUtils.buildTrailLore;

public class CosmeticsMenu extends TrailUtils {

    private final CosmeticPerks plugin = CosmeticPerks.getInstance();
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

    public GuiItem getDefaultGuiItem(Player player, PaginatedPane pages, ChestGui gui, String key) {
        GuiItem item = new GuiItem(
                new CustomItem.ItemBuilder(Material.BARRIER)
                        .name(Component.text("None").decorate(TextDecoration.BOLD))
                        .lore(List.of(Component.empty(), Component.text("Click to Select", NamedTextColor.RED)))
                        .build());
        item.setAction(event -> {
            removeActiveTrail(player, key, true);
            if (plugin.getAnimationManager().hasActiveAnimation(player)) {
                plugin.getAnimationManager().removeParticleAnimation(player.getUniqueId());
            }
            enableItem(item);
            pages.getItems().forEach(this::disableItem);
            gui.update();
        });
        return item;
    }

    public ChestGui getTrailSelectionMenu(Player player, String type) {
        List<CustomTrail> allTrails = plugin.getTrailManager().getTrailType(type);
        Set<String> ownedLimitedTrails = plugin.getLimitedTrailStorage().getPlayerTrails(player.getUniqueId(), type);

        List<CustomTrail> trails = allTrails.stream()
                .filter(trail -> !trail.isLimitedItem() || ownedLimitedTrails.contains(trail.getTrailName()))
                .sorted(Comparator.naturalOrder())
                .toList();

        Function<Integer, String> getTitle = page -> {
            Component base = Component.text(capitalize(type) + " Trails");
            if (page > 0) {
                base = base.append(Component.text(" (Page " + (page + 1) + ")"));
            }
            return LegacyComponentSerializer.legacySection().serialize(base);
        };

        ChestGui gui = new ChestGui(6, getTitle.apply(0));
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnGlobalDrag(event -> event.setCancelled(true));

        PaginatedPane pages = new PaginatedPane(0, 0, 9, 1);
        List<GuiItem> items = new ArrayList<>();

        items.add(getDefaultGuiItem(player, pages, gui, type));

        for (CustomTrail trailEnum : trails) {
            GuiItem item = new GuiItem(trailEnum.getItem());
            item.setAction(event -> {
                if (trailEnum.getAnimation() != null && plugin.getAnimationManager().hasActiveAnimation(player)) {
                    plugin.getAnimationManager().removeParticleAnimation(player.getUniqueId());
                }
                setActiveTrail(trailEnum.getTrailName(), player, type);
                if (trailEnum.getAnimation() != null) {
                    plugin.getAnimationManager().attachParticleAnimation(player, player.getUniqueId(), type, trailEnum);
                }
                pages.getItems().forEach(this::disableItem);
                enableItem(item);
                gui.update();
            });
            if (trailEnum.getTrailName().equals(getActiveTrail(player, type))) enableItem(item);
            items.add(item);
        }

        // TODO: Remove this
        // Arbitrary test items
        for (int i = 1; i <= 10; i++) {
            ItemStack itemStack = new ItemStack(Material.STONE);
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.text("Item " + i).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
            itemStack.setItemMeta(meta);

            GuiItem guiItem = new GuiItem(itemStack, event -> {
                event.getWhoClicked().sendMessage("You clicked on an Item! Good job!");
            });

            items.add(guiItem);
        }

        int itemRows = (int) Math.ceil(items.size() / 9.0);
        itemRows = Math.min(itemRows, 5);  // Limit max rows to 5, if needed
        int totalRows = itemRows + 1;      // +1 for the nav bar row

        gui.setRows(totalRows);
        pages.setHeight(itemRows);
        pages.populateWithGuiItems(items);
        gui.addPane(pages);

        // Create paging buttons
        GuiItem prevButton = new GuiItem(
                new CustomItem.ItemBuilder(Material.ARROW)
                        .name(Component.text("Previous", NamedTextColor.YELLOW)
                                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                        .build());

        GuiItem nextButton = new GuiItem(
                new CustomItem.ItemBuilder(Material.ARROW)
                        .name(Component.text("Next", NamedTextColor.YELLOW)
                                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                        .build());

        // Initialize visibility and behavior
        updatePagingButtonsVisibility(prevButton, nextButton, pages);

        prevButton.setAction(event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                updateGuiForPage(gui, pages, prevButton, nextButton, getTitle);
            }
        });

        nextButton.setAction(event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                updateGuiForPage(gui, pages, prevButton, nextButton, getTitle);
            }
        });

        // Navigation bar (with background filler)
        OutlinePane navBackground = new OutlinePane(0, totalRows - 1, 9, 1, Pane.Priority.LOWEST);
        navBackground.addItem(new GuiItem(FILLER));
        navBackground.setRepeat(true);
        gui.addPane(navBackground);

        StaticPane navBar = new StaticPane(0, totalRows - 1, 9, 1);
        navBar.addItem(prevButton, 0, 0);
        navBar.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.CLOCK)
                        .name(Component.text("Back", NamedTextColor.YELLOW)
                                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                        .build(),
                event -> displayCosmeticsMenu(player)
        ), 4, 0);
        navBar.addItem(nextButton, 8, 0);
        gui.addPane(navBar);

        return gui;
    }

    private void updatePagingButtonsVisibility(GuiItem prevButton, GuiItem nextButton, PaginatedPane pages) {
        prevButton.setVisible(pages.getPage() > 0);
        nextButton.setVisible(pages.getPage() < pages.getPages() - 1);
    }

    private void updateGuiForPage(ChestGui gui, PaginatedPane pages, GuiItem prevButton, GuiItem nextButton, Function<Integer, String> getTitle) {
        gui.setTitle(getTitle.apply(pages.getPage()));
        updatePagingButtonsVisibility(prevButton, nextButton, pages);
        gui.update();
    }

    public void enableItem(GuiItem item) {
        ItemMeta meta = item.getItem().getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            lore.set(lore.size() - 1, Component.text("Selected", NamedTextColor.GREEN)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
            meta.lore(lore);
        }
        item.getItem().setItemMeta(meta);
    }

    public void disableItem(GuiItem item) {
        ItemMeta meta = item.getItem().getItemMeta();
        meta.removeEnchant(Enchantment.UNBREAKING);
        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            lore.set(lore.size() - 1, Component.text("Click to Select", NamedTextColor.RED)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
            meta.lore(lore);
        }
        item.getItem().setItemMeta(meta);
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
