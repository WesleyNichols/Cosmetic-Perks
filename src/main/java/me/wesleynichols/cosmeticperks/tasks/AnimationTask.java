package me.wesleynichols.cosmeticperks.tasks;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.AnimationManager;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AnimationTask extends BukkitRunnable {

    public void run() {
        if (!CosmeticPerks.getInstance().isEnabled()) { this.cancel(); }

        HashMap<UUID, CustomTrail> particleAnimationList = AnimationManager.getParticleAnimationList();
        for (UUID uuid : particleAnimationList.keySet()) {
            Entity target = Bukkit.getEntity(uuid);
            //assert target != null;

            if ((target instanceof Player && target.isDead()) || target == null) { return; }

            CustomTrail particleProperties = particleAnimationList.get(uuid);
            AnimationValues particleAnimationValues = particleProperties.getAnimation();
            int step = particleAnimationValues.getCurrentStep();
            double[] offset = particleProperties.getOffset();

            World world = target.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(target.getLocation()) <= 40)
                    .forEach(player -> {
                                if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player) || player.getGameMode() == GameMode.SPECTATOR) { return; }
                                if(particleAnimationValues.getEquationValuesLength() != 0) {
                                    for(double[][] loc: particleAnimationValues.getEquationValues()) {
                                        player.spawnParticle(particleProperties.getTrailEffect(), target.getLocation().add(loc[step][0], loc[step][1], loc[step][2]), particleProperties.getParticleAmount(),
                                                offset[0], offset[1], offset[2], particleProperties.getParticleSpeed());
                                    }
                                }
                                if(particleAnimationValues.getStyleValuesLength() != 0) {
                                    for(double[] loc: particleAnimationValues.getStyleValues()) {
                                        player.spawnParticle(particleProperties.getTrailEffect(), target.getLocation().add(loc[0], loc[1], loc[2]), particleProperties.getParticleAmount(),
                                                offset[0], offset[1], offset[2], particleProperties.getParticleSpeed());
                                    }
                                }
                            }
                    );
            if(particleAnimationValues.getEquationValuesLength() != 0) {
                particleAnimationValues.addStep();
            }
        }
    }
}
