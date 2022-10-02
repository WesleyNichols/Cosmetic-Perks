package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.structures.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit extends Methods implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(AnimationManager.hasActiveAnimation(event.getPlayer())) {
            AnimationManager.removeParticleAnimation(event.getPlayer().getUniqueId());
            Bukkit.broadcast(Component.text("Removed Animation"));
        }
    }
}
