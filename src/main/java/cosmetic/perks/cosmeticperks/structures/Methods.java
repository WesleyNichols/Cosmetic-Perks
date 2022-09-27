package cosmetic.perks.cosmeticperks.structures;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.sun.tools.javac.code.Attribute;
import cosmetic.perks.cosmeticperks.CosmeticPerks;
import cosmetic.perks.cosmeticperks.enums.PlayerTrails;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class Methods {
    public Enchantment[] enchantArray(Enchantment... e) {
        return e;
    }

    public int[] levelArray(int... i) {
        return i;
    }

    public void setActiveTrail(PlayerTrails e, Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, e.name().toUpperCase());
        player.sendMessage(Component.text("Enabled the " + e.getEffectName() + " " + key + " trail!"));
    }

    public void removeActiveTrail(Player player, String key) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING, "NONE");
        player.sendMessage(Component.text("Disabled your " + key + " trail!"));
    }

    public String getActiveTrail(Player player, String key) {
        return player.getPersistentDataContainer().get(new NamespacedKey(CosmeticPerks.getInstance(), key + "-trail"), PersistentDataType.STRING);
    }
}
