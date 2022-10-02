package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import org.bukkit.Bukkit;
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

            CustomTrail.TrailProperties projTrails = projTrailList.get(projId).getProperties();

            AnimationManager.spawnParticle(projEntity, projTrails);
        }
    }

}
