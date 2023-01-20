package net.mov51.ItemShift.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.HashMap;

import static net.mov51.ItemShift.util.ConfigHelper.lodestoneFillCost;
import static net.mov51.ItemShift.util.GiveItem.*;
import static net.mov51.ItemShift.util.XPHelper.hasEnoughXP;

public class ShiftToLodeStone {
    public static boolean isHoldingLodeStoneCompass(Player p){
        return isLodeStoneCompass(p.getInventory().getItemInOffHand());
    }
    private static boolean isLodeStoneCompass(ItemStack item) {
        return item.getType().equals(Material.COMPASS) && item.getItemMeta() != null && ((CompassMeta) item.getItemMeta()).hasLodestone();
    }

    public static void shiftItemToLocation(Player p, Location blockLocation, ItemStack item){
        if(isHoldingLodeStoneCompass(p)){
            Location lodestoneLocation = getLodestoneLocation(p.getInventory().getItemInOffHand());
            assert lodestoneLocation != null;
            lodestoneLocation.add(0,1,0);
            int LodeStoneCost = getLodeStoneCost(lodestoneLocation,blockLocation);
            if((hasEnoughXP(p,LodeStoneCost))){
                HashMap<Integer,ItemStack> leftOvers;
                if(lodestoneLocation.getBlock().getType().equals(Material.CHEST)){
                    Chest c = (Chest) lodestoneLocation.getBlock().getState();
                    leftOvers = c.getBlockInventory().addItem(item);
                    p.giveExp(-LodeStoneCost);
                    if(!leftOvers.isEmpty()){
                        for (ItemStack i : leftOvers.values()) {
                            lodestoneLocation.getWorld().dropItemNaturally(lodestoneLocation,i);
                        }
                    }
                }else {
                    p.giveExp(-LodeStoneCost);
                    lodestoneLocation.getWorld().dropItemNaturally(lodestoneLocation,item);
                }
                playPickupSound(p);
                incrementPickupStat(p,item);
            }else{
                giveItemToPlayer(p,item);
            }
        }
    }
    private static int getLodeStoneCost(Location breakLocation, Location lodeStoneLocation){
        return (int) Math.round(breakLocation.distance(lodeStoneLocation) * lodestoneFillCost);
    }

    public static Location getLodestoneLocation(ItemStack item){
        return ((CompassMeta) item.getItemMeta()).getLodestone();
    }

}

