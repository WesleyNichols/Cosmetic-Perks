package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ElytraTrails;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import me.quantiom.advancedvanish.AdvancedVanish;
import me.quantiom.advancedvanish.sync.impl.SqlServerSyncStore;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import me.quantiom.advancedvanish.util.AdvancedVanishAPIKt;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CosmeticsCommand implements CommandExecutor {

    public static String getMainCommand = "cosmetic";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("cosmetic") && sender instanceof Player) {
            Player player = (Player) sender;

            if (args[0].equals("player")) {
                if (args[1] != null) {
                    if (args[1].equalsIgnoreCase("NONE")) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, "NONE");
                        player.sendMessage(Component.text("Disabling Player Trails"));
                        return true;
                    }
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
            } else if (args[0].equals("projectile")) {
                if (args[1] != null) {
                    if (args[1].equalsIgnoreCase("NONE")) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, "NONE");
                        player.sendMessage(Component.text("Disabling Projectile Trails"));
                        return true;
                    }
                    if (Arrays.stream(ProjectileTrails.values()).anyMatch(e -> e.toString().equalsIgnoreCase(args[1]))) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, args[1].toUpperCase());
                        player.sendMessage(Component.text("Enabling Projectile Trail: " + args[1].toUpperCase()));
                    } else {
                        player.sendMessage(Component.text(args[1].toUpperCase() + " is not a valid projectile trail!"));
                    }
                } else {
                    player.sendMessage(Component.text("Not enough arguments provided!"));
                }
                return true;
            } else if (args[0].equals("elytra")) {
                if (args[1] != null) {
                    if (args[1].equalsIgnoreCase("NONE")) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING, "NONE");
                        player.sendMessage(Component.text("Disabling Elytra Trails"));
                        return true;
                    }
                    if (Arrays.stream(ElytraTrails.values()).anyMatch(e -> e.toString().equalsIgnoreCase(args[1]))) {
                        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING, args[1].toUpperCase());
                        player.sendMessage(Component.text("Enabling Elytra Trail: " + args[1].toUpperCase()));
                    } else {
                        player.sendMessage(Component.text(args[1].toUpperCase() + " is not a valid elytra trail!"));
                    }
                } else {
                    player.sendMessage(Component.text("Not enough arguments provided!"));
                }
                return true;
            } else if (args[0].equals("vanish")) {
                player.sendMessage(Component.text(AdvancedVanishAPI.INSTANCE.getVanishedPlayers().size()));
            }
        }
        return false;
    }
}