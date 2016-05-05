package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;
import me.Qball.Wild.Wild;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.ps.*;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TeleportTar {
	public static Plugin wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");
    private static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
  
  public  static void TP(final Location loc, final Player target)
  
    {	
	 if(wild.getConfig().getBoolean("Towny"))
	 {
	 if(!TownyUniverse.isWilderness(loc.getBlock()))
  	{
		 
		 if(Wild.Retries()!=0)
		 {
			 Wild.Random(target);
			 
		 }
  	}
	 }
  	 if (wild.getConfig().getBoolean("Factions"))
  	{
  		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
  		if(!faction.isNone())
  		{
  			if(Wild.Retries()!= 0)
  			{
  			Wild.Random(target);
  			}
  			
  		}
  			
  			

  	}
    	if (CmdUsed.contains(target.getUniqueId()))
    	{
    		target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
    	}
    	
    	else 
    	{ 
    		
    		CmdUsed.add(target.getUniqueId()); 
    		String Wait = String.valueOf(confWait);
    	     String delayMsg = wild.getConfig().getString("WaitMsg");
    	     String DelayMsg = delayMsg.replaceAll("\\{wait}\\", Wait);
    	    
			final String Teleport = wild.getConfig().getString("Teleport");
	        int wait = confWait*20;
	        if(wild.getConfig().getBoolean("Play"))
	        {
	        if (wait >0) { 
	            target.sendMessage(ChatColor.translateAlternateColorCodes('&',DelayMsg));

	            new BukkitRunnable() {
	                public void run() {
	                	Wild.applyPotions(target);
	                     target.teleport(loc);
	                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	 					target.playSound(loc, Sounds.getSound(), 3, 10);
	 					   CmdUsed.remove(target.getUniqueId());
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
	        else
	        {
	        	if(wait>0)
	        	{
		            target.sendMessage(ChatColor.translateAlternateColorCodes('&',DelayMsg));
	        		new BukkitRunnable()
	        		{
	        			public void run()
	        			{
	        				Wild.applyPotions(target);
	        				 target.teleport(loc);
		                     target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Teleport));
		 					
		 					   CmdUsed.remove(target.getUniqueId());
	        			}
	        		}.runTaskLater(wild, wait);
	        	}
	        	else
	        	{

		        	Wild.applyPotions(target);
		            target.teleport(loc);
		            target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Teleport));
	        	}
	        }
	        }
    }
}