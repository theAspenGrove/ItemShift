package net.mov51.ItemShift.listeners;

import net.mov51.ItemShift.command.util.MessageBuilder;
import net.mov51.ItemShift.command.util.arrayManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class itemSpawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockDropItemEvent e) {
        Player player = e.getPlayer();
        if (!e.isCancelled() && player.getGameMode() == GameMode.SURVIVAL) {
            if (arrayManager.INSTANCE.autoPickupList.contains(player.getUniqueId())) {
                for(Item I : e.getItems()){
                    HashMap<java.lang.Integer, org.bukkit.inventory.ItemStack> drops = player.getInventory().addItem(I.getItemStack());
                    if(drops.size() == 0){
                        float pitch = 0 + new Random().nextFloat() * (2f - 0f);
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1f, pitch);
                    }
                    for (ItemStack Item : drops.values()){
                        player.getWorld().dropItemNaturally(e.getBlock().getLocation(), Item);
                        MessageBuilder.sendChatToPlayer("Your inventory is full!", player);
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}