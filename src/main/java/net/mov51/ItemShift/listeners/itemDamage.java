package net.mov51.ItemShift.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import static net.mov51.ItemShift.util.HoldingGold.isHoldingGold;

public class itemDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        if(isHoldingGold(p)){
            int level = p.getLevel();
            float progress = p.getExp();
            if(progress > 0.25){
                progress = progress - 0.25f;
            } else {
                progress = progress + 0.75f;
                level = level - 1;
            }
            //set XP level and progress
            if(level > 0){
                p.setExp(progress);
                p.setLevel(level);
                //cancel item damage
                e.setCancelled(true);
            }

        }
    }
}
