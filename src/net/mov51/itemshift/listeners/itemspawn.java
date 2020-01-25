package net.mov51.itemshift.listeners;

import net.mov51.itemshift.command.util.MessageBuilder;
import net.mov51.itemshift.command.util.arrayManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class itemspawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e){
        if(e.isDropItems()  && !e.isCancelled() && e.getPlayer().getGameMode()== GameMode.SURVIVAL){
            Player player = e.getPlayer();
            if(arrayManager.INSTANCE.autoPickupList.contains(player.getUniqueId())) {
                ItemStack mainhand = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
                e.getBlock().getDrops(mainhand).forEach(item -> {
                    e.setDropItems(false);
                    if (!player.getInventory().addItem(item).isEmpty()) {
                        MessageBuilder.sendBarToPlayer("your inventory is full!", player);
                    }
                });
            }
        }
    }
}
