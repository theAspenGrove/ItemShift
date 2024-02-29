package net.mov51.ItemShift.util;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.*;

import static net.mov51.ItemShift.util.ConfigHelper.nuggetCost;

public class HoldingGold {
    public static Material[] goldTools = {
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
        return Arrays.asList(Arrays.stream(goldTools).toArray()).contains(player.getInventory().getItemInMainHand().getType());
    }
    public static boolean isWearingGoldArmor(Player player){
        if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() == Material.GOLDEN_HELMET){
            return true;
        }else if(player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.GOLDEN_CHESTPLATE){
            return true;
        }else if(player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getType() == Material.GOLDEN_LEGGINGS){
            return true;
        }else if(player.getInventory().getBoots() != null && player.getInventory().getBoots().getType() == Material.GOLDEN_BOOTS){
            return true;
        }
        return false;
    }
    public static boolean isShulker(ItemStack item){
        return isShulker(item.getType());
    }
    public static boolean isShulker(Material material){
        return Arrays.asList(Arrays.stream(Shulkers).toArray()).contains(material);
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
