package me.wesleynichols.cosmeticperks.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Arrays;
import java.util.List;

import static me.wesleynichols.cosmeticperks.menus.CosmeticsMenu.capitalize;

public class ItemUtils {
    /**
     * Basic Lore for Main Menu items
     */
    public static List<Component> buildTrailLore(String trailName) {
        return Arrays.asList(
                Component.empty(),
                Component.text("Current:", NamedTextColor.YELLOW)
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text(capitalize(trailName), NamedTextColor.DARK_GREEN)
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );
    }

    /**
     * Basic Name for Main Menu items (defaults to not bold)
     */
    public static Component buildItemName(String name, NamedTextColor color) {
        return buildItemName(name, color, false); // Default: not bold
    }

    /**
     * Flexible Name builder that allows specifying bold
     */
    public static Component buildItemName(String name, NamedTextColor color, boolean bold) {
        Component component = Component.text(name)
                .color(color)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE); // Always disable italics

        if (bold) {
            component = component.decorate(TextDecoration.BOLD);
        }

        return component;
    }
}
