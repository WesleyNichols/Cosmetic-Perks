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
        if (!hasActiveTrail(player, "player")) { removeActiveTrail(player, "player"); }

        if (!hasActiveTrail(player, "projectile")) { removeActiveTrail(player, "projectile"); }

        if (!hasActiveTrail(player, "elytra")) { removeActiveTrail(player, "elytra"); }

        if (!hasActiveTrail(player, "player-animation")) { removeActiveTrail(player, "player-animation"); }

        if (!hasActiveTrail(player, "projectile-animation")) { removeActiveTrail(player, "projectile-animation"); }

        //Attach animation trail to player
        attachParticleAnimation(player, player.getUniqueId(), "player-animation");
    }
}

