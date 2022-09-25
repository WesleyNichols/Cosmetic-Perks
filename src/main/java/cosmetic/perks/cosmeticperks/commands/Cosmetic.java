package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.menus.CosmeticsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Cosmetic implements CommandExecutor {

    public static String getMainCommand = "sheep";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (label.equalsIgnoreCase(getMainCommand) && player.hasPermission("group.donator")) {
                    CosmeticsMenu gui = new CosmeticsMenu();
                    gui.displayCosmeticsMenu(player);
                    return true;
                }
                return true;
            }
            return false;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}