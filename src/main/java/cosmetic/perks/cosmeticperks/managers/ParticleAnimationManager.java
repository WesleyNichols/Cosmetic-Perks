package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.structures.CustomTrail;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationManager {

    private static HashMap<UUID, CustomTrail> particleAnimationList = new HashMap<>();

    public static HashMap<UUID, CustomTrail> getParticleAnimationList() {
        return new HashMap<>(particleAnimationList);
    }

    public static void addParticleAnimation(UUID entity, CustomTrail e) {
        particleAnimationList.put(entity, e);
    }

    public static void removeParticleAnimation(UUID entity) {
        particleAnimationList.remove(entity);
    }
}
