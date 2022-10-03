package cosmetic.perks.cosmeticperks;

import cosmetic.perks.cosmeticperks.commands.CosmeticsCommand;
import cosmetic.perks.cosmeticperks.commands.ShopCommand;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerJoin;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerQuit;
import cosmetic.perks.cosmeticperks.listeners.OnProjectileShot;
import cosmetic.perks.cosmeticperks.listeners.OnPlayerMove;
import cosmetic.perks.cosmeticperks.structures.Animations;
import cosmetic.perks.cosmeticperks.styles.Styles;
import cosmetic.perks.cosmeticperks.tasks.AnimationTask;
import cosmetic.perks.cosmeticperks.tasks.ProjectileTrailTask;
import cosmetic.perks.cosmeticperks.util.AnimationValueInitialize;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmeticPerks extends JavaPlugin {

    private static CosmeticPerks instance;

    @Override
    public void onEnable() {
        instance = this;

        this.initializeAnimations();

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

    public void initializeAnimations() {
        new AnimationValueInitialize("Fire Circle1",
                Styles.styleValues(//Styles.circle(1.25, 30, new double[]{45, 0, 0}, new double[]{0, 1, 0}),
                        //Styles.circle(1.25, 30, new double[]{-45, 0, 0}, new double[]{0, 1, 0}),
                        Styles.rectangle(1, 1,  30, null, null)
                        ));

        new AnimationValueInitialize("Totem Circle",
                Styles.styleValues(Styles.circle(1.0, 10, null, null)),
                new Animations(new String[]{"xcos(16x)", "x/5 + 0.5", "xsin(16x)", "-xcos(16x)", "x/5 + 0.5", "-xsin(16x)"}, 66, 1, true));

        new AnimationValueInitialize("Totem Circle2",
                new Animations(new String[]{"cos(x/6)", "sin(x/6)", "sin(x/8)", "-cos(x/6)", "sin(x/6)", "-sin(x/8)"}, 20, 16, true));

        new AnimationValueInitialize("Fire Circle",
                new Animations(new String[]{"cos(x)", "0", "sin(x)", "-1.1*cos(x)", "0.2", "-1.1*sin(x)", "1.2*cos(x)", "0.4", "1.2*sin(x)", "-1.3*cos(x)", "0.6", "-1.3*sin(x)", "1.4*cos(x)", "0.8", "1.4*sin(x)", "-1.5*cos(x)", "1", "-1.5*sin(x)"}, 20, 2, false));
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
