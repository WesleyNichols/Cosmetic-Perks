package cosmetic.perks.cosmeticperks.GUI;

import org.bukkit.inventory.Inventory;

public class GUI {

    public static Inventory inv;

    public Inventory getInv(){
        return inv;
    }

    private void setInv(Inventory i){
        inv = i;
    }
}
