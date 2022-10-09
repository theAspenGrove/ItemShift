package net.mov51.ItemShift.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import static net.mov51.ItemShift.util.HoldingGold.isHoldingGold;
import static net.mov51.ItemShift.util.dropMaker.makeDrops;

public class itemSpawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockDropItemEvent e) {
        Player p = e.getPlayer();
        if (!e.isCancelled() && p.getGameMode() == GameMode.SURVIVAL) {
            if (isHoldingGold(p)) {
                int level = p.getLevel();
                float progress = p.getExp();
                if(progress > 0.25){
                    progress = progress - 0.25f;
                } else {
                    progress = progress + 0.75f;
                    level = level - 1;
                }
                if(level > 0) {
                    p.setExp(progress);
                    p.setLevel(level);
                    for (Item I : e.getItems()) {
                        makeDrops(p, I);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
}