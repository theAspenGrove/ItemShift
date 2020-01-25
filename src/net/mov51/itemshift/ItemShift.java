package net.mov51.itemshift;

import net.mov51.itemshift.command.toggle;
import net.mov51.itemshift.listeners.itemspawn;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemShift extends JavaPlugin {
    public void onEnable(){
        //Fired when the server enables the plugin
        System.out.println("Items are being shifted");
        getServer().getPluginManager().registerEvents(new itemspawn(), this);
        getCommand("autopickup").setExecutor(new toggle());
    }
}
