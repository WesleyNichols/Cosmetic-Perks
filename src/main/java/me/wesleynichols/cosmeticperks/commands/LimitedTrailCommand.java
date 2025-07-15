package me.wesleynichols.cosmeticperks.commands;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.config.LimitedTrailStorage;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.shanerx.mojang.Mojang;

import java.util.Arrays;
import java.util.UUID;

public class LimitedTrailCommand implements CommandExecutor {

    private final LimitedTrailStorage storage;
    private final TrailManager trailManager;
    private final Mojang mojang = new Mojang().connect();

    public LimitedTrailCommand() {
        CosmeticPerks plugin = CosmeticPerks.getInstance();
        this.storage = plugin.getLimitedTrailStorage();
        this.trailManager = plugin.getTrailManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 4) { return false; }

        String action = args[0].toLowerCase();
        String playerName = args[1];
        String trailType = args[2].toLowerCase();
        String trailName = String.join(" ", Arrays.copyOfRange(args, 3, args.length));  // Join remaining args as trail name

        // Verbose input validation
        if (!trailType.equals("player") && !trailType.equals("elytra") && !trailType.equals("projectile")) {
            sender.sendMessage(Component.text("Invalid trail type. Use 'player', 'elytra', or 'projectile'.", NamedTextColor.RED));
            return true;
        }

        if (!trailManager.doesTrailExist(trailName)) {
            sender.sendMessage(Component.text("Trail '" + trailName + "' does not exist.", NamedTextColor.RED));
            return true;
        }

        CustomTrail trail = trailManager.getTrail(trailName);

        if (!trail.isLimitedItem()) {
            sender.sendMessage(Component.text("Trail '" + trailName + "' is not a limited trail.", NamedTextColor.YELLOW));
            return true;
        }

        if (!trail.getTrailType().equalsIgnoreCase(trailType)) {
            sender.sendMessage(Component.text("Trail type '" + trailType + "' does not match the trail's actual type '" + trail.getTrailType() + "'.", NamedTextColor.RED));
            return true;
        }

        // Is provided player real
        String mojangId = mojang.getUUIDOfUsername(playerName);
        if (mojangId == null || mojangId.isEmpty()) {
            sender.sendMessage(Component.text(playerName + " is not a valid user!", NamedTextColor.RED));
            return true;
        }

        // Grant/revoke
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = target.getUniqueId();

        // HERE?

        if (action.equals("grant")) {
            if (storage.hasTrail(uuid, trailType, trailName)) {
                sender.sendMessage(Component.text(target.getName() + " already has the " + trailType + " trail '" + trailName + "'.", NamedTextColor.YELLOW));
                return true;
            }

            storage.grantTrail(uuid, trailType, trailName);
            sender.sendMessage(Component.text("Granted " + trailType + " trail '" + trailName + "' to " + target.getName() + ".", NamedTextColor.GREEN));
        } else if (action.equals("revoke")) {
            if (!storage.hasTrail(uuid, trailType, trailName)) {
                sender.sendMessage(Component.text(target.getName() + " does not have the " + trailType + " trail '" + trailName + "'.", NamedTextColor.YELLOW));
                return true;
            }

            storage.revokeTrail(uuid, trailType, trailName);
            sender.sendMessage(Component.text("Revoked " + trailType + " trail '" + trailName + "' from " + target.getName() + ".", NamedTextColor.RED));
        } else {
            sender.sendMessage(Component.text("Unknown action. Use 'grant' or 'revoke'.", NamedTextColor.RED));
        }

        return true;
    }
}
