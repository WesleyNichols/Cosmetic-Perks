package me.wesleynichols.cosmeticperks.tasks;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.util.TrailUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class ProjectileTrailTask extends BukkitRunnable {

    private final CosmeticPerks plugin;

    public ProjectileTrailTask(CosmeticPerks plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        CosmeticPerks plugin = CosmeticPerks.getInstance();
        if (!plugin.isEnabled()) {
            this.cancel();
            return;
        }

        Map<UUID, CustomTrail> projTrailList = plugin.getProjectileTrailManager().getProjTrailList();

        for (Map.Entry<UUID, CustomTrail> entry : projTrailList.entrySet()) {
            UUID projId = entry.getKey();
            CustomTrail trail = entry.getValue();

            Entity projEntity = Bukkit.getEntity(projId);

            if (projEntity == null || projEntity.isDead() || projEntity.isOnGround()) {
                plugin.getProjectileTrailManager().removeProjTrail(projId);
                continue;
            }

            TrailUtils.spawnParticle(projEntity, trail);
        }
    }
}
