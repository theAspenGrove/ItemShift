package net.mov51.ItemShift.util;

import org.bukkit.entity.Player;

public class XPHelper {
    public static int getTotalExpPoints(Player p){
        int level = p.getLevel();
        if(level >= 32){
            double points = (((4.5 * (level^2)) - 162.5) * level) + 2220;
            return (int) Math.floor(points) + (pointsToLevel(p.getExpToLevel(),p.getExp()));
        } else if (level >= 17) {
            double points = (((2.5 * (level^2)) - 40.5) * level) + 360;
            return (int) Math.floor(points) + (pointsToLevel(p.getExpToLevel(),p.getExp()));
        }else{
            double points = (((level^2) + 6) * level) + 2220;
            return (int) Math.floor(points) + (pointsToLevel(p.getExpToLevel(),p.getExp()));
        }
    }
    private static int pointsToLevel(int progressRequired,float progress){
        return Math.round(progressRequired * progress);
    }
}
