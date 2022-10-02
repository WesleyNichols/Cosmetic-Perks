package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.structures.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class OnPlayerJoin extends Methods implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!hasActiveTrail(player, "player")) { removeActiveTrail(player, "player"); } else { callAttachParticleAnimation(player, "player"); }

        if (!hasActiveTrail(player, "projectile")) { removeActiveTrail(player, "projectile"); } else { callAttachParticleAnimation(player, "projectile"); }

        if (!hasActiveTrail(player, "elytra")) { removeActiveTrail(player, "elytra"); }  else { callAttachParticleAnimation(player, "elytra"); }

    }
}

