package me.wesleynichols.cosmeticperks.commands;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.menus.CosmeticsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CosmeticCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            CosmeticsMenu menu = new CosmeticsMenu();
            menu.displayCosmeticsMenu(player);
            return true;
        }
        return false;
    }
}