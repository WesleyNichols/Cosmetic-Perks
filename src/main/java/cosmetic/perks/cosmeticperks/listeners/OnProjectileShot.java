package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class OnProjectileShot implements Listener {

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "arrow-trail"), PersistentDataType.STRING), "NONE")) {
                ProjectileTrails projectileTrails = ProjectileTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "arrow-trail"), PersistentDataType.STRING));
                ProjectileTrailManager.addProjTrail(event.getEntity().getUniqueId(), projectileTrails);
            }
        }
    }

}