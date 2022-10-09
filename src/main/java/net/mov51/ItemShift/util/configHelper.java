package net.mov51.ItemShift.util;

import net.mov51.ItemShift.ItemShift;
import org.bukkit.configuration.file.FileConfiguration;

public class configHelper {
    public static FileConfiguration c;
    public static String pluginPrefix;
    public static float levelCost;

    public static void loadConfig(){
        c = ItemShift.plugin.getConfig();
        levelCost = (c.getInt("level-cost") != 0 ? c.getInt("level-cost") : 0.0556f);
        pluginPrefix = c.getString("chat-prefix") != null ? c.getString("chat-prefix") : "§8[§6ItemShift§8]§r ";

    }

}
