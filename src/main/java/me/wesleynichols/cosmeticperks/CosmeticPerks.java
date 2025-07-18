package me.wesleynichols.cosmeticperks;

import me.wesleynichols.cosmeticperks.commands.CosmeticCommand;
import me.wesleynichols.cosmeticperks.commands.LimitedTrailCommand;
import me.wesleynichols.cosmeticperks.commands.ReloadCommand;
import me.wesleynichols.cosmeticperks.commands.ShopCommand;
import me.wesleynichols.cosmeticperks.commands.tabcomplete.LimitedTrailTabCompleter;
import me.wesleynichols.cosmeticperks.config.ConfigParser;
import me.wesleynichols.cosmeticperks.config.CustomConfig;
import me.wesleynichols.cosmeticperks.config.LimitedTrailStorage;
import me.wesleynichols.cosmeticperks.listeners.PlayerEventListener;
import me.wesleynichols.cosmeticperks.managers.AnimationManager;
import me.wesleynichols.cosmeticperks.managers.AnimationValueManager;
import me.wesleynichols.cosmeticperks.managers.ProjectileTrailManager;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.structures.TrailType;
import me.wesleynichols.cosmeticperks.tasks.AnimationTask;
import me.wesleynichols.cosmeticperks.tasks.ProjectileTrailTask;
import me.wesleynichols.cosmeticperks.util.TrailUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CosmeticPerks extends JavaPlugin {

    private static CosmeticPerks instance;

    public static CosmeticPerks getInstance() {
        return instance;
    }

    private AnimationManager animationManager;
    private AnimationValueManager animationValueManager;
    private ProjectileTrailManager projectileTrailManager;
    private TrailManager trailManager;
    private LimitedTrailStorage limitedTrailStorage;


    @Override
    public void onEnable() {
        instance = this;

        // Initialize managers
        animationManager = new AnimationManager(this);
        animationValueManager = new AnimationValueManager();
        projectileTrailManager = new ProjectileTrailManager();
        trailManager = new TrailManager();
        limitedTrailStorage = new LimitedTrailStorage(this);

        saveDefaultConfig();
        reloadConfigs();

        registerEvent(new PlayerEventListener());

        registerCommand("cosmetic", new CosmeticCommand());
        registerCommand("limitedtrail", new LimitedTrailCommand());
        registerCommand("reload", new ReloadCommand());
        registerCommand("shop", new ShopCommand());

        registerTabComplete("limitedtrail", new LimitedTrailTabCompleter());

        new AnimationTask(this).runTaskTimer(this, 1L, 1L);
        new ProjectileTrailTask(this).runTaskTimer(this, 1L, 1L);
    }

    // Getters for managers
    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public AnimationValueManager getAnimationValueManager() {
        return animationValueManager;
    }

    public ProjectileTrailManager getProjectileTrailManager() {
        return projectileTrailManager;
    }

    public TrailManager getTrailManager() {
        return trailManager;
    }

    public LimitedTrailStorage getLimitedTrailStorage() {
        return limitedTrailStorage;
    }

    public void registerEvent(Listener event) {
        getServer().getPluginManager().registerEvents(event, this);
    }

    public void registerCommand(String command, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(command)).setExecutor(executor);
    }

    public void registerTabComplete(String command, TabCompleter executor) {
        Objects.requireNonNull(getCommand(command)).setTabCompleter(executor);
    }

    public void reloadConfigs() {
        CustomConfig playerConfig = new CustomConfig(this, "player.yml");
        playerConfig.load();
        playerConfig.save();

        CustomConfig elytraConfig = new CustomConfig(this, "elytra.yml");
        elytraConfig.load();
        elytraConfig.save();

        CustomConfig projectileConfig = new CustomConfig(this, "projectile.yml");
        projectileConfig.load();
        projectileConfig.save();

        ConfigParser.parseConfig(playerConfig.getConfig(), TrailType.PLAYER);
        ConfigParser.parseConfig(elytraConfig.getConfig(), TrailType.ELYTRA);
        ConfigParser.parseConfig(projectileConfig.getConfig(), TrailType.PROJECTILE);

        for (Player player : Bukkit.getOnlinePlayers()) {
            TrailUtils.removeOrAttachAnimation(player);
        }

        instance.getLimitedTrailStorage().cleanupLimitedTrails(instance.getTrailManager());
    }
}
