package cosmetic.perks.cosmeticperks.menus;

import com.github.stefvanschie.inventoryframework.gui.InventoryComponent;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.commands.Cosmetic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class CosmeticsMenu {

    private static Inventory inv;

    public void displayCosmeticsMenu(Player player) throws FileNotFoundException {
        //ChestGui gui = new ChestGui(3, "Cosmetics Menu");
        ChestGui gui = ChestGui.load(this, CosmeticPerks.getInstance().getResource("Menus/CosmeticsMenu.xml"));
        assert gui != null;

        gui.setOnGlobalClick(event -> event.setCancelled(true));

//        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
//        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
//        background.setRepeat(true);
//
//        gui.addPane(background);
//
//        OutlinePane navigationPane = new OutlinePane(3, 1, 3, 1);
//
////        region Player Trails
//        ItemStack playerTrail = new ItemStack(Material.LEATHER_BOOTS);
//        LeatherArmorMeta playerTrailMeta = (LeatherArmorMeta) playerTrail.getItemMeta();
//        playerTrailMeta.setColor(Color.GREEN);
//        playerTrailMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        playerTrailMeta.addItemFlags(ItemFlag.HIDE_DYE);
//        playerTrailMeta.displayName(Component.text(ChatColor.YELLOW + "Player Trails"));
//        playerTrail.setItemMeta(playerTrailMeta);
//
//
//        navigationPane.addItem(new GuiItem(playerTrail, event -> {
//            //navigate to player trails
//        }));
////        endregion
//
////        region Projectile Trails
//        ItemStack arrow = new ItemStack(Material.SPECTRAL_ARROW);
//        ItemMeta arrowMeta = arrow.getItemMeta();
//        arrowMeta.displayName(Component.text(ChatColor.GOLD + "Projectile Trails"));
//        arrow.setItemMeta(arrowMeta);
//
//        navigationPane.addItem(new GuiItem(arrow, event -> {
//            //navigate to projectile trails
//        }));
////        endregion
//
////        region Elytra Trails
//        ItemStack elytra = new ItemStack(Material.ELYTRA);
//        ItemMeta elytraMeta = elytra.getItemMeta();
//        elytraMeta.addEnchant(Enchantment.DURABILITY, 1, false);
//        elytraMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        elytraMeta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "Elytra Trails"));
//        elytra.setItemMeta(elytraMeta);
//
//        navigationPane.addItem(new GuiItem(elytra, event -> {
//            //navigate to elytra trails
//        }));
////        endregion

        gui.update();
        gui.show(player);
    }

    public void displayArrowGui(Player player) {

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
