package me.wesleynichols.cosmeticperks.config;

import me.wesleynichols.cosmeticperks.trails.CustomTrail;
import me.wesleynichols.cosmeticperks.trails.TrailManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LimitedTrailStorage {

    private final File file;
    private final YamlConfiguration config;

    public LimitedTrailStorage(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "limited_trails.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public Set<String> getPlayerTrails(UUID uuid, String type) {
        return new HashSet<>(config.getStringList(uuid.toString() + "." + type));
    }

    public Map<String, Set<String>> getAllPlayerTrails(UUID uuid) {
        Map<String, Set<String>> result = new HashMap<>();
        if (config.contains(uuid.toString())) {
            for (String key : config.getConfigurationSection(uuid.toString()).getKeys(false)) {
                result.put(key, new HashSet<>(config.getStringList(uuid.toString() + "." + key)));
            }
        }
        return result;
    }

    public void grantTrail(UUID uuid, String type, String trailName) {
        Set<String> trails = getPlayerTrails(uuid, type);
        trails.add(trailName);
        config.set(uuid + "." + type, new ArrayList<>(trails));
        save();
    }

    public void revokeTrail(UUID uuid, String type, String trailName) {
        Set<String> trails = getPlayerTrails(uuid, type);
        trails.remove(trailName);
        config.set(uuid + "." + type, new ArrayList<>(trails));
        save();
    }

    public boolean hasTrail(UUID uuid, String type, String trailName) {
        return getPlayerTrails(uuid, type).contains(trailName);
    }

    public void cleanupLimitedTrails(TrailManager trailManager) {
        // We'll collect players to remove after processing
        Set<String> playersToRemove = new HashSet<>();

        for (String playerUUID : config.getKeys(false)) {
            boolean playerHasAnyTrail = false;

            for (String type : config.getConfigurationSection(playerUUID).getKeys(false)) {
                List<String> trails = config.getStringList(playerUUID + "." + type);
                List<String> filteredTrails = trails.stream()
                        .filter(trailName -> {
                            // Check trail exists and is limited
                            CustomTrail trail = trailManager.getTrail(trailName);
                            return trail != null && trail.isLimitedItem();
                        })
                        .collect(Collectors.toList());

                if (filteredTrails.isEmpty()) {
                    // Remove this type section if empty
                    config.set(playerUUID + "." + type, null);
                } else {
                    playerHasAnyTrail = true;
                    // Save filtered list back
                    config.set(playerUUID + "." + type, filteredTrails);
                }
            }

            if (!playerHasAnyTrail) {
                playersToRemove.add(playerUUID);
            }
        }

        // Remove players with no trails
        for (String uuid : playersToRemove) {
            config.set(uuid, null);
        }

        save(); // Make sure to save changes to file
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
