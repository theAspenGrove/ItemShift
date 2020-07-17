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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class itemSpawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockDropItemEvent e) {
        Player player = e.getPlayer();
        if (!e.isCancelled() && player.getGameMode() == GameMode.SURVIVAL) {
            if (arrayManager.INSTANCE.autoPickupList.contains(player.getUniqueId())) {
                for(Item I : e.getItems()){
                    HashMap<java.lang.Integer, org.bukkit.inventory.ItemStack> drops = player.getInventory().addItem(I.getItemStack());
                    for (ItemStack Item : drops.values()){
                        player.getWorld().dropItemNaturally(e.getBlock().getLocation(), Item);
                        MessageBuilder.sendChatToPlayer("Your inventory is full!", player);
                        Location Local = player.getLocation();
                        player.playSound(Local, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}