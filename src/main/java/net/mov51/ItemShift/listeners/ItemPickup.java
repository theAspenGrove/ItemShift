package net.mov51.ItemShift.listeners;

import org.bukkit.SoundCategory;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static net.mov51.ItemShift.util.ConfigHelper.shulkerFillCost;
import static net.mov51.ItemShift.util.HoldingGold.Shulkers;
import static org.bukkit.Sound.ENTITY_ITEM_PICKUP;

public class ItemPickup implements Listener {
    Random random = new Random();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.getLevel() > 0){
                ItemStack offHand = p.getInventory().getItemInOffHand();
                if(Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(offHand.getType())){
                    p.playSound(p.getLocation(), ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 1.4f + 2.0f);
                    //prevent item pickup
                    e.setCancelled(true);
                    //get shulker box meta data
                    BlockStateMeta meta = (BlockStateMeta) offHand.getItemMeta();
                    ShulkerBox box = (ShulkerBox) meta.getBlockState();
                    //add item to meta and return with leftover items
                    HashMap<Integer,ItemStack> leftovers = box.getInventory().addItem(e.getItem().getItemStack());
                    //remove the item from the world
                    e.getItem().remove();
                    //if there are leftover items, place them in the player's inventory
                    if(leftovers.size() > 0){
                        for(ItemStack i : leftovers.values()){
                            //add the item to the player's inventory
                            HashMap<Integer,ItemStack> playerLeftovers = p.getInventory().addItem(i);
                            //check that they had space for it
                            if(playerLeftovers.size() > 0){
                                for(ItemStack j : playerLeftovers.values()){
                                    //if not, drop it on the ground
                                    p.getWorld().dropItem(p.getLocation(),j);
                                }
                            }
                        }
                    }
                    meta.setBlockState(box);
                    offHand.setItemMeta(meta);
                    p.giveExp(-shulkerFillCost);
                }
            }
        }
    }
}
