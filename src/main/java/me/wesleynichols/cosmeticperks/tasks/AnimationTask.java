package me.wesleynichols.cosmeticperks.tasks;

import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class AnimationTask extends BukkitRunnable {

    private final CosmeticPerks plugin;
    private static final double MAX_RENDER_DISTANCE = 40.0;

    public AnimationTask(CosmeticPerks plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        CosmeticPerks plugin = CosmeticPerks.getInstance();
        if (!plugin.isEnabled()) {
            this.cancel();
            return;
        }

        Map<UUID, CustomTrail> animations = plugin.getAnimationManager().getParticleAnimationList();

        for (Map.Entry<UUID, CustomTrail> entry : animations.entrySet()) {
            UUID uuid = entry.getKey();
            CustomTrail trail = entry.getValue();

            // Skip invalid or dead entities
            Entity target = Bukkit.getEntity(uuid);
            if (target == null || target.isDead() || (target instanceof Player && target.isDead())) { continue; }

            // No animation values to process
            AnimationValues animationValues = trail.getAnimation();
            if (animationValues == null) { continue; }

            int currentStep = animationValues.getCurrentStep();
            double[] offset = trail.getOffset();
            World world = target.getWorld();

            // Filter players in the same world within render distance
            world.getPlayers().stream()
                    .filter(player -> !AdvancedVanishAPI.INSTANCE.isPlayerVanished(player))
                    .filter(player -> player.getGameMode() != GameMode.SPECTATOR)
                    .filter(player -> !player.isGliding())
                    .filter(player -> player.getLocation().distanceSquared(target.getLocation()) <= MAX_RENDER_DISTANCE * MAX_RENDER_DISTANCE)
                    .forEach(player -> {
                        // Spawn particles from equation values if available
                        if (animationValues.getEquationValuesLength() > 0) {
                            for (double[][] locArray : animationValues.getEquationValues()) {
                                double[] loc = locArray[currentStep];
                                player.spawnParticle(
                                        trail.getTrailEffect(),
                                        target.getLocation().add(loc[0], loc[1], loc[2]),
                                        trail.getParticleAmount(),
                                        offset[0], offset[1], offset[2],
                                        trail.getParticleSpeed()
                                );
                            }
                        }

                        // Spawn particles from style values if available
                        if (animationValues.getStyleValuesLength() > 0) {
                            for (double[] loc : animationValues.getStyleValues()) {
                                player.spawnParticle(
                                        trail.getTrailEffect(),
                                        target.getLocation().add(loc[0], loc[1], loc[2]),
                                        trail.getParticleAmount(),
                                        offset[0], offset[1], offset[2],
                                        trail.getParticleSpeed()
                                );
                            }
                        }
                    });

            // Advance animation step if equation values are used
            if (animationValues.getEquationValuesLength() > 0) {
                animationValues.addStep();
            }
        }
    }
}
