package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;
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

    public static void clearParticleAnimationList() {
        particleAnimationList.clear();
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

    public static void attachParticleAnimation(Player player, UUID id, String key, CustomTrail trail) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
        if (AnimationManager.hasActiveAnimation(player)) {
            AnimationManager.removeParticleAnimation(id);
        }
        AnimationManager.addParticleAnimation(id, trail);
    }

    public static void callAttachParticleAnimation(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (Objects.equals(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING), "NONE")) {return;}
        CustomTrail trail = TrailManager.getTrail(data.get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING));
        if(trail.getAnimation() == null) {return;}
        attachParticleAnimation(player, player.getUniqueId(), key, trail);
    }
}
