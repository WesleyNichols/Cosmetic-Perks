package me.wesleynichols.cosmeticperks.config;

import com.google.common.io.ByteStreams;
import me.wesleynichols.cosmeticperks.CosmeticPerks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class CustomConfig {
    public static File file;
    private static FileConfiguration customFile;

    //  Find or generate a custom config
    public static void load(String FilePath) {
        file = new File(CosmeticPerks.getInstance().getDataFolder(), FilePath);

        if (!file.exists() || file.length() == 0) {
            try {
                file.createNewFile();
                try (InputStream in = CosmeticPerks.getInstance().getResource(FilePath);
                     OutputStream out = new FileOutputStream(file)) {
                    ByteStreams.copy(in, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not create config for " + FilePath, e);
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + customFile.getName(), e);
        }
    }

}
