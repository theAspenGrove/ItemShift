package net.mov51.ItemShift.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import static net.mov51.ItemShift.listeners.BlockBreak.brokenPlayers;
import static net.mov51.ItemShift.util.configHelper.levelCost;
import static net.mov51.ItemShift.util.configHelper.mendingMultiplier;

public class itemDamage implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageEvent(PlayerItemDamageEvent e) {
    Player p = e.getPlayer();
        if(!brokenPlayers.contains(p)) {
            return;
        }
        if (!(p.getLevel() > 0)) {
            return;
        }
        if(e.getDamage() > 0 && e.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.MENDING)){
            e.setCancelled(true);
            p.giveExp(-(levelCost * mendingMultiplier));
        }else{
            p.giveExp(-levelCost);
        }
        brokenPlayers.remove(p);
    }
}
