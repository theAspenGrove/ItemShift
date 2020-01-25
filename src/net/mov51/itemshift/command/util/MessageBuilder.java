package net.mov51.itemshift.command.util;

import org.bukkit.entity.Player;

public class MessageBuilder {

    public static final String PREFIX = "§c§lCapeCraft §9»§r ";

    public static void sendChatToPlayer(String message, Player player){
        player.sendMessage(PREFIX + message);
    }
    public static void sendBarToPlayer(String message, Player player){
        player.sendTitle("",message,10,60,10);
    }
}
