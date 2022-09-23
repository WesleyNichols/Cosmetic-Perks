package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.enums.ArrowTrails;
import cosmetic.perks.cosmeticperks.managers.TrailItemManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class OnArrowTrailShot implements Listener {

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (TrailManager.hasSavedArrowTrail(player.getUniqueId())) {
                ArrowTrails trailMap = TrailManager.getSavedArrowTrail(player.getUniqueId());
                TrailItemManager.addArrowTrail(event.getProjectile().getUniqueId(), trailMap);
            }
        }
    }

}