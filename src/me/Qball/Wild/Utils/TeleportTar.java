package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTar {
	public static Plugin wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");


    public  static void TP(final Location loc, final Player target)
    {	
    	final String Teleport = wild.getConfig().getString("Teleport");
        int wait = confWait*20;
        if (wait >0) {
            target.sendMessage(ChatColor.RED + "Teleporting in " + confWait + " seconds.");

            new BukkitRunnable() {
                public void run() {
                	Wild.applyPotions(target);
                     target.teleport(loc);
                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
 					target.playSound(loc, Sounds.getSound(), 3, 10);

                }
            }.runTaskLater(wild, wait);
        }
        else
        {
        	Wild.applyPotions(target);
            target.teleport(loc);
            target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
			target.playSound(loc, Sounds.getSound(), 3, 10);

        }

    }


}


