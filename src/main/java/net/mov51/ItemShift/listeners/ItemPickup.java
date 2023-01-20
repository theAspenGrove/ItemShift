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
        if(handleOffhand(p, e.getItem().getItemStack(), e.getItem().getLocation())){
            e.getItem().remove();
            e.setCancelled(true);
        }
    }
}
