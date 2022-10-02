package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AnimationManager {

    private static final HashMap<UUID, CustomTrail> particleAnimationList = new HashMap<>();

    public static HashMap<UUID, CustomTrail> getParticleAnimationList() {
        return new HashMap<>(particleAnimationList);
    }

    public static void addParticleAnimation(UUID entity, CustomTrail e) {
        particleAnimationList.put(entity, e);
    }

    public static void removeParticleAnimation(UUID entity) {
        particleAnimationList.remove(entity);
    }

    public static boolean hasActiveAnimation(Player player) {
        return particleAnimationList.containsKey(player.getUniqueId());
    }

    public static <T extends CustomTrail> void attachParticleAnimation(Player player, UUID id, String key, T e) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
        if (AnimationManager.hasActiveAnimation(player)) {
            AnimationManager.removeParticleAnimation(id);}
        // T particleAnimations = e.(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING));
        AnimationManager.addParticleAnimation(id, e);
        player.sendMessage(Component.text("Animations trail attached to " + player.getName()));
    }

    public static void callAttachParticleAnimation(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
        attachParticleAnimation(player, player.getUniqueId(), key, PlayerTrails.valueOf((data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING))));
    }
}
