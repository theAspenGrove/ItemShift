package net.mov51.ItemShift;

import net.mov51.ItemShift.listeners.BlockBreak;
import net.mov51.ItemShift.listeners.ItemDamage;
import net.mov51.ItemShift.listeners.ItemPickup;
import net.mov51.ItemShift.listeners.ItemSpawn;
import net.mov51.periderm.logs.AspenLogHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static net.mov51.ItemShift.util.ConfigHelper.loadConfig;

public final class ItemShift extends JavaPlugin {
    public static Logger logger;
    public static AspenLogHelper logHelper;
    public static ItemShift plugin;
    @Override
    public void onEnable() {
        plugin = getPlugin(ItemShift.class);
        //create default config file
        this.saveDefaultConfig();
        loadConfig();
        //create Aspen logger
        logger = this.getLogger();
        logHelper = new AspenLogHelper(logger, "ItemShift");
        //register events
        getServer().getPluginManager().registerEvents(new ItemSpawn(), this);
        getServer().getPluginManager().registerEvents(new ItemDamage(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new ItemPickup(), this);
        //send enable message
        logHelper.sendLogInfo("Items are being shifted!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
