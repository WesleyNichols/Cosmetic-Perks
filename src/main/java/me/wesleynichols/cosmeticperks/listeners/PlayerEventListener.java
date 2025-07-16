package me.wesleynichols.cosmeticperks.listeners;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.structures.CustomTrail;
import me.wesleynichols.cosmeticperks.util.TrailUtils;
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

import static me.wesleynichols.cosmeticperks.util.TrailUtils.removeOrAttachAnimation;

public class PlayerEventListener implements Listener {

    private final CosmeticPerks plugin = CosmeticPerks.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        removeOrAttachAnimation(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(plugin.getAnimationManager().hasActiveAnimation(event.getPlayer())) {
            plugin.getAnimationManager().removeParticleAnimation(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            if (player.hasMetadata("vanished")) { return; }
            if (event.getEntity() instanceof FishHook || event.getEntity() instanceof Trident) { return; }

            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING), "NONE")) {
                CustomTrail projectileTrails = plugin.getTrailManager().getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING));
                plugin.getProjectileTrailManager().addProjTrail(event.getEntity().getUniqueId(), projectileTrails);
            }
        }
    }

    @EventHandler
    public void onPlayerTrailMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("cosmeticperks.access") || player.hasMetadata("vanished") || player.getGameMode() == GameMode.SPECTATOR) { return; }
        if(event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (player.isGliding()) {
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING), "NONE")) {
                CustomTrail trail = plugin.getTrailManager().getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING));

                TrailUtils.spawnParticle(player, trail);
            }
        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail trailProperties = plugin.getTrailManager().getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING));
            if(trailProperties == null) {throw new NullPointerException("Trail Properties is null!");}
            if(trailProperties.getAnimation() != null) {return;}

            TrailUtils.spawnParticle(player, trailProperties);
        }
    }
}
