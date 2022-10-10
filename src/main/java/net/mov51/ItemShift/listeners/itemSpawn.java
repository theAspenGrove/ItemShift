package net.mov51.ItemShift.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import static net.mov51.ItemShift.util.HoldingGold.hasNuggets;
import static net.mov51.ItemShift.util.HoldingGold.isHoldingGold;

public class itemSpawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockDropItemEvent e) {
        Player p = e.getPlayer();
        if (!e.isCancelled() && p.getGameMode() == GameMode.SURVIVAL) {
            if (isHoldingGold(p) || hasNuggets(p, false)) {
                if (p.getLevel() > 0) {
                    for (Item I : e.getItems()) {
                        makeDrops(p, I);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
    public static void makeDrops(Player p, Item item){
        p.getLocation().getWorld().dropItem(p.getLocation(),item.getItemStack()).setPickupDelay(0);
    }
}