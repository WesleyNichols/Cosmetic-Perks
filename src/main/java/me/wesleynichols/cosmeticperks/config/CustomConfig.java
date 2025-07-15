package me.wesleynichols.cosmeticperks.config;

import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class CustomConfig {

    private final Plugin plugin;
    private final String fileName;
    private File file;
    private FileConfiguration config;

    public CustomConfig(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    public void load() {
        file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists() || file.length() == 0) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();

                try (InputStream in = plugin.getResource(fileName);
                     OutputStream out = new FileOutputStream(file)) {
                    if (in != null) {
                        ByteStreams.copy(in, out);
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "Error copying default resource for " + fileName, e);
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create config file: " + fileName, e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file.getName(), e);
        }
    }
}
