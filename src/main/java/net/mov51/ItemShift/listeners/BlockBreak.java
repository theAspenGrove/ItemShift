package net.mov51.ItemShift.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;

import static net.mov51.ItemShift.util.HoldingGold.*;

public class BlockBreak implements Listener {
    public static HashSet<Player> brokenPlayers = new HashSet<>();
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(e.isDropItems() && (isHoldingGold(p) || hasNuggets(p, true))){
            brokenPlayers.add(e.getPlayer());
        }
    }
}
