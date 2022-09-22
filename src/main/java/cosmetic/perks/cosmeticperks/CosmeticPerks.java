package cosmetic.perks.cosmeticperks;

import cosmetic.perks.cosmeticperks.commands.Cosmetic;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmeticPerks extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        getCommand("accept").setExecutor(new Cosmetic());
    }

    public void registerEvents() {

    }
}
