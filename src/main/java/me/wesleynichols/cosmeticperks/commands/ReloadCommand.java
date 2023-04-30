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

public class ReloadCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            AnimationManager.clearParticleAnimationList();
            AnimationValueManager.clearAnimationValueList();
            ProjectileTrailManager.clearProjTrailList();
            TrailManager.clearTrailLists();
            CosmeticPerks.getInstance().reloadConfigs();
            sender.sendMessage(Component.text("[CosmeticPerks] Config reloaded!", NamedTextColor.GREEN));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(Component.text("[CosmeticPerks] An error occurred when trying to reload the configs.", NamedTextColor.RED));
            return false;
        }
    }
}
