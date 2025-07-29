package me.wesleynichols.cosmeticperks.commands.tabcomplete;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.trails.CustomTrail;
import me.wesleynichols.cosmeticperks.trails.TrailManager;
import me.wesleynichols.cosmeticperks.trails.TrailType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LimitedTrailTabCompleter implements TabCompleter {

    private static final List<String> ACTIONS = List.of("grant", "revoke", "view");
    private static final String ALL = "all";

    private final TrailManager trailManager;

    public LimitedTrailTabCompleter() {
        this.trailManager = CosmeticPerks.getInstance().getTrailManager();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        return switch (args.length) {
            case 1 -> partialMatch(args[0], ACTIONS);
            case 2 -> null; // Let Bukkit handle player name completion
            case 3 -> {
                String action = args[0].toLowerCase(Locale.ROOT);
                if (action.equals("view")) {
                    yield partialMatch(args[2], getTrailTypeNames());
                } else {
                    yield partialMatch(args[2], withAll(getTrailTypeNames()));
                }
            }
            case 4 -> {
                String action = args[0].toLowerCase(Locale.ROOT);
                if (!action.equals("grant") && !action.equals("revoke")) {
                    yield List.of();
                }

                String typeInput = args[2].toLowerCase(Locale.ROOT);
                if (typeInput.equals(ALL)) {
                    yield List.of();
                }

                Optional<TrailType> typeOpt = TrailType.fromString(typeInput);
                if (typeOpt.isEmpty()) {
                    yield List.of();
                }

                TrailType type = typeOpt.get();

                List<String> trails = trailManager.getTrailsByType(type).stream()
                        .filter(CustomTrail::isLimitedItem)
                        .map(CustomTrail::getTrailName)
                        .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(args[3].toLowerCase(Locale.ROOT)))
                        .sorted()
                        .collect(Collectors.toList());

                trails.add(ALL);
                yield trails;
            }
            default -> List.of();
        };
    }

    private List<String> partialMatch(String input, List<String> options) {
        String lowerInput = input.toLowerCase(Locale.ROOT);
        return options.stream()
                .filter(opt -> opt.toLowerCase(Locale.ROOT).startsWith(lowerInput))
                .sorted()
                .toList();
    }

    private List<String> getTrailTypeNames() {
        return Arrays.stream(TrailType.values())
                .map(TrailType::getName)
                .collect(Collectors.toList());
    }

    private List<String> withAll(List<String> baseList) {
        List<String> copy = new ArrayList<>(baseList);
        copy.add(ALL);
        return copy;
    }
}
