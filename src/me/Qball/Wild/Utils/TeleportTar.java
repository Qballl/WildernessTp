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
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.ps.*;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TeleportTar {
	public static Plugin wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");
    private static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
   // protected static String wait = String.valueOf(wild.getConfig().getString("Wait"));
   // protected static String delayMsg = wild.getConfig().getString("WaitMsg");
   // public static String DelayMsg = delayMsg.replaceAll("\\{wait}\\", wait);
  public  static void TP(final Location loc, final Player target)
  
    {	
	 if(TownyUniverse.isWilderness(loc.getBlock())&&wild.getConfig().getBoolean("Towny"));
  	{
  		Wild.Random(target);
  	}
  	 if (wild.getConfig().getBoolean("Factions"))
  	{
  		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
  		if(faction != FactionColl.get().getByName("Wilderness"))
  		{
  			Wild.Random(target);
  			
  		}
  			
  			

  	}
    	if (CmdUsed.contains(target.getUniqueId()))
    	{
    		target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
    	}
    	
    	else 
    	{
    		
    		CmdUsed.add(target.getUniqueId()); 
    	
			final String Teleport = wild.getConfig().getString("Teleport");
	        int wait = confWait*20;
	        if(wild.getConfig().getBoolean("Play"))
	        {
	        if (wait >0) { 
	            target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("WaitMsg")));

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
		            target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("WaitMsg")));
	        		new BukkitRunnable()
	        		{
	        			public void run()
	        			{
	        				Wild.applyPotions(target);
	        				 target.teleport(loc);
		                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
		 					
		 					   CmdUsed.remove(target.getUniqueId());
	        			}
	        		}.runTaskLater(wild, wait);
	        	}
	        	else
	        	{

		        	Wild.applyPotions(target);
		            target.teleport(loc);
		            target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	        	}
	        }
	        
	        }
	       
    		
    	

    }


}


