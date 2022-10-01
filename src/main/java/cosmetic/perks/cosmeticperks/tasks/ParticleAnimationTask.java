package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import cosmetic.perks.cosmeticperks.structures.Animations;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import cosmetic.perks.cosmeticperks.structures.EquationValues;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationTask extends BukkitRunnable {

    private static double[][] evaluateExpressions(String[] expr, double x) {
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
                ParticleAnimationManager.removeParticleAnimation(entityId);
                continue;
            }

            if (entity.isDead()) {
                continue;
            }

            CustomTrail.TrailProperties particleProperties = particleAnimationList.get(entityId).getProperties();
            EquationValues particleAnimationValues = particleProperties.getAnimation();
            int step = particleAnimationValues.getCurrentStep();


            World world = entity.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(entity.getLocation()) <= 40)
                    .forEach(player -> {
                                if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }
                                for(double[][] loc: particleAnimationValues.getEquationValues()) {
                                    player.spawnParticle(particleProperties.getTrailEffect(), entity.getLocation().add(loc[step][0], loc[step][1], loc[step][2]), particleProperties.getParticleAmount(),
                                            particleProperties.getXOffSet(), particleProperties.getYOffSet(), particleProperties.getZOffSet(), particleProperties.getParticleSpeed());
                                }
                            }
                    );
        particleAnimationValues.addStep();
        }
    }
}
