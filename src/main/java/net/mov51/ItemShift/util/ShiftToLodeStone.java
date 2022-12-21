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

import static net.mov51.ItemShift.util.ConfigHelper.shulkerFillCost;

public class ShiftToLodeStone {
    public static boolean isHoldingLodeStoneCompass(Player p){
        return isLodeStoneCompass(p.getInventory().getItemInOffHand());
    }
    private static boolean isLodeStoneCompass(ItemStack item){
        return item.getType().equals(Material.COMPASS) && item.getItemMeta() != null && ((CompassMeta) item.getItemMeta()).hasLodestone();
    }
    public static void sendToLodeStone(Player p,ItemStack item){
        if(isHoldingLodeStoneCompass(p)){
            Location l = ((CompassMeta) p.getInventory().getItemInOffHand().getItemMeta()).getLodestone();
            assert l != null;
            l.add(0,1,0);
            shiftItemToLocation(l,item);
            p.giveExp(-shulkerFillCost);p.giveExp(-shulkerFillCost);
        }
    }

    public static void sendToLodeStone(Player p, List<Item> items){
        for (Item item : items) {
            sendToLodeStone(p,item.getItemStack());
        }
    }
    private static void shiftItemToLocation(Location l, ItemStack item){
        HashMap<Integer,ItemStack> leftOvers = null;
        if(l.getBlock().getType().equals(Material.CHEST)){
            Chest c = (Chest) l.getBlock().getState();
            leftOvers = c.getBlockInventory().addItem(item);
        }
        if(leftOvers != null){
            for (ItemStack i : leftOvers.values()) {
                l.getWorld().dropItemNaturally(l,i);
            }
        }
        l.getWorld().dropItemNaturally(l,item);
    }

}

