package net.mov51.ItemShift.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static net.mov51.ItemShift.util.GiveItem.fillShulker;
import static net.mov51.ItemShift.util.HoldingGold.Shulkers;

public class ItemPickup implements Listener {
    Random random = new Random();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.getLevel() > 0){
                ItemStack offHand = p.getInventory().getItemInOffHand();
                //check if offhand is a shulker
                if(Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(offHand.getType())){
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
}
