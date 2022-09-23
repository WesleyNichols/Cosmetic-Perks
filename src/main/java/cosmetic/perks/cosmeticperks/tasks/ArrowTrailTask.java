package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.enums.ArrowTrails;
import cosmetic.perks.cosmeticperks.managers.TrailItemManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ArrowTrailTask extends BukkitRunnable {
    public void run() {
        HashMap<UUID, ArrowTrails> arrowTrailList = TrailItemManager.getArrowTrailList();
        for (UUID arrowId : arrowTrailList.keySet()) {
            Entity arrowEntity = Bukkit.getEntity(arrowId);

            if (arrowEntity == null) {
                TrailItemManager.removeSavedArrowTrail(arrowId);
                continue;
            }

            if (arrowEntity.isOnGround() || arrowEntity.isDead()) {
                TrailItemManager.removeSavedArrowTrail(arrowId);
                continue;
            }

            ArrowTrails arrowTrailMap = arrowTrailList.get(arrowId);

            World worldServer = arrowEntity.getWorld();
            worldServer.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(worldServer.getUID()))
                    .filter(player -> player.getLocation().distance(arrowEntity.getLocation()) <= 40)
                    .forEach(player -> {
                                player.spawnParticle(arrowTrailMap.getTrailEffect(), arrowEntity.getLocation(), arrowTrailMap.getParticleAmount(),
                                        arrowTrailMap.getXOffSet(), arrowTrailMap.getYOffSet(), arrowTrailMap.getZOffSet(), arrowTrailMap.getParticleSpeed());
                            }
                    );
        }
    }
}
