package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTar {
	public static Plugin wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");
    private static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
  static  String delayMsg = wild.getConfig().getString("WaitMsg");
   static int wait = wild.getConfig().getInt("Wait");
    static String Wait = String.valueOf(wait);
    static String DelayMsg = delayMsg.replaceAll("\\{cool\\}",Wait);
    public  static void TP(final Location loc, final Player target)
    {	
    	if (CmdUsed.contains(target.getUniqueId()))
    			{
    		target.sendMessage(ChatColor.RED+"You have already used the command now please wait to be teleported");
    			}
    	else
    	{
    		
    		CmdUsed.add(target.getUniqueId());
			final String Teleport = wild.getConfig().getString("Teleport");
	        int wait = confWait*20;
	        if (wait >0) {
	            target.sendMessage(ChatColor.translateAlternateColorCodes('&', DelayMsg));

	            new BukkitRunnable() {
	                public void run() {
	                	Wild.applyPotions(target);
	                     target.teleport(loc);
	                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	 					target.playSound(loc, Sounds.getSound(), 3, 10);
	 					 
	                }
	            }.runTaskLater(wild, wait);
	            CmdUsed.remove(target.getUniqueId());
	        }
	        else
	        {
	        	Wild.applyPotions(target);
	            target.teleport(loc);
	            target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
				target.playSound(loc, Sounds.getSound(), 3, 10);
				CmdUsed.remove(target.getUniqueId());

	        }
		
    	}
    		
    	

    }


}


