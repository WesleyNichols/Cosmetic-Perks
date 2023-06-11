package me.wesleynichols.cosmeticperks.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

    private static final String shop = "https://store.beeboxmc.com";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookmeta = (BookMeta) book.getItemMeta();
            bookmeta.setAuthor("");
            bookmeta.setTitle("");

            TextComponent textComponent = Component.text("\n        Shop\n\n", NamedTextColor.BLACK)
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" ✧  ✦  ✧  ✪  ✧  ✦  ✧\n\n", NamedTextColor.DARK_PURPLE)
                            .decoration(TextDecoration.BOLD, false)
                            .append(Component.text("All purchases directly support the server!\n\n", NamedTextColor.BLACK)
                                    .decoration(TextDecoration.BOLD, false)
                                    .append(Component.text("  [Click to Shop]", NamedTextColor.DARK_AQUA)
                                            .decoration(TextDecoration.BOLD, true)
                                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, shop))
                                    )));

            if (args.length != 0) {
                if (args[0].equals("cosmetic")) {
                    textComponent = textComponent.append(Component.text("\n\n  [Back to Menu]", NamedTextColor.DARK_BLUE)
                            .decoration(TextDecoration.BOLD, true)
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/cosmetic")));
                }
            }

            bookmeta.addPages(textComponent);
            book.setItemMeta(bookmeta);

            player.openBook(book);
            return true;
        }
        return false;
    }

}
