package net.mov51.ItemShift;

import net.kyori.adventure.text.Component;
import net.mov51.ItemShift.command.toggle;
import net.mov51.ItemShift.listeners.itemDamage;
import net.mov51.ItemShift.listeners.itemSpawn;
import net.mov51.periderm.chat.AspenChatHelper;
import net.mov51.periderm.chat.PredefinedMessage;
import net.mov51.periderm.permissions.PermissionHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Itemshift extends JavaPlugin {

    public static PermissionHelper permHelper;
    public static AspenChatHelper chatHelper;

    public static final String pluginPrefix = "§8[§6ItemShift§8]§r ";

    @Override
    public void onEnable() {
        //Fired when the server enables the plugin
        System.out.println("Items are being shifted");
        getServer().getPluginManager().registerEvents(new itemSpawn(), this);
        getServer().getPluginManager().registerEvents(new itemDamage(), this);
        getCommand("autopickup").setExecutor(new toggle());
        chatHelper = new AspenChatHelper(pluginPrefix);
        permHelper = new PermissionHelper("AspenPrefix.",
                new PredefinedMessage(Component.text("You don't have permission to run that command!")),chatHelper);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
