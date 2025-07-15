package me.wesleynichols.cosmeticperks.commands.tabcomplete;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LimitedTrailTabCompleter implements TabCompleter {

    private static final List<String> ACTIONS = Arrays.asList("grant", "revoke");
    private static final List<String> TRAIL_TYPES = Arrays.asList("player", "elytra", "projectile");

    private final TrailManager trailManager;

    public LimitedTrailTabCompleter() {
        this.trailManager = CosmeticPerks.getInstance().getTrailManager();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        switch (args.length) {
            case 1 -> {
                return partialMatch(args[0], ACTIONS);
            }
            case 2 -> {
                return null; // Let Bukkit autocomplete player names
            }
            case 3 -> {
                return partialMatch(args[2], TRAIL_TYPES);
            }
            default -> {
                String trailType = args[2].toLowerCase();
                List<CustomTrail> trails = trailManager.getTrailsByType(trailType);

                return trails.stream()
                        .filter(CustomTrail::isLimitedItem) // Only limited trails
                        .map(CustomTrail::getTrailName)
                        .filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .sorted()
                        .collect(Collectors.toList());
            }
        }
    }

    private List<String> partialMatch(String input, List<String> options) {
        return options.stream()
                .filter(opt -> opt.toLowerCase().startsWith(input.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
