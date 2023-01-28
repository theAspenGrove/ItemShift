package net.mov51.ItemShift.listeners;

import net.mov51.ItemShift.util.GiveItem;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import static net.mov51.ItemShift.util.ConfigHelper.minimumLevel;
import static net.mov51.ItemShift.util.GiveItem.handleOffhand;
import static net.mov51.ItemShift.util.HoldingGold.isWearingGoldArmor;

public class ItemPickup implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickup(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if (p.getLevel() <= minimumLevel) {
            return;
        }
        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if(e.isCancelled()){
            return;
        }
        if(isWearingGoldArmor(p)){
            if(handleOffhand(p, e.getItem().getItemStack(), e.getItem().getLocation())){
                //Always cancel the event if handling it.
                //This prevents the items from duplicating!!
                //This in particular wil present as the player picking up the item infinitely.
                e.getItem().remove();
                //Always cancel the event if handling it.
                //This prevents the items from duplicating!!
                e.setCancelled(true);
            }
        }
    }
}
