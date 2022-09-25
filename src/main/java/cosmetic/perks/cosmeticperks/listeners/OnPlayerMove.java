package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ElytraTrails;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import org.bukkit.Bukkit;
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
        if(!player.hasPermission("group.donator")){return;}

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (player.isGliding() && !Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING), "NONE")) {
            ElytraTrails elytraTrails = ElytraTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING));

            World world = player.getWorld();
            world.getPlayers().stream()
                    .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                    .filter(e -> e.getLocation().distance(player.getLocation()) <= 40)
                    .forEach(e -> {
                                e.spawnParticle(elytraTrails.getTrailEffect(), player.getLocation(), elytraTrails.getParticleAmount(),
                                        elytraTrails.getXOffSet(), elytraTrails.getYOffSet(), elytraTrails.getZOffSet(), elytraTrails.getParticleSpeed());
                            }
                    );
        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            PlayerTrails playerTrails = PlayerTrails.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING));

            World world = player.getWorld();
            world.getPlayers().stream()
                    .filter(e -> e.getWorld().getUID().equals(world.getUID()))
                    .filter(e -> e.getLocation().distance(player.getLocation()) <= 40)
                    .forEach(e -> {
                                e.spawnParticle(playerTrails.getTrailEffect(), player.getLocation(), playerTrails.getParticleAmount(),
                                        playerTrails.getXOffSet(), playerTrails.getYOffSet(), playerTrails.getZOffSet(), playerTrails.getParticleSpeed());
                            }
                    );
        }
    }
}
