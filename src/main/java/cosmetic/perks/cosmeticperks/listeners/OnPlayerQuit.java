package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit extends TrailManager implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(AnimationManager.hasActiveAnimation(event.getPlayer())) {
            AnimationManager.removeParticleAnimation(event.getPlayer().getUniqueId());
        }
    }
}
