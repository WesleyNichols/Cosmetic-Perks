package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationManager {

    private static HashMap<UUID, ParticleAnimations> particleAnimationList = new HashMap<>();

    public static HashMap<UUID, ParticleAnimations> getParticleAnimationList() {
        return new HashMap<>(particleAnimationList);
    }

    public static void addParticleAnimation(UUID entity, ParticleAnimations trailMap) {
        particleAnimationList.put(entity, trailMap);
    }

    public static void removeParticleAnimation(UUID entity) {
        particleAnimationList.remove(entity);
    }

}
