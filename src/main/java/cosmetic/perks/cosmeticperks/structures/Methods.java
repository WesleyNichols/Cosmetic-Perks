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

    public void enablePlayerTrail(PlayerTrails e, Player player, Boolean selected, ChestGui gui) {
        if(selected) {
            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, "NONE");
            player.sendMessage(Component.text("You disabled the " + e.getEffectName() + " player trail."));
        } else{
            player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "player-trail"), PersistentDataType.STRING, e.name().toUpperCase());
            player.sendMessage(Component.text("You enabled the " + e.getEffectName() + " player trail."));
        }
        gui.update();
    }

    public void enableProjectileTrail(PlayerTrails e, Player player) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "projectile-trail"), PersistentDataType.STRING, e.name().toUpperCase());
        player.sendMessage(Component.text("You enabled the " + e.getEffectName() + " projectile trail."));
    }

    public void enableElytraTrail(PlayerTrails e, Player player) {
        player.getPersistentDataContainer().set(new NamespacedKey(CosmeticPerks.getInstance(), "elytra-trail"), PersistentDataType.STRING, e.name().toUpperCase());
        player.sendMessage(Component.text("You enabled the " + e.getEffectName() + " elytra trail."));
    }
}
