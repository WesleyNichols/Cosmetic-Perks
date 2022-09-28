package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.structures.Methods;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class OnProjectileShot extends Methods implements Listener {

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            if (AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }

            if (event.getEntity() instanceof FishHook || event.getEntity() instanceof Trident) { return; }

            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING), "NONE")) {
                ProjectileTrails projectileTrails = ProjectileTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING));
                ProjectileTrailManager.addProjTrail(event.getEntity().getUniqueId(), projectileTrails);
            }
            attachParticleAnimation(player, event.getEntity().getUniqueId(), "projectile-animation");
        }
    }

}