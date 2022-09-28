package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ElytraTrails;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;


public class OnPlayerMove implements Listener {

    @EventHandler
    public void onPlayerTrailMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("group.donator") || AdvancedVanishAPI.INSTANCE.isPlayerVanished(player)) { return; }

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (player.isGliding() && !Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail.TrailProperties trailProperties = ElytraTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING)).getProperties();

            World world = player.getWorld();
            world.getPlayers().stream()
                    .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                    .filter(e -> e.getLocation().distance(player.getLocation()) <= 40)
                    .forEach(e -> e.spawnParticle(trailProperties.getTrailEffect(), player.getLocation(), trailProperties.getParticleAmount(),
                            trailProperties.getXOffSet(), trailProperties.getYOffSet(), trailProperties.getZOffSet(), trailProperties.getParticleSpeed())
                    );
        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail.TrailProperties trailProperties = PlayerTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING)).getProperties();

            World world = player.getWorld();
            world.getPlayers().stream()
                    .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                    .filter(e -> e.getLocation().distance(player.getLocation()) <= 40)
                    .forEach(e -> e.spawnParticle(trailProperties.getTrailEffect(), player.getLocation(), trailProperties.getParticleAmount(),
                            trailProperties.getXOffSet(), trailProperties.getYOffSet(), trailProperties.getZOffSet(), trailProperties.getParticleSpeed())
                    );
        }
    }
}
