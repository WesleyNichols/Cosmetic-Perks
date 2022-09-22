package cosmetic.perks.cosmeticperks;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmeticPerks extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.broadcast(Component.text("haha lmao"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
