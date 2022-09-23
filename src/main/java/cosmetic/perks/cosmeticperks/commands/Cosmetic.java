package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.GUI.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Cosmetic extends JavaPlugin {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player)sender;
        if(player.hasPermission("group.donator")){
            GUI gui = new GUI();
            Inventory inv = gui.getInv();
            gui.create();
            gui.openInv(player);
            return true;
        }
        return true;
    }
}
