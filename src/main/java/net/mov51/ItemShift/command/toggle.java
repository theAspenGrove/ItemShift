package net.mov51.ItemShift.command;

import net.mov51.ItemShift.command.util.MessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.mov51.ItemShift.command.util.arrayManager;

public class toggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            if (s.equalsIgnoreCase("autopickup")){
                if(player!=null && commandSender.hasPermission("itemshift.autopickup")){
                    arrayManager.INSTANCE.toggleAutoPickup(player);
                }else{
                    MessageBuilder.sendChatToPlayer("Your AutoPickup permissions have been revoked! Speak to a staff member!",player);
                }
            }
        }
        return false;
    }
}
