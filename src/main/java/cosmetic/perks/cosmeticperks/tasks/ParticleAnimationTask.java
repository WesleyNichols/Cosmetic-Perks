package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;
import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.structures.Animations;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationTask<T extends CustomTrail> extends BukkitRunnable {

    public double[][] evaluateExpressions(String[] expr, double x) {
        double[][] finalList = new double[expr.length/3][3];
        int exprNumber = 0;
        for (int i=1;i<=expr.length/3;i++) {
            for(int j=0; j<3; j++) {
                finalList[i-1][j] = new ExpressionBuilder(expr[exprNumber]).variable("x").build().setVariable("x", x).evaluate();
                exprNumber++;
            }
        }
        return finalList;
    }

    public void run() {
        if (!CosmeticPerks.getInstance().isEnabled()) { this.cancel(); }

        HashMap<UUID, CustomTrail> particleAnimationList = ParticleAnimationManager.getParticleAnimationList();
        for (UUID entityId : particleAnimationList.keySet()) {
            Entity entity = Bukkit.getEntity(entityId);

            if (entity == null) {
                ProjectileTrailManager.removeProjTrail(entityId);
                continue;
            }

            if (entity.isOnGround() || entity.isDead()) {
                ProjectileTrailManager.removeProjTrail(entityId);
                continue;
            }

            CustomTrail.TrailProperties particleProperties = particleAnimationList.get(entityId).getProperties();
            Animations particleAnimations = particleProperties.getAnimation();
            //Bukkit.broadcast(Component.text(particleAnimations.getCurrentDistance()));

            if(particleAnimations.getCurrentDistance() < particleAnimations.getMaxDistance()) {
                particleAnimations.addToCurrentDistance();
            } else {
                particleAnimations.resetCurrentDistance();
            }

            double[][] values = this.evaluateExpressions(particleAnimations.getEquationList(), particleAnimations.getCurrentDistance());

            World world = entity.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(entity.getLocation()) <= 40)
                    .forEach(player -> {
                                if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }
                                for(double[] loc: values) {
                                    player.spawnParticle(particleProperties.getTrailEffect(), entity.getLocation().add(loc[0], loc[1], loc[2]), particleProperties.getParticleAmount(),
                                            particleProperties.getXOffSet(), particleProperties.getYOffSet(), particleProperties.getZOffSet(), particleProperties.getParticleSpeed());
                                }
                            }
                    );
        }
    }
}
