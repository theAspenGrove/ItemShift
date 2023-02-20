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
import static net.mov51.ItemShift.util.HoldingGold.*;
import static net.mov51.ItemShift.util.ShiftToLodeStone.*;
import static net.mov51.ItemShift.util.XPHelper.hasEnoughXP;
import static org.bukkit.Sound.ENTITY_ITEM_PICKUP;

public class GiveItem {

    public static boolean handleOffhand(Player p, ItemStack item, Location blockLocation){
        if(isHoldingLodeStoneCompass(p) && p.getLevel() >= lodestoneMinimumLevel){
            shiftItemToLocation(p,blockLocation,item);
            return true;
        }
        if(isShulker(p.getInventory().getItemInOffHand())){
            //get the block meta for the player's offhand assuming it's a shulker box.
            BlockStateMeta meta = (BlockStateMeta) p.getInventory().getItemInOffHand().getItemMeta();
            //cast the block state to a shulker box
            ShulkerBox box = (ShulkerBox) meta.getBlockState();
            //get the item in the first slot of the Shulker Box
            ItemStack i = box.getInventory().getItem(0);
            //if the item in the first slot is not null and is a LodeStone Compass
            if(i != null && isLodeStoneCompass(i)){
                //if the shulker box contains an item of the same type as the item being handled
                if(box.getInventory().contains(item.getType())){
                    shiftItemToLocation(p,i,blockLocation,item);
                    return true;
                }else{
                    return false;
                }
            }
            //only progresses to here if the Shulker Box does not contain a LodeStone Compass
            //if the player has enough XP to fill the Shulker box
            if(hasEnoughXP(p,shulkerFillCost)){
                //do not fill the Shulker with a Shulker
                if(isShulker(item)){
                    return false;
                }
                //add item to shulker box using the returned item meta
                p.getInventory().getItemInOffHand().setItemMeta(fillShulker(p,item));
                //prevent block from dropping items
                return true;
            }
        }
        return false;
    }
    public static void giveItems(Player p, List<Item> items, Location blockLocation){
        for(Item i : items){
            //run the standard give item method for every item in the list
            if(!handleOffhand(p,i.getItemStack(), blockLocation)){
                giveItemToPlayer(p,i.getItemStack());
            }
        }
    }
    public static void giveItemToPlayer(Player p, ItemStack item){
        //give the item and get the leftovers as a list
        HashMap <Integer, ItemStack> leftovers = null;
        ItemStack offhand = p.getInventory().getItemInOffHand();
        if(offhand.asOne().equals(item.asOne()) && offhand.getAmount() < offhand.getMaxStackSize()){
            if(offhand.getAmount() + item.getAmount() < offhand.getMaxStackSize()){
                p.getInventory().getItemInOffHand().setAmount(offhand.getAmount() + item.getAmount());
            }else{
                int leftover = offhand.getAmount() + item.getAmount() - offhand.getMaxStackSize();
                p.getInventory().getItemInOffHand().setAmount(offhand.getMaxStackSize());
                item.setAmount(leftover);
                leftovers = p.getInventory().addItem(item);
            }
        }else{
            leftovers = p.getInventory().addItem(item);
        }
        //if there are leftovers, drop them on the ground
        if(leftovers != null && leftovers.size() > 0){
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
            p.giveExp(-shulkerFillCost);
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
