package net.mov51.ItemShift.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import static net.mov51.ItemShift.util.HoldingGold.isItemGold;
import static net.mov51.ItemShift.util.configHelper.levelCost;

public class itemDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
            if(isItemGold(e.getItem())){
                if (p.getLevel() > 0) {
                    p.giveExp(-levelCost);
                    //cancel item damage
                    e.setCancelled(true);
                }
            }
        }
    }
