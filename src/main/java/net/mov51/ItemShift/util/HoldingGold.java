package net.mov51.ItemShift.util;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.*;

import static net.mov51.ItemShift.util.ConfigHelper.nuggetCost;

public class HoldingGold {
    public static Material[] goldItems = {
            Material.GOLDEN_SWORD,
            Material.GOLDEN_SHOVEL,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_AXE,
            Material.GOLDEN_HOE
    };
    public static Material[] Shulkers = {
            Material.BLACK_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX,
            Material.BROWN_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX,
            Material.GRAY_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX,
            Material.LIGHT_BLUE_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX,
            Material.LIME_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX,
            Material.PINK_SHULKER_BOX,
            Material.PURPLE_SHULKER_BOX,
            Material.RED_SHULKER_BOX,
            Material.WHITE_SHULKER_BOX,
            Material.YELLOW_SHULKER_BOX,
            Material.SHULKER_BOX
    };
    public static boolean isHoldingGold(Player player) {
        return Arrays.asList(Arrays.stream(goldItems).toArray()).contains(player.getInventory().getItemInMainHand().getType());
    }
    public static boolean hasNuggets(Player player, boolean remove) {
        ItemStack[] inv = player.getInventory().getContents();
        ItemStack[] hotBar = Arrays.copyOfRange(inv, 0, 9);
        for (ItemStack item : hotBar) {
            if (containsNugget(item, remove)) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsNugget(ItemStack item,boolean remove){
        boolean hasNugget = false;
        if(item == null){
            return false;
        }
        if(item.getType() == Material.GOLD_NUGGET){
            if(remove){
                item.setAmount(item.getAmount()-nuggetCost);
            }
            hasNugget = true;
        } else if (Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(item.getType())) {
            BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
            ShulkerBox box = (ShulkerBox) meta.getBlockState();
            for (ItemStack i : box.getInventory().getContents()) {
                if(i != null) {
                    if(i.getType() == Material.GOLD_NUGGET){
                        if (remove) {
                            i.setAmount(i.getAmount()-nuggetCost);
                        }
                        hasNugget = true;
                        break;
                    }
                }
            }
            meta.setBlockState(box);
            item.setItemMeta(meta);
        }
        return hasNugget;
    }
}
