package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import cosmetic.perks.cosmeticperks.structures.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit extends Methods implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(hasActiveAnimation(event.getPlayer())) {
//            TODO ParticleAnimationManager.removeParticleAnimation(event.getPlayer().getUniqueId());
            Bukkit.broadcast(Component.text("Removed Animation"));
        }
    }
}
