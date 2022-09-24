package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;


public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getPersistentDataContainer().has(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING)) {
            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, "NONE");
        }

        if (player.getPersistentDataContainer().has(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING)) {
            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, "NONE");
        }
    }

}
