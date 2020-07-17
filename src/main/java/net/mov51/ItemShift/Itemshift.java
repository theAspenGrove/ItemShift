package net.mov51.ItemShift;

import net.mov51.ItemShift.command.toggle;
import net.mov51.ItemShift.listeners.itemSpawn;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Itemshift extends JavaPlugin {

    @Override
    public void onEnable() {
        //Fired when the server enables the plugin
        System.out.println("Items are being shifted");
        getServer().getPluginManager().registerEvents(new itemSpawn(), this);
        getCommand("autopickup").setExecutor(new toggle());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
