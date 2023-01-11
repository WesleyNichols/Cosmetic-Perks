package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.AnimationManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import cosmetic.perks.cosmeticperks.managers.TrailMethods;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

import static cosmetic.perks.cosmeticperks.managers.TrailMethods.removeOrAttachAnimation;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        removeOrAttachAnimation(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(AnimationManager.hasActiveAnimation(event.getPlayer())) {
            AnimationManager.removeParticleAnimation(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }

            if (event.getEntity() instanceof FishHook || event.getEntity() instanceof Trident) { return; }

            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING), "NONE")) {
                CustomTrail projectileTrails = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING));
                ProjectileTrailManager.addProjTrail(event.getEntity().getUniqueId(), projectileTrails);
            }
        }
    }

    @EventHandler
    public void onPlayerTrailMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("group.donator") || AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }
        if(event.getTo().distanceSquared(event.getFrom()) == 0) return;

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (player.isGliding() && !Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail trail = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING));

            TrailMethods.spawnParticle(player, trail);

        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail trailProperties = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING));
            if(trailProperties == null) {throw new NullPointerException("Trail Properties is null!");}
            if(trailProperties.getAnimation() != null) {return;}

            TrailMethods.spawnParticle(player, trailProperties);
        }
    }
}
