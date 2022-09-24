package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ProjectileTrailTask extends BukkitRunnable {
    public void run() {
        if (!CosmeticPerks.getInstance().isEnabled()) { this.cancel(); }

        HashMap<UUID, ProjectileTrails> projTrailList = ProjectileTrailManager.getProjTrailList();
        for (UUID projId : projTrailList.keySet()) {
            Entity projEntity = Bukkit.getEntity(projId);

            if (projEntity == null) {
                ProjectileTrailManager.removeProjTrail(projId);
                continue;
            }

            if (projEntity.isOnGround() || projEntity.isDead()) {
                ProjectileTrailManager.removeProjTrail(projId);
                continue;
            }

            ProjectileTrails projTrails = projTrailList.get(projId);

            World world = projEntity.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(projEntity.getLocation()) <= 40)
                    .forEach(player -> {
                                player.spawnParticle(projTrails.getTrailEffect(), projEntity.getLocation(), projTrails.getParticleAmount(),
                                        projTrails.getXOffSet(), projTrails.getYOffSet(), projTrails.getZOffSet(), projTrails.getParticleSpeed());
                            }
                    );
        }
    }
}
