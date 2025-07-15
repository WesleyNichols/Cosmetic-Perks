package me.wesleynichols.cosmeticperks.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.codehaus.plexus.util.StringUtils;

import java.util.List;

public class ItemUtils {

    /**
     * Builds the basic lore for trail menu items.
     */
    public static List<Component> buildTrailLore(String trailName) {
        return List.of(
                Component.empty(),
                Component.text("Current:", NamedTextColor.YELLOW)
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text(StringUtils.capitalise(trailName), NamedTextColor.DARK_GREEN)
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );
    }

    /**
     * Builds an item name with color, not bold by default.
     */
    public static Component buildItemName(String name, NamedTextColor color) {
        return buildItemName(name, color, false);
    }

    /**
     * Builds an item name with color and optional bold style.
     */
    public static Component buildItemName(String name, NamedTextColor color, boolean bold) {
        Component component = Component.text(name)
                .color(color)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        return bold ? component.decorate(TextDecoration.BOLD) : component;
    }
}
