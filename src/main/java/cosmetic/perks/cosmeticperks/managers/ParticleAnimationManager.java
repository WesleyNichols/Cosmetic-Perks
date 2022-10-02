package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;
import org.bukkit.entity.Player;

import java.util.*;

public class ParticleAnimationManager {

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
}
