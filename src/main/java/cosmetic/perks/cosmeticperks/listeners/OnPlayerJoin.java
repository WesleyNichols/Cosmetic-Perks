package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;
import cosmetic.perks.cosmeticperks.enums.ProjectileTrails;
import cosmetic.perks.cosmeticperks.managers.ParticleAnimationManager;
import cosmetic.perks.cosmeticperks.managers.ProjectileTrailManager;
import cosmetic.perks.cosmeticperks.structures.Methods;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;


public class OnPlayerJoin extends Methods implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!hasActiveTrail(player, "player")) { removeActiveTrail(player, "player"); }

        if (!hasActiveTrail(player, "projectile")) { removeActiveTrail(player, "projectile"); }

        if (!hasActiveTrail(player, "elytra")) { removeActiveTrail(player, "elytra"); }

        if (!hasActiveTrail(player, "player-animation")) { removeActiveTrail(player, "player-animation"); }

        //Attach animation trail to player
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-animation-trail"), PersistentDataType.STRING), "NONE")) {
            ParticleAnimations particleAnimations = ParticleAnimations.valueOf(data.get(new NamespacedKey(CosmeticPerks.getInstance(), "player-animation-trail"), PersistentDataType.STRING));
            ParticleAnimationManager.addParticleAnimation(event.getPlayer().getUniqueId(), particleAnimations);
            player.sendMessage(Component.text("Animation trail attached to " + player.getName()));
        }
    }
}

