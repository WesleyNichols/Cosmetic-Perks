package me.wesleynichols.cosmeticperks.commands;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.config.LimitedTrailStorage;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.structures.TrailType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.shanerx.mojang.Mojang;

import java.util.*;
import java.util.stream.Collectors;

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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length < 2) return false;

        String action = args[0].toLowerCase(Locale.ROOT);

        if (action.equals("view")) {
            return handleViewCommand(sender, args);
        }

        if (args.length < 3) return false;

        String playerName = args[1];
        String trailTypeStr = args[2].toLowerCase(Locale.ROOT);
        String trailName = (args.length >= 4) ? String.join(" ", Arrays.copyOfRange(args, 3, args.length)) : "all";

        if (!action.equals("grant") && !action.equals("revoke")) {
            sender.sendMessage(Component.text("Unknown action. Use 'grant', 'revoke', or 'view'.", NamedTextColor.RED));
            return true;
        }

        // Validate trail type safely
        TrailType trailType = null;
        if (!trailTypeStr.equals("all")) {
            try {
                trailType = TrailType.fromStringOrThrow(trailTypeStr);
            } catch (IllegalArgumentException ex) {
                sender.sendMessage(Component.text(ex.getMessage(), NamedTextColor.RED));
                return true;
            }
        }

        String mojangId = mojang.getUUIDOfUsername(playerName);
        if (mojangId == null || mojangId.isEmpty()) {
            sender.sendMessage(Component.text(playerName + " is not a valid user!", NamedTextColor.RED));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = target.getUniqueId();

        // Process all types if "all" is given for trailType and trailName
        if (trailTypeStr.equals("all") && trailName.equalsIgnoreCase("all")) {
            for (TrailType type : TrailType.values()) {
                processAllLimitedTrails(uuid, type, action);
            }
            sendConfirmation(sender, action, "all limited trails", target.getName());
            return true;
        }

        // Process all trails for a specific trail type
        if (trailName.equalsIgnoreCase("all")) {
            assert trailType != null;
            processAllLimitedTrails(uuid, trailType, action);
            sendConfirmation(sender, action, "all limited " + trailType.getName() + " trails", target.getName());
            return true;
        }

        // Check trail existence and validity
        if (!trailManager.doesTrailExist(trailName)) {
            sender.sendMessage(Component.text("Trail '" + trailName + "' does not exist.", NamedTextColor.RED));
            return true;
        }

        CustomTrail trail = trailManager.getTrail(trailName);
        if (!trail.isLimitedItem()) {
            sender.sendMessage(Component.text("Trail '" + trailName + "' is not a limited trail.", NamedTextColor.YELLOW));
            return true;
        }

        assert trailType != null;
        if (!trail.getTrailType().getName().equalsIgnoreCase(trailType.getName())) {
            sender.sendMessage(Component.text("Trail type mismatch. '" + trailName + "' is not a " + trailType.getName() + " trail.", NamedTextColor.RED));
            return true;
        }

        processTrail(action, uuid, trailType.getName(), trailName);
        sendConfirmation(sender, action, trailName, target.getName());
        return true;
    }

    private boolean handleViewCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }

        String playerName = args[1];
        String typeFilterStr = (args.length >= 3) ? args[2].toLowerCase(Locale.ROOT) : "all";

        List<TrailType> typesToCheck;

        if (typeFilterStr.equals("all")) {
            typesToCheck = Arrays.asList(TrailType.values());
        } else {
            try {
                typesToCheck = List.of(TrailType.fromStringOrThrow(typeFilterStr));
            } catch (IllegalArgumentException ex) {
                sender.sendMessage(Component.text(ex.getMessage(), NamedTextColor.RED));
                return true;
            }
        }

        String mojangId = mojang.getUUIDOfUsername(playerName);
        if (mojangId == null || mojangId.isEmpty()) {
            sender.sendMessage(Component.text(playerName + " is not a valid user!", NamedTextColor.RED));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = target.getUniqueId();

        Map<String, List<String>> grantedByType = new LinkedHashMap<>();

        for (TrailType type : typesToCheck) {
            List<String> granted = trailManager.getTrailsByType(type).stream()
                    .filter(CustomTrail::isLimitedItem)
                    .map(CustomTrail::getTrailName)
                    .filter(name -> storage.hasTrail(uuid, type.getName(), name))
                    .sorted()
                    .collect(Collectors.toList());

            if (!granted.isEmpty()) {
                grantedByType.put(type.getName(), granted);
            }
        }

        if (grantedByType.isEmpty()) {
            sender.sendMessage(Component.text(target.getName() + " has no granted limited trails.", NamedTextColor.YELLOW));
        } else {
            sender.sendMessage(Component.text(target.getName() + "'s Limited Trails:", NamedTextColor.GOLD));
            for (Map.Entry<String, List<String>> entry : grantedByType.entrySet()) {
                sender.sendMessage(Component.text(entry.getKey() + ":", NamedTextColor.AQUA));
                for (String trailName : entry.getValue()) {
                    sender.sendMessage(Component.text(" - " + trailName, NamedTextColor.GRAY));
                }
            }
        }

        return true;
    }

    private void processTrail(String action, UUID uuid, String trailTypeName, String trailName) {
        if (action.equals("grant")) {
            if (!storage.hasTrail(uuid, trailTypeName, trailName)) {
                storage.grantTrail(uuid, trailTypeName, trailName);
            }
        } else {
            if (storage.hasTrail(uuid, trailTypeName, trailName)) {
                storage.revokeTrail(uuid, trailTypeName, trailName);
            }
        }
    }

    private void processAllLimitedTrails(UUID uuid, TrailType type, String action) {
        for (CustomTrail trail : trailManager.getTrailsByType(type)) {
            if (trail.isLimitedItem()) {
                processTrail(action, uuid, type.getName(), trail.getTrailName());
            }
        }
    }


    private void sendConfirmation(CommandSender sender, String action, String trailInfo, String playerName) {
        sender.sendMessage(Component.text(capitalize(action) + " " + trailInfo + " for " + playerName, NamedTextColor.GREEN));
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
    }
}
