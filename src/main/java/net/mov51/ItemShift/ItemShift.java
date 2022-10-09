package net.mov51.ItemShift;

import net.kyori.adventure.text.Component;
import net.mov51.ItemShift.listeners.itemDamage;
import net.mov51.ItemShift.listeners.itemSpawn;
import net.mov51.periderm.chat.AspenChatHelper;
import net.mov51.periderm.chat.PredefinedMessage;
import net.mov51.periderm.logs.AspenLogHelper;
import net.mov51.periderm.permissions.PermissionHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static net.mov51.ItemShift.util.configHelper.loadConfig;
import static net.mov51.ItemShift.util.configHelper.pluginPrefix;

public final class ItemShift extends JavaPlugin {

    public static PermissionHelper permHelper;
    public static AspenChatHelper chatHelper;
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
        //create Aspen chat helper
        chatHelper = new AspenChatHelper(pluginPrefix);
        //register events
        getServer().getPluginManager().registerEvents(new itemSpawn(), this);
        getServer().getPluginManager().registerEvents(new itemDamage(), this);
        //create permission helper
        permHelper = new PermissionHelper("AspenPrefix.",
                new PredefinedMessage(Component.text("You don't have permission to run that command!")),chatHelper);
        logHelper.sendLogInfo("Items are being shifted!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
