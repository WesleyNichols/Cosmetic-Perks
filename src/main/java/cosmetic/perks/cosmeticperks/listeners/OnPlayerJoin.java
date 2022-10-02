package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class OnPlayerJoin extends TrailManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!hasActiveTrail(player, "player")) { removeActiveTrail(player, "player"); } else { AnimationManager.callAttachParticleAnimation(player, "player"); }

        if (!hasActiveTrail(player, "projectile")) { removeActiveTrail(player, "projectile"); } else { AnimationManager.callAttachParticleAnimation(player, "projectile"); }

        if (!hasActiveTrail(player, "elytra")) { removeActiveTrail(player, "elytra"); }  else { AnimationManager.callAttachParticleAnimation(player, "elytra"); }

    }
}

