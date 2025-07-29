package me.wesleynichols.cosmeticperks.animation;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.trails.CustomTrail;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnimationManager {

    private final CosmeticPerks plugin;
    private final Map<UUID, CustomTrail> particleAnimationMap = new ConcurrentHashMap<>();

    public AnimationManager(CosmeticPerks plugin) {
        this.plugin = plugin;
    }

    public Map<UUID, CustomTrail> getParticleAnimationList() {
        return Collections.unmodifiableMap(particleAnimationMap);
    }

    public void clearParticleAnimationList() {
        particleAnimationMap.clear();
    }

    public void addParticleAnimation(UUID entity, CustomTrail trail) {
        particleAnimationMap.put(entity, trail);
    }

    public void removeParticleAnimation(UUID entity) {
        particleAnimationMap.remove(entity);
    }

    public boolean hasActiveAnimation(Player player) {
        return particleAnimationMap.containsKey(player.getUniqueId());
    }

    public void attachParticleAnimation(Player player, UUID id, String key, CustomTrail trail) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key + "-trail");

        if (Objects.equals(data.get(namespacedKey, PersistentDataType.STRING), "NONE")) {
            return;
        }
        if (hasActiveAnimation(player)) {
            removeParticleAnimation(id);
        }
        addParticleAnimation(id, trail);
    }

    public void callAttachParticleAnimation(Player player, String key) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key + "-trail");

        String trailName = data.get(namespacedKey, PersistentDataType.STRING);
        if (Objects.equals(trailName, "NONE")) {
            return;
        }
        CustomTrail trail = plugin.getTrailManager().getTrail(trailName);
        if (trail == null || trail.getAnimation() == null) {
            return;
        }
        attachParticleAnimation(player, player.getUniqueId(), key, trail);
    }
}
