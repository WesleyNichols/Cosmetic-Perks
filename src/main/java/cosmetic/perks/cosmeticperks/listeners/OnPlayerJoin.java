package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.managers.TrailMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class OnPlayerJoin extends TrailMethods implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        removeOrAttachAnimation(player);
    }
}

