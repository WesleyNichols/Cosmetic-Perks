package me.wesleynichols.cosmeticperks.tasks;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.ProjectileTrailManager;
import me.wesleynichols.cosmeticperks.managers.TrailMethods;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ProjectileTrailTask extends BukkitRunnable {

    public void run() {
        if (!CosmeticPerks.getInstance().isEnabled()) { this.cancel(); }

        HashMap<UUID, CustomTrail> projTrailList = ProjectileTrailManager.getProjTrailList();
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

            CustomTrail projTrails = projTrailList.get(projId);
            TrailMethods.spawnParticle(projEntity, projTrails);
        }
    }

}
