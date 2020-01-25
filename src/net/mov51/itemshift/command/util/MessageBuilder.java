package net.mov51.itemshift.command.util;

import org.bukkit.entity.Player;

public class MessageBuilder {

    public static final String PREFIX = "§c§lCapeCraft §9»§r ";

    public static void sendToPlayer(String message, Player player){
        player.sendMessage(PREFIX + message);
    }
}
