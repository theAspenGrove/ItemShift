package net.mov51.ItemShift.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.Arrays;
import java.util.HashMap;

import static net.mov51.ItemShift.util.ConfigHelper.lodestoneFillCost;
import static net.mov51.ItemShift.util.GiveItem.*;
import static net.mov51.ItemShift.util.XPHelper.hasEnoughXP;

public class ShiftToLodeStone {
    public static Material[] container = {Material.CHEST,Material.TRAPPED_CHEST,Material.SHULKER_BOX,Material.BARREL,Material.HOPPER,Material.DROPPER,Material.DISPENSER, Material.BLACK_SHULKER_BOX,Material.BLUE_SHULKER_BOX,Material.BROWN_SHULKER_BOX,Material.CYAN_SHULKER_BOX,Material.GRAY_SHULKER_BOX,Material.GREEN_SHULKER_BOX,Material.LIGHT_BLUE_SHULKER_BOX,Material.LIGHT_GRAY_SHULKER_BOX,Material.LIME_SHULKER_BOX,Material.MAGENTA_SHULKER_BOX,Material.ORANGE_SHULKER_BOX,Material.PINK_SHULKER_BOX,Material.PURPLE_SHULKER_BOX,Material.RED_SHULKER_BOX,Material.WHITE_SHULKER_BOX,Material.YELLOW_SHULKER_BOX};
    public static boolean isHoldingLodeStoneCompass(Player p){
        return isLodeStoneCompass(p.getInventory().getItemInOffHand());
    }
    public static boolean isLodeStoneCompass(ItemStack item) {
        return item.getType().equals(Material.COMPASS) && item.getItemMeta() != null && ((CompassMeta) item.getItemMeta()).hasLodestone();
    }

    public static void shiftItemToLocation(Player p,ItemStack lodeStone, Location blockLocation, ItemStack item){
            Location lodestoneLocation = getLodestoneLocation(lodeStone);
            assert lodestoneLocation != null;
            lodestoneLocation.add(0,1,0);
            int LodeStoneCost = getLodeStoneCost(lodestoneLocation,blockLocation);
            if((hasEnoughXP(p,LodeStoneCost))){
                HashMap<Integer,ItemStack> leftOvers;
                if(Arrays.stream(container).anyMatch(material -> material.equals(lodestoneLocation.getBlock().getType()))){
                    Container c = (Container) lodestoneLocation.getBlock().getState();
                    leftOvers = c.getInventory().addItem(item);
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

    public static void shiftItemToLocation(Player p, Location blockLocation, ItemStack item){
        if(isHoldingLodeStoneCompass(p)){
            shiftItemToLocation(p,p.getInventory().getItemInOffHand(),blockLocation,item);
        }
    }
    private static int getLodeStoneCost(Location breakLocation, Location lodeStoneLocation){
        return (int) Math.round(breakLocation.distance(lodeStoneLocation) * lodestoneFillCost);
    }

    public static Location getLodestoneLocation(ItemStack item){
        return ((CompassMeta) item.getItemMeta()).getLodestone();
    }

}

