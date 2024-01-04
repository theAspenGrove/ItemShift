package net.mov51.ItemShift.listeners;

import org.bukkit.Material;
import org.bukkit.block.EndGateway;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.mov51.ItemShift.util.ShiftToLodeStone.getLodestoneLocation;
import static net.mov51.ItemShift.util.ShiftToLodeStone.isLodeStoneCompass;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class PlayerInteractEventListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if(e.getAction() != RIGHT_CLICK_BLOCK){
            return;
        }
        if(!e.getPlayer().isSneaking()){
            return;
        }
        if(e.getItem() == null){
            return;
        }
        if(e.getClickedBlock() == null){
            return;
        }
        if(e.getItem().getType() == Material.COMPASS){
            if(!isLodeStoneCompass(e.getItem())){
                return;
            }
            if(e.getPlayer().getInventory().getItemInOffHand().getType() != Material.NETHER_STAR){
                return;
            }
            if(getLodestoneLocation(e.getItem()) == null){
                return;
            }
            if(getLodestoneLocation(e.getItem()).getWorld() != e.getClickedBlock().getWorld()){
                return;
            }

            if (e.getClickedBlock().getType() == Material.NETHERITE_BLOCK) {
                e.getClickedBlock().setType(Material.END_GATEWAY);
                EndGateway EG = (EndGateway) e.getClickedBlock().getLocation().getBlock().getState();
                EG.setExitLocation(getLodestoneLocation(e.getItem()).add(0, 1, 0));
                EG.setExactTeleport(true);
                EG.update();
                e.getPlayer().getInventory().getItemInOffHand().setAmount(e.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
                return;
            }
        }else if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.NETHER_STAR){
            if(e.getClickedBlock().getType() == Material.END_GATEWAY){
                EndGateway EG = (EndGateway) e.getClickedBlock().getLocation().getBlock().getState();
                if(EG.getExitLocation() == null){
                    return;
                }
                if(EG.isExactTeleport()){
                    e.getClickedBlock().getLocation().getBlock().setType(Material.NETHERITE_BLOCK);
                }
            }
        }


    }
}
