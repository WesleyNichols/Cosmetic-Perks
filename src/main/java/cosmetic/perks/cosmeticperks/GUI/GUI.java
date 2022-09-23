package cosmetic.perks.cosmeticperks.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    private static Inventory inv;

    public void create(){
        Inventory inv = Bukkit.createInventory(null, 5, Component.text("Particles").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));

        ItemStack item = new ItemStack(Material.BEEHIVE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Bee Trail").color(NamedTextColor.YELLOW));
        meta.lore(Arrays.asList(Component.text("The Best Trail in the Hive!")));
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
