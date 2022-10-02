package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import cosmetic.perks.cosmeticperks.structures.AnimationValues;
import cosmetic.perks.cosmeticperks.util.Particles;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.Bukkit;
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
        for (UUID entityId : particleAnimationList.keySet()) {
            Entity entity = Bukkit.getEntity(entityId);
            assert entity != null;

            if (entity instanceof Player && entity.isDead()) {
                return;
            }

            CustomTrail.TrailProperties particleProperties = particleAnimationList.get(entityId).getProperties();
            AnimationValues particleAnimationValues = particleProperties.getAnimation();
            int step = particleAnimationValues.getCurrentStep();


            World world = entity.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(entity.getLocation()) <= 40)
                    .forEach(player -> {
                                if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }
                                if(particleAnimationValues.getEquationValuesLength() != 0) {
                                    for(Particles equation: particleAnimationValues.getEquationValues()) {
                                        double[][] loc = equation.getParticleValues();
                                        player.spawnParticle(equation.getParticleEffect(), entity.getLocation().add(loc[step][0], loc[step][1], loc[step][2]), particleProperties.getParticleAmount(),
                                                particleProperties.getXOffSet(), particleProperties.getYOffSet(), particleProperties.getZOffSet(), particleProperties.getParticleSpeed());
                                    }
                                    particleAnimationValues.addStep();
                                }
                                if(particleAnimationValues.getStyleValuesLength() != 0) {
                                    for(Particles style: particleAnimationValues.getStyleValues()) {
                                        for(double[] loc: style.getParticleValues()) {
                                            player.spawnParticle(style.getParticleEffect(), entity.getLocation().add(loc[0], loc[1], loc[2]), particleProperties.getParticleAmount(),
                                                    particleProperties.getXOffSet(), particleProperties.getYOffSet(), particleProperties.getZOffSet(), particleProperties.getParticleSpeed());
                                        }
                                    }
                                }
                            }
                    );
        }
    }
}
