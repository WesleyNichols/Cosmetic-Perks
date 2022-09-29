package cosmetic.perks.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import cosmetic.perks.cosmeticperks.enums.ElytraTrails;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.structures.CustomItem;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import cosmetic.perks.cosmeticperks.structures.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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


public class CosmeticsMenu extends Methods {

    public final ItemStack filler = new CustomItem.ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(" ")).build();

    /**
     * Method to display the cosmetic menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayCosmeticsMenu(Player player){
        ChestGui gui = new ChestGui(3, ChatColor.GOLD + "Cosmetics Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(filler));
        background.setRepeat(true);
        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);

        //  region Player Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.LEATHER_BOOTS)
                        .name(Component.text(ChatColor.GREEN + "Player Trails"))
                        .armorColor(Color.GREEN)
                        .build(),
                event -> displayPlayerMenu(player))
        );
       //   endregion

       //   region Projectile Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.SPECTRAL_ARROW)
                        .name(Component.text(ChatColor.GOLD + "Projectile Trails"))
                        .lore(Arrays.asList(
                                Component.text("test"),
                                Component.text("also test")))
                        .build(),
                event -> displayProjectileMenu(player))
        );
        //  endregion

        //  region Elytra Trails
        navigationPane.addItem(new GuiItem(
                new CustomItem.ItemBuilder(Material.ELYTRA)
                        .name(Component.text(ChatColor.LIGHT_PURPLE + "Elytra Trails"))
                        .enchantments(enchantArray(Enchantment.DURABILITY), levelArray(1))
                        .hideEnchants(true)
                        .build(),
                event -> displayElytraMenu(player))
        );
        //  endregion

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
        ChestGui gui = getTrailSelectionMenu(player, PlayerTrails.class);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the projectile trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayProjectileMenu(Player player) {
        ChestGui gui = getTrailSelectionMenu(player, ProjectileTrails.class);
        gui.update();
        gui.show(player);
    }

    /**
     * Method to display the elytra trail menu to a player.
     *
     * @param player The player to show the menu to
     */
    public void displayElytraMenu(Player player) {
        ChestGui gui = getTrailSelectionMenu(player, ElytraTrails.class);
        gui.update();
        gui.show(player);
    }

    /**
     * The StaticPane to navigate in a menu
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
                        .name(Component.text(ChatColor.WHITE + "None"))
                        .build()
        );
        item.setAction(event -> {
            removeActiveTrail(player, key);
            enableItem(item);
            pages.getItems().forEach(this::disableItem);
            gui.update();
        });
        return item;
    }

    /**
     * The ChestGui to offer selections in a menu
     */
    public <T extends CustomTrail> ChestGui getTrailSelectionMenu(Player player, Class<T> enumClass) {
        ChestGui gui = new ChestGui(6, ChatColor.GOLD + "Player Trails");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        PaginatedPane pages = new PaginatedPane(0, 0, 9, gui.getRows()-1);

        String type = enumClass.getEnumConstants()[0].getProperties().getTrailType();
        List<GuiItem> items = new ArrayList<>(Collections.singletonList(getDefaultGuiItem(player, pages, gui, type)));

        for (T trailType : enumClass.getEnumConstants()) {
            CustomTrail.TrailProperties trailProperties = trailType.getProperties();
            GuiItem item = new GuiItem(trailProperties.getItem());
            item.setAction(event -> {
                setActiveTrail(trailType.toString(), player, type);
                pages.getItems().forEach(this::disableItem);
                enableItem(item);
                gui.update();
            });
            if (trailType.toString().equals(getActiveTrail(player, type))) { enableItem(item); }
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
        item.getItem().setItemMeta(itemMeta);
    }


    /*
    Leaving this for Sheep, delete later

    guiItems = new ArrayList<>(Collections.singletonList(getDefaultGuiItem(player, pages, gui, "player-animation")));
    String activeAnimation = getActiveTrail(player, "player-animation");
    for (ParticleAnimations playerAnimations : ParticleAnimations.values()) {
        GuiItem item = new GuiItem(playerAnimations.getItem());
        item.setAction(event -> {
            setActiveTrail(playerAnimations.name(), player, "player-animation");
            attachParticleAnimation(player, player.getUniqueId(), "player-animation");
            pages.getItems().forEach(this::disableItem);
            enableItem(item);
            gui.update();
        });
        if (playerAnimations.name().equals(activeAnimation)) { enableItem(item); }
        guiItems.add(item);
    }

     */
}
