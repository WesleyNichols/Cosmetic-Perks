package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.structures.Methods;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class OnPlayerJoin extends Methods implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!hasActiveTrail(player, "player")) { removeActiveTrail(player, "player"); } else { callAttachParticleAnimation(player, "player"); }

        if (!hasActiveTrail(player, "projectile")) { removeActiveTrail(player, "projectile"); } else { callAttachParticleAnimation(player, "projectile"); }

        if (!hasActiveTrail(player, "elytra")) { removeActiveTrail(player, "elytra"); }  else { callAttachParticleAnimation(player, "elytra"); }

        //Attach animation trail to player
        //TODO attachParticleAnimation(player, player.getUniqueId(), "player-animation");
    }
}

