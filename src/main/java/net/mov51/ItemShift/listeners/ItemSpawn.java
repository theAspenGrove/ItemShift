package net.mov51.ItemShift.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import static net.mov51.ItemShift.util.ConfigHelper.minimumLevel;
import static net.mov51.ItemShift.util.GiveItem.giveItems;
import static net.mov51.ItemShift.util.HoldingGold.*;

public class ItemSpawn implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(BlockDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.getLevel() <= minimumLevel) {
            return;
        }
        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if(e.isCancelled()){
            return;
        }
        if (isHoldingGold(p) || hasNuggets(p, false)) {
            giveItems(p, e.getItems(), e.getBlock().getLocation());
            e.setCancelled(true);
        }
    }
}