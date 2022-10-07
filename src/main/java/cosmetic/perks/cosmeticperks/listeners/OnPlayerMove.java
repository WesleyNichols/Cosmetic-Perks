package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.managers.TrailManager;
import cosmetic.perks.cosmeticperks.managers.TrailMethods;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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
            CustomTrail trail = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING));

            TrailMethods.spawnParticle(player, trail);

        } else if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING), "NONE")) {
            CustomTrail trailProperties = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING));
            if(trailProperties == null) {throw new NullPointerException("Trail Properties is null!");}
            // TODO particle effect is not being parsed correctly Bukkit.broadcast(Component.text(trailProperties.getTrailEffect().toString()));
            if(trailProperties.getAnimation() != null) {return;}

            TrailMethods.spawnParticle(player, trailProperties);
        }
    }

}
