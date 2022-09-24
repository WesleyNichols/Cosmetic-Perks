package cosmetic.perks.cosmeticperks.GUI;

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

import java.util.Arrays;

public class GUI {

    private static Inventory inv;

    public void create(){
        Inventory inv = Bukkit.createInventory(null, 9, Component.text("Particles").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "Arrow Trails"));
        item.setItemMeta(meta);
        inv.setItem(0, item);

        item = new ItemStack(Material.BEE_NEST);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "Bee Trail"));
        meta.lore(Arrays.asList(Component.text("The Best Trail in the Hive!")));
        item.setItemMeta(meta);
        inv.setItem(1, item);

        setInv(inv);
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
