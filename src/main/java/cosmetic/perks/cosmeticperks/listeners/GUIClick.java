package cosmetic.perks.cosmeticperks.listeners;

import cosmetic.perks.cosmeticperks.CosmeticPerks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIClick implements Listener {

    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        if(!event.getView().title().equals(Component.text("Particles").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD))){return;}

        ItemStack clicked = event.getCurrentItem();
        if(clicked == null){return;}

        HumanEntity entity = event.getWhoClicked();
        if(entity instanceof Player){
            Player player = (Player)entity;
            event.setCancelled(true);
            if(clicked.getType() == Material.BEE_NEST){
                player.closeInventory();
                player.sendMessage(Component.text("Working").color(NamedTextColor.GREEN));
            }
        }
    }
}
