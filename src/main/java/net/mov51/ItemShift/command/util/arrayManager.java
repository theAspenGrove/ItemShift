package net.mov51.ItemShift.command.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class arrayManager {

    public List<UUID> autoPickupList = new ArrayList<>();

    public void toggleAutoPickup(Player player){
        UUID uuid = player.getUniqueId();
        if(!arrayManager.INSTANCE.autoPickupList.contains(uuid)){
            arrayManager.INSTANCE.autoPickupList.add(uuid);
            if(arrayManager.INSTANCE.autoPickupList.contains(uuid)) {
                MessageBuilder.sendChatToPlayer("You are now picking up all items!", player);
            }else{
                MessageBuilder.sendChatToPlayer("Something Broke", player);
                MessageBuilder.sendChatToPlayer(arrayManager.INSTANCE.autoPickupList.toString(), player);
            }
        }else{
            while (arrayManager.INSTANCE.autoPickupList.contains(uuid)){
                arrayManager.INSTANCE.autoPickupList.remove(uuid);
                MessageBuilder.sendChatToPlayer("You are no longer picking up items!",player);
            }
        }
    }
    public arrayManager(){}
    //Create singleton instance - mov51
    public static final arrayManager INSTANCE = new arrayManager();
}
