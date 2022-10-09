package net.mov51.ItemShift.listeners;

import net.mov51.ItemShift.util.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import static net.mov51.ItemShift.util.HoldingGold.isHoldingGold;

public class itemDamage implements Listener {

    float levelsToRemove = 0.0556f;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        if(isHoldingGold(p)) {
            Level levelProgress = new Level(p.getLevel(), p.getExp());
            if (levelProgress.getLevel() > 0) {
                if (levelProgress.getProgress() > levelsToRemove) {
                    levelProgress.setProgress(levelProgress.getProgress() - levelsToRemove);
                } else {
                    levelProgress.setProgress(levelProgress.getProgress() + (1.0f - levelsToRemove));
                    if(levelProgress.getLevel() < 1){
                        return;
                    }
                    levelProgress.setLevel(levelProgress.getLevel() - 1);
                }
                p.setExp(levelProgress.getProgress());
                p.setLevel(levelProgress.getLevel());
                //cancel item damage
                e.setCancelled(true);
                }
            }
        }
    }
