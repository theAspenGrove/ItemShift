package net.mov51.ItemShift.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import static net.mov51.ItemShift.util.ConfigHelper.minimumLevel;
import static net.mov51.ItemShift.util.GiveItem.fillShulker;
import static net.mov51.ItemShift.util.GiveItem.giveItem;
import static net.mov51.ItemShift.util.HoldingGold.*;

public class ItemSpawn implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(BlockDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.getLevel() < minimumLevel) {
            return;
        }
        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if(e.isCancelled()){
            return;
        }
        if (isHoldingGold(p) || hasNuggets(p, false)) {
            ItemStack offHand = p.getInventory().getItemInOffHand();
            //check if offhand is a shulker
            if(isHoldingShulker(p)){
                //add item to shulker box using the returned item meta
                offHand.setItemMeta(fillShulker(p,e.getItems()));
                //prevent block from dropping items
                e.setCancelled(true);
                //exit event
                return;
            }
            //give items that the block has dropped
            giveItem(p, e.getItems());
            //prevent block from dropping items
            e.setCancelled(true);
        }
    }
}