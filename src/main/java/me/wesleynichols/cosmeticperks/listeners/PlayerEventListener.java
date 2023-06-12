package me.wesleynichols.cosmeticperks.listeners;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.AnimationManager;
import me.wesleynichols.cosmeticperks.managers.ProjectileTrailManager;
import me.wesleynichols.cosmeticperks.managers.TrailManager;
import me.wesleynichols.cosmeticperks.managers.TrailMethods;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.GameMode;
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

import static me.wesleynichols.cosmeticperks.managers.TrailMethods.removeOrAttachAnimation;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        removeOrAttachAnimation(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(AnimationManager.hasActiveAnimation(event.getPlayer())) {
            AnimationManager.removeParticleAnimation(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
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
        if (!player.hasPermission("cosmeticperks.access") || AdvancedVanishAPI.INSTANCE.isPlayerVanished(player) || player.getGameMode() == GameMode.SPECTATOR) { return; }
        if(event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (player.isGliding()) {
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING), "NONE")) {
                CustomTrail trail = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING));

                TrailMethods.spawnParticle(player, trail);
            }
        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail trailProperties = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING));
            if(trailProperties == null) {throw new NullPointerException("Trail Properties is null!");}
            if(trailProperties.getAnimation() != null) {return;}

            TrailMethods.spawnParticle(player, trailProperties);
        }
    }
}
