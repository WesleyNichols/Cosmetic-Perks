package cosmetic.perks.cosmeticperks.commands;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import cosmetic.perks.cosmeticperks.menus.CosmeticsMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CosmeticsCommand implements CommandExecutor {

    public static String getCommand = "cosmetic";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                CosmeticsMenu menu = new CosmeticsMenu();
                menu.displayCosmeticsMenu(player);
                return true;
            } else if (args[0].equalsIgnoreCase("reload") && player.hasPermission("cosmetics.reload")) {
                String returnValue = runReloadCommand();
                player.sendMessage(Component.text(returnValue));
                return true;
            }
        } else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("cosmetics.reload")) {
            String returnValue = runReloadCommand();
            Bukkit.getServer().getConsoleSender().sendMessage(returnValue);
            return true;
        }
        return false;
    }

    public String runReloadCommand() {
        try {
            AnimationManager.clearParticleAnimationList();
            AnimationValueManager.clearAnimationValueList();
            ProjectileTrailManager.clearProjTrailList();
            TrailManager.clearTrailLists();
            CosmeticPerks.getInstance().reloadConfigs();
        } catch (Exception e) {
            e.printStackTrace();
            return ChatColor.RED + "[CosmeticPerks] An error occurred when trying to reload the configs.";
        }
        return ChatColor.GREEN + "[CosmeticPerks] Config reloaded!";
    }
}