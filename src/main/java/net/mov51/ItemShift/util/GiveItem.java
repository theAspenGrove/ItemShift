package net.mov51.ItemShift.util;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static net.mov51.ItemShift.util.ConfigHelper.lodestoneMinimumLevel;
import static net.mov51.ItemShift.util.ConfigHelper.shulkerFillCost;
import static net.mov51.ItemShift.util.HoldingGold.Shulkers;
import static net.mov51.ItemShift.util.HoldingGold.isHoldingShulker;
import static net.mov51.ItemShift.util.ShiftToLodeStone.*;
import static net.mov51.ItemShift.util.XPHelper.hasEnoughXP;
import static org.bukkit.Sound.ENTITY_ITEM_PICKUP;

public class GiveItem {

    public static void giveItem(Player p, ItemStack item, Location blockLocation){
        if(isHoldingLodeStoneCompass(p) && p.getLevel() >= lodestoneMinimumLevel){
            shiftItemToLocation(p,blockLocation,item);
            return;
        }
        if(isHoldingShulker(p) && hasEnoughXP(p,shulkerFillCost)){
            //add item to shulker box using the returned item meta
            p.getInventory().getItemInOffHand().setItemMeta(fillShulker(p,item));
            //prevent block from dropping items
            return;
        }
        giveItemToPlayer(p,item);
    }
    public static void giveItem(Player p, List<Item> items, Location blockLocation){
        for(Item i : items){
            //run the standard give item method for every item in the list
            giveItem(p,i.getItemStack(), blockLocation);
        }
    }

    public static void giveItemToPlayer(Player p, ItemStack item){
        //give the item and get the leftovers as a list
        HashMap <Integer, ItemStack> leftovers = p.getInventory().addItem(item);
        //if there are leftovers, drop them on the ground
        if(leftovers.size() > 0){
            for(ItemStack i : leftovers.values()){
                p.getWorld().dropItem(p.getLocation(),i);
            }
        }else{
            playPickupSound(p);
            incrementPickupStat(p,item);
        }
    }

    public static ItemMeta fillShulker(Player p, ItemStack item) {
        //get the block meta for the player's offhand assuming it's a shulker box.
        BlockStateMeta meta = (BlockStateMeta) p.getInventory().getItemInOffHand().getItemMeta();
        //cast the block state to a shulker box
        ShulkerBox box = (ShulkerBox) meta.getBlockState();
        //loop through every item one by one
        if(Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(item.getType())){
            giveItemToPlayer(p,item);
        }
        //add items to the shulker box then get the leftovers from the shulker box and drop them on the ground
        HashMap <Integer, ItemStack> leftovers = box.getInventory().addItem(item);
        if(leftovers.size() > 0) {
            for (ItemStack j : leftovers.values()) {
                giveItemToPlayer(p,j);
            }
        }else {
            //if there are no leftovers, play the pickup sound and increment the pickup stat
            playPickupSound(p);
            incrementPickupStat(p,item);
            //remove the shulkerFillCost from the player's level
            p.giveExp(-shulkerFillCost);p.giveExp(-shulkerFillCost);
        }
        //set the block state to the shulker box
        meta.setBlockState(box);
        //return the block meta for the calling method to set on the actual player object
        return meta;
    }

    public static void playPickupSound(Player p){
        Random random = new Random();
        p.playSound(p.getLocation(), ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (random.nextFloat() - random.nextFloat()) * 1.4f + 2.0f);
    }

    public static void incrementPickupStat(Player p, ItemStack item){
        p.incrementStatistic(Statistic.PICKUP, item.getType());
    }
}
