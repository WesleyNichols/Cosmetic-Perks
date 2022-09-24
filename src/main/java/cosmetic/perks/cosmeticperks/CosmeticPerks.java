package cosmetic.perks.cosmeticperks;

import cosmetic.perks.cosmeticperks.commands.Cosmetic;
import cosmetic.perks.cosmeticperks.commands.CosmeticsCommand;
import cosmetic.perks.cosmeticperks.listeners.GUIClick;
import cosmetic.perks.cosmeticperks.listeners.OnProjectileShot;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerMove;
import cosmetic.perks.cosmeticperks.tasks.ProjectileTrailTask;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmeticPerks extends JavaPlugin {

    private static CosmeticPerks instance;

    @Override
    public void onEnable() {
        instance = this;

        this.registerEvent(new GUIClick());
        this.registerEvent(new OnProjectileShot());
        this.registerEvent(new OnPlayerMove());

        this.registerCommand(CosmeticsCommand.getMainCommand, new CosmeticsCommand());
        this.registerCommand(Cosmetic.getMainCommand, new Cosmetic());

        new ProjectileTrailTask().runTaskTimer(this, 1L, 1L);
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
}
