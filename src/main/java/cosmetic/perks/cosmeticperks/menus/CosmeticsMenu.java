package cosmetic.perks.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import cosmetic.perks.cosmeticperks.structures.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CosmeticsMenu {

    private static Inventory inv;

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
                        .enchantments(new Enchantment[]{Enchantment.DURABILITY}, new int[]{1})
                        .hideEnchants(true)
                        .armorColor(Color.GREEN)
                        .build(),
                event -> {
            //navigate to player trails
        }));
       //endregion

       //region Projectile Trails
        ItemStack arrow = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.displayName(Component.text(ChatColor.GOLD + "Projectile Trails"));
        arrow.setItemMeta(arrowMeta);

        navigationPane.addItem(new GuiItem(arrow, event -> {
            //navigate to projectile trails
        }));
        //endregion

        //region Elytra Trails
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta elytraMeta = elytra.getItemMeta();
        elytraMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        elytraMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        elytraMeta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "Elytra Trails"));
        elytra.setItemMeta(elytraMeta);

        navigationPane.addItem(new GuiItem(elytra, event -> {
            //navigate to elytra trails
        }));
        //endregion

        gui.addPane(navigationPane);
        gui.update();
        gui.show(player);
    }

    public void displayPlayerGui(Player player) {
        ChestGui gui = new ChestGui(3, "Cosmetics Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);

        //region Player Trails
        ItemStack fireworks = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta fireworksMeta = fireworks.getItemMeta();
        fireworksMeta.displayName(Component.text(ChatColor.RED + "Firework Spark Trail"));
        fireworks.setItemMeta(fireworksMeta);


        navigationPane.addItem(new GuiItem(fireworks, event -> {
            //navigate to player trails
        }));
        //endregion

        //region Projectile Trails
        ItemStack arrow = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.displayName(Component.text(ChatColor.GOLD + "Projectile Trails"));
        arrow.setItemMeta(arrowMeta);

        navigationPane.addItem(new GuiItem(arrow, event -> {
            //navigate to projectile trails
        }));
        //endregion

        //region Elytra Trails
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta elytraMeta = elytra.getItemMeta();
        elytraMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        elytraMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        elytraMeta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "Elytra Trails"));
        elytra.setItemMeta(elytraMeta);

        navigationPane.addItem(new GuiItem(elytra, event -> {
            //navigate to elytra trails
        }));
        //endregion

        gui.update();
        gui.show(player);
    }

    public void displayParticleGui(Player player) {

    }

    public void displayElytraGui(Player player) {

    }

    public void createArrowGUI(){
        Inventory inv = Bukkit.createInventory(null, 9, Component.text("Arrow Trails").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));

        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.RED + "Firework Sparks"));
        item.setItemMeta(meta);
        inv.setItem(0, item);

        setInv(inv);
    }

    public Inventory getInv(){
        return inv;
    }

    private void setInv(Inventory i){
        inv = i;
    }

    public void openInv(Player player){
        player.openInventory(inv);
    }

}
