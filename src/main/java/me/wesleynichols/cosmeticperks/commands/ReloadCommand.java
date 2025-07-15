package me.wesleynichols.cosmeticperks.commands;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.AnimationManager;
import me.wesleynichols.cosmeticperks.managers.AnimationValueManager;
import me.wesleynichols.cosmeticperks.managers.ProjectileTrailManager;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CosmeticPerks plugin = CosmeticPerks.getInstance();

        try {
            plugin.getAnimationManager().clearParticleAnimationList();
            plugin.getAnimationValueManager().clearAnimationValueList();
            plugin.getProjectileTrailManager().clearProjTrailList();
            plugin.getTrailManager().clearTrailLists();
            plugin.reloadConfigs();

            sender.sendMessage(Component.text("[CosmeticPerks] Config reloaded!", NamedTextColor.GREEN));
            return true;

        } catch (Exception e) {
            sender.sendMessage(Component.text("[CosmeticPerks] An error occurred while reloading configs!", NamedTextColor.RED));
            plugin.getLogger().log(Level.SEVERE, "An error occurred while reloading configs:", e);
            return false;
        }
    }
}
