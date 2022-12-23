package net.mov51.ItemShift.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.HashMap;
import java.util.List;

import static net.mov51.ItemShift.util.ConfigHelper.lodestoneFillCost;
import static net.mov51.ItemShift.util.XPHelper.getTotalExpPoints;

public class ShiftToLodeStone {
    public static boolean isHoldingLodeStoneCompass(Player p){
        return isLodeStoneCompass(p.getInventory().getItemInOffHand());
    }
    private static boolean isLodeStoneCompass(ItemStack item){
        return item.getType().equals(Material.COMPASS) && item.getItemMeta() != null && ((CompassMeta) item.getItemMeta()).hasLodestone();
    }
    public static void sendToLodeStone(Player p, List<Item> items, Location blockLocation){
        if(isHoldingLodeStoneCompass(p)){
            Location l = ((CompassMeta) p.getInventory().getItemInOffHand().getItemMeta()).getLodestone();
            assert l != null;
            l.add(0,1,0);
            for (Item item : items) {
                shiftItemToLocation(p,l,blockLocation,item.getItemStack());
            }
        }
    }
    private static void shiftItemToLocation(Player p, Location l , Location lodeStoneLocation, ItemStack item){
        int LodeStoneCost = getLodeStoneCost(l,lodeStoneLocation);
        if((getTotalExpPoints(p) > LodeStoneCost)){
            HashMap<Integer,ItemStack> leftOvers;
            if(l.getBlock().getType().equals(Material.CHEST)){
                Chest c = (Chest) l.getBlock().getState();
                leftOvers = c.getBlockInventory().addItem(item);
                p.giveExp(-LodeStoneCost);
                if(!leftOvers.isEmpty()){
                    for (ItemStack i : leftOvers.values()) {
                        l.getWorld().dropItemNaturally(l,i);
                    }
                }
            }else {
                p.giveExp(-LodeStoneCost);
                l.getWorld().dropItemNaturally(l,item);
            }
        }
    }
    private static int getLodeStoneCost(Location breakLocation, Location lodeStoneLocation){
        return (int) Math.round(breakLocation.distance(lodeStoneLocation) * lodestoneFillCost);
    }



}

