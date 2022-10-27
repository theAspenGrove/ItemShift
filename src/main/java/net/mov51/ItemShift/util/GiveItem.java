package net.mov51.ItemShift.util;

import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static net.mov51.ItemShift.util.ConfigHelper.shulkerFillCost;
import static net.mov51.ItemShift.util.HoldingGold.Shulkers;
import static org.bukkit.Sound.ENTITY_ITEM_PICKUP;

public class GiveItem {
    static Random random = new Random();

    private static void giveItem(Player p, ItemStack item, Inventory inv){
        //play sound for the standard give item
        p.playSound(p.getLocation(), ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (random.nextFloat() - random.nextFloat()) * 1.4f + 2.0f);
        //give the item and get the leftovers as a list
        HashMap <Integer, ItemStack> leftovers = inv.addItem(item);
        //increment the player's item pickup statistic
        p.incrementStatistic(Statistic.PICKUP, item.getType());
        //if there are leftovers, drop them on the ground
        if(leftovers.size() > 0){
            for(ItemStack i : leftovers.values()){
                p.getWorld().dropItem(p.getLocation(),i);
            }
        }
    }
    public static void giveItem(Player p, List<Item> items) {
        for(Item i : items){
            //run the standard give item method for every item in the list
            giveItem(p,i.getItemStack(),p.getInventory());
        }
    }

    public static ItemMeta fillShulker(Player p, List<Item> items) {
        //get the block meta for the player's offhand assuming it's a shulker box.
        BlockStateMeta meta = (BlockStateMeta) p.getInventory().getItemInOffHand().getItemMeta();
        //cast the block state to a shulker box
        ShulkerBox box = (ShulkerBox) meta.getBlockState();
        for(Item i : items){
            //if it's a shulker, skip adding it to the shulker box and add it to inventory instead
            if(Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(i.getItemStack().getType())){
                giveItem(p,i.getItemStack(),p.getInventory());
                continue;
            }
            //play the standard pickup sound
            p.playSound(p.getLocation(), ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (random.nextFloat() - random.nextFloat()) * 1.4f + 2.0f);
            //add items to the shulker box then get the leftovers from the shulker box and drop them on the ground
            HashMap <Integer, ItemStack> leftovers = box.getInventory().addItem(i.getItemStack());
            if(leftovers.size() > 0) {
                for (ItemStack j : leftovers.values()) {
                    giveItem(p,j,p.getInventory());
                }
            }
            //increment the player's item pickup statistic
            p.incrementStatistic(Statistic.PICKUP, i.getItemStack().getType());
            //remove the shulkerFillCost from the player's level
            p.giveExp(-shulkerFillCost);
        }
        //set the block state to the shulker box
        meta.setBlockState(box);
        //return the block meta for the calling method to set on the actual player object
        return meta;
    }
}
