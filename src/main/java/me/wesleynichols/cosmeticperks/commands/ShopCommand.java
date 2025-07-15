package me.wesleynichols.cosmeticperks.commands;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
            return true;
        }

        player.openBook(createShopBook());
        return true;
    }

    private ItemStack createShopBook() {
        String shopUrl = CosmeticPerks.getInstance().getConfig().getString("shop-url", "https://store.beeboxmc.com");

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        TextComponent bookText = Component.empty()
                .append(Component.text("\n        Shop\n\n", NamedTextColor.BLACK)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text(" ✧  ✦  ✧  ✪  ✧  ✦  ✧\n\n", NamedTextColor.DARK_PURPLE)
                        .decoration(TextDecoration.BOLD, false))
                .append(Component.text("All purchases directly support the server!\n\n", NamedTextColor.BLACK)
                        .append(Component.text("  [Click to Shop]", NamedTextColor.DARK_AQUA)
                                .decorate(TextDecoration.BOLD)
                                .clickEvent(ClickEvent.openUrl(shopUrl))))
                .append(Component.text("\n\n  [Back to Menu]", NamedTextColor.DARK_BLUE)
                        .decorate(TextDecoration.BOLD)
                        .clickEvent(ClickEvent.runCommand("/cosmetic")));

        bookMeta.setAuthor("");
        bookMeta.setTitle("");
        bookMeta.addPages(bookText);
        book.setItemMeta(bookMeta);

        return book;
    }

}
