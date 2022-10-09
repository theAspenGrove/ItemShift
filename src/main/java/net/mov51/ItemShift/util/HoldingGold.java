package net.mov51.ItemShift.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HoldingGold {

    public static Material[] goldItems = {
            Material.GOLDEN_SWORD,
            Material.GOLDEN_SHOVEL,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_AXE,
            Material.GOLDEN_HOE
    };

    public static boolean isHoldingGold(Player player) {
        return Arrays.asList(Arrays.stream(goldItems).toArray()).contains(player.getInventory().getItemInMainHand().getType());
    }
}
