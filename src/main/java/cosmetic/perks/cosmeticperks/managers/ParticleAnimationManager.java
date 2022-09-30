package cosmetic.perks.cosmeticperks.managers;

import cosmetic.perks.cosmeticperks.enums.ParticleAnimations;
import cosmetic.perks.cosmeticperks.structures.CustomTrail;

import java.util.HashMap;
import java.util.UUID;

public class ParticleAnimationManager<T> {

    private static HashMap<UUID, Class> particleAnimationList = new HashMap<>();

    public static HashMap<UUID, Class> getParticleAnimationList() {
        return new HashMap<>(particleAnimationList);
    }

    public void addParticleAnimation(UUID entity, T e) {
        particleAnimationList.put(entity, e);
    }

    public static void removeParticleAnimation(UUID entity) {
        particleAnimationList.remove(entity);
    }

}
