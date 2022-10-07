package cosmetic.perks.cosmeticperks;

import cosmetic.perks.cosmeticperks.commands.CosmeticsCommand;
import cosmetic.perks.cosmeticperks.commands.ShopCommand;
import cosmetic.perks.cosmeticperks.config.ConfigParser;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerJoin;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerQuit;
import cosmetic.perks.cosmeticperks.listeners.OnProjectileShot;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerMove;
import cosmetic.perks.cosmeticperks.tasks.AnimationTask;
import cosmetic.perks.cosmeticperks.tasks.ProjectileTrailTask;
import cosmetic.perks.cosmeticperks.config.CustomConfig;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmeticPerks extends JavaPlugin {

    private static CosmeticPerks instance;

    @Override
    public void onEnable() {
        instance = this;

        saveConfigs();

        this.registerEvent(new OnPlayerJoin());
        this.registerEvent(new OnPlayerQuit());
        this.registerEvent(new OnPlayerMove());
        this.registerEvent(new OnProjectileShot());

        this.registerCommand(CosmeticsCommand.getCommand, new CosmeticsCommand());
        this.registerCommand(ShopCommand.getCommand, new ShopCommand());

        new ProjectileTrailTask().runTaskTimer(this, 1L, 1L);
        new AnimationTask().runTaskTimer(this, 1L, 1L);
    }

    @Override
    public void onDisable() {
    }

    public static CosmeticPerks getInstance() {
        return instance;
    }

    public void registerEvent(Listener event) {
        this.getServer().getPluginManager().registerEvents(event, this);
    }

    public void registerCommand(String command, CommandExecutor executor) {
        instance.getCommand(command).setExecutor(executor);
    }

    public void saveConfigs() {
        CustomConfig.load("player.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "player");
        CustomConfig.load("elytra.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "elytra");
        CustomConfig.load("projectile.yml");
        CustomConfig.save();
        ConfigParser.parseConfig(CustomConfig.get(), "projectile");
    }
}
