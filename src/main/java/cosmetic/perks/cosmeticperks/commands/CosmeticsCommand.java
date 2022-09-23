package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.enums.ArrowTrails;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CosmeticsCommand implements CommandExecutor {

    public static String getMainCommand = "cosmetic";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase(getMainCommand) && sender instanceof Player) {
            Player player = (Player) sender;
            TrailManager.addSavedArrowTrail(player.getUniqueId(), ArrowTrails.FIREWORKS);
            player.sendMessage(Component.text("Enabling ArrowTrails.FIREWORKS"));
            return true;
        }
        return false;
    }
}
