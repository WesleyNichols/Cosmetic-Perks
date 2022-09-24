package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.GUI.GUI;
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
                    GUI gui = new GUI();
                    gui.create();
                    gui.openInv(player);
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