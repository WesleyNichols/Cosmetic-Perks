package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import me.quantiom.advancedvanish.sync.impl.SqlServerSyncStore;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CosmeticsCommand implements CommandExecutor {

    public static String getMainCommand = "cosmetic";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase(getMainCommand) && sender instanceof Player) {
            Player player = (Player) sender;

            if (args[0].equals("projectile")) {
                if (args[1] != null) {
                    if (Arrays.stream(ProjectileTrails.values()).anyMatch(e -> e.toString().equalsIgnoreCase(args[1]))) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "arrow-trail"), PersistentDataType.STRING, args[1].toUpperCase());
                        player.sendMessage(Component.text("Enabling Projectile Trail: " + args[1].toUpperCase()));
                    } else {
                        player.sendMessage(Component.text(args[1].toUpperCase() + " is not a valid projectile trail!"));
                    }
                } else {
                    player.sendMessage(Component.text("Not enough arguments provided!"));
                }
                return true;
            } else if (args[0].equals("player")) {
                if (args[1] != null) {
                    if (Arrays.stream(PlayerTrails.values()).anyMatch(e -> e.toString().equalsIgnoreCase(args[1]))) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, args[1].toUpperCase());
                        player.sendMessage(Component.text("Enabling Player Trail: " + args[1].toUpperCase()));
                    } else {
                        player.sendMessage(Component.text(args[1].toUpperCase() + " is not a valid player trail!"));
                    }
                } else {
                    player.sendMessage(Component.text("Not enough arguments provided!"));
                }
                return true;
            } else if (args[0].equals("vanish")) {
//                try {
//                    if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) {
//                        player.sendMessage(Component.text(player.getName() + " is vanished!"));
//                    } else {
//                        player.sendMessage(Component.text(player.getName() + " is not vanished!"));
//                    }
//                    player.sendMessage(Component.text(AdvancedVanishAPI.INSTANCE.isPlayerVanished(player) + " " + AdvancedVanishAPI.INSTANCE.getVanishedPlayers()));
//                    return true;
//                } catch (Exception e) {
//                    sender.sendMessage(ChatColor.RED + "Failed to run!");
//                    return true;
//                }
            }
        }
        return false;
    }
}