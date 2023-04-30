package me.wesleynichols.cosmeticperks;

import me.wesleynichols.cosmeticperks.commands.CosmeticCommand;
import me.wesleynichols.cosmeticperks.commands.ReloadCommand;
import me.wesleynichols.cosmeticperks.commands.ShopCommand;
import me.wesleynichols.cosmeticperks.config.ConfigParser;
import me.wesleynichols.cosmeticperks.config.CustomConfig;
import me.wesleynichols.cosmeticperks.listeners.PlayerEventListener;
import me.wesleynichols.cosmeticperks.managers.TrailMethods;
import me.wesleynichols.cosmeticperks.tasks.AnimationTask;
import me.wesleynichols.cosmeticperks.tasks.ProjectileTrailTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CosmeticPerks extends JavaPlugin {

    private static CosmeticPerks plugin;

    public static CosmeticPerks getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveConfig();
        reloadConfigs();

        registerEvent(new PlayerEventListener());

        registerCommand("cosmetic", new CosmeticCommand());
        registerCommand("reload", new ReloadCommand());
        registerCommand("shop", new ShopCommand());

        new ProjectileTrailTask().runTaskTimer(this, 1L, 1L);
        new AnimationTask().runTaskTimer(this, 1L, 1L);
    }

    public void registerEvent(Listener event) {
        getServer().getPluginManager().registerEvents(event, this);
    }

    public void registerCommand(String command, CommandExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(executor);
    }

    public void reloadConfigs() {
        CustomConfig.load("player.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "player");
        CustomConfig.load("elytra.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "elytra");
        CustomConfig.load("projectile.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "projectile");
        for(Player player: Bukkit.getOnlinePlayers()) {
            TrailMethods.removeOrAttachAnimation(player);
        }
    }
}
