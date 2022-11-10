package net.mov51.ItemShift.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

import static net.mov51.ItemShift.util.ConfigHelper.minimumLevel;
import static net.mov51.ItemShift.util.GiveItem.fillShulker;
import static net.mov51.ItemShift.util.HoldingGold.Shulkers;
import static net.mov51.ItemShift.util.HoldingGold.isHoldingShulker;

public class ItemPickup implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickup(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.getLevel() < minimumLevel){
                return;
            }
            ItemStack offHand = p.getInventory().getItemInOffHand();
            //check if offhand is a shulker
            if(isHoldingShulker(p)){
                //prevent item pickup
                e.setCancelled(true);
                //remove the item from the world
                e.getItem().remove();
                //add item to shulker box using the returned item meta
                offHand.setItemMeta(fillShulker(p, Collections.singletonList(e.getItem())));
            }
    }
    }
}
