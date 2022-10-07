package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.menus.CosmeticsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CosmeticsCommand implements CommandExecutor {

    public static String getCommand = "cosmetic";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                CosmeticsMenu menu = new CosmeticsMenu();
                menu.displayCosmeticsMenu(player);
                return true;
            }
        }
        return false;
    }

}