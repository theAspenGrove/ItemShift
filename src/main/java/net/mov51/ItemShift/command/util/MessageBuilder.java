package net.mov51.ItemShift.command.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class MessageBuilder {

    public static final String PREFIX = "§c§lCapeCraft §9»§r ";

    public static void sendChatToPlayer(String message, Player player){
        player.sendMessage(PREFIX + message);
    }
    public static void sendBarToPlayer(String message, Player player){
        BaseComponent[] msg = new ComponentBuilder(message).color(ChatColor.YELLOW).bold( true ).create();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
    }
}
