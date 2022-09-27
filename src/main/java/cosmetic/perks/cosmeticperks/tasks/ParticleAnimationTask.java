package cosmetic.perks.cosmeticperks.tasks;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;
import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationTask extends BukkitRunnable {

    public double[] evaluateExpressions(String[] expr, double x) {
        return new double[]{new ExpressionBuilder(expr[0]).variable("x").build().setVariable("x", x).evaluate(),
                new ExpressionBuilder(expr[1]).variable("x").build().setVariable("x", x).evaluate(),
                new ExpressionBuilder(expr[2]).variable("x").build().setVariable("x", x).evaluate()
        };
    }
    double x;

    public void run() {
        if (!CosmeticPerks.getInstance().isEnabled()) { this.cancel(); }

        HashMap<UUID, ParticleAnimations> particleAnimationList = ParticleAnimationManager.getParticleAnimationList();
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

            ParticleAnimations particleAnimations = particleAnimationList.get(entityId);

            x += particleAnimations.getDistanceBetweenParticles();
            Bukkit.broadcast(Component.text(x));
            double[] values = this.evaluateExpressions(particleAnimations.getEquationList(), x);

            World world = entity.getWorld();
            world.getPlayers().stream()
                    .filter(player -> player.getWorld().getUID().equals(world.getUID()))
                    .filter(player -> player.getLocation().distance(entity.getLocation()) <= 40)
                    .forEach(player -> {
                                if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }
                                player.spawnParticle(particleAnimations.getTrailEffect(), entity.getLocation().add(values[0], values[1], values[2]), 0);
                            }
                    );
        }
    }
}
