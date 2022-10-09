package net.mov51.ItemShift.util;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class dropMaker {
    public static void makeDrops(Player p, Item item){
        p.getLocation().getWorld().dropItem(p.getLocation(),item.getItemStack()).setPickupDelay(0);
    }
}
