package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Listeners.PlayMoveEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class TeleportTar {
	public static Wild wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");
    public static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
    public GetRandomLocation random = new GetRandomLocation();
  public   void TP(final Location loc, final Player target)
    {		
  	
  	
    	if (CmdUsed.contains(target.getUniqueId()))
    	{
    		target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
    	}
    	
    	else 
    	{ 
    		
    		 CmdUsed.add(target.getUniqueId()); 
    		 String Wait = String.valueOf(confWait);
    	     String delayMsg = wild.getConfig().getString("WaitMsg");
    	     String DelayMsg = delayMsg.replaceAll("\\{wait\\}", Wait);
    	    
			final String Teleport = wild.getConfig().getString("Teleport");
	        int wait = confWait*20;
	        if(wild.getConfig().getBoolean("Play"))
	        {
	        if (wait >0) { 
	            target.sendMessage(ChatColor.translateAlternateColorCodes('&',DelayMsg));

	            new BukkitRunnable() {
	                public void run() {
	                	if(!PlayMoveEvent.moved.contains(target.getUniqueId())&& !PlayMoveEvent.dontTele.contains(target.getUniqueId()))
	                			{        			
	                	if(!Checks.blacklistBiome(loc))
	                	{
	                	 CmdUsed.remove(target.getUniqueId());
	                	 Wild.applyPotions(target);
	                     target.teleport(loc);
	                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	 					 target.playSound(loc, Sounds.getSound(), 3, 10);
	 					 
	 					   if(PlayMoveEvent.moved.contains(target.getUniqueId()))
	 					   {
	 						   PlayMoveEvent.moved.remove(target.getUniqueId());
	 					   }
	                	}
	                	else
	                	{
	                		if(wild.retries!=0)
	                		{
	                			String info = random.getWorldInfomation(target);
	                        	random.recallTeleport(random.getRandomLoc(info, target), target);
	                		}
	                		else
	               		 {
	               			 target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("No Suitable Location")));
	               		 }
	                	}
	             }
	                	else
        				{	
	                		if(PlayMoveEvent.moved.contains(target.getUniqueId()))
	                		{
        					PlayMoveEvent.moved.remove(target.getUniqueId());
	                		}
	                		else if (PlayMoveEvent.dontTele.contains(target.getUniqueId()))
	                		{
	                			PlayMoveEvent.dontTele.remove(target.getUniqueId());
	                		}
        				
        				}
	                }
	            }.runTaskLater(wild, wait);
	          
	        }  
	        
	        else
	        {
	        	if(!Checks.blacklistBiome(loc))
	        	{
	        	Wild.applyPotions(target);
	            target.teleport(loc);
	            target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
				target.playSound(loc, Sounds.getSound(), 3, 10);
	        	}
	        	else
	        	{
	        		if(wild.retries!=0)
	        		{
                	String info = random.getWorldInfomation(target);
                	random.recallTeleport(random.getRandomLoc(info, target), target);
	        		}
	        		else
	       		 {
	       			 target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("No Suitable Location")));
	       		 }
	        	}
				
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
	        				if(!PlayMoveEvent.moved.contains(target.getUniqueId()) && !PlayMoveEvent.dontTele.contains(target.getUniqueId()))
	        				{
	        				if(!Checks.blacklistBiome(loc))
	        				{
	        				CmdUsed.remove(target.getUniqueId());
	        				 Wild.applyPotions(target);
	        				 target.teleport(loc);
		                     target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Teleport));
		 					
		 					  if(PlayMoveEvent.moved.contains(target.getUniqueId()))
		 					   {
		 						   PlayMoveEvent.moved.remove(target.getUniqueId());
		 					   }
	        				}
	        				else
	        				{ 
	        					if(wild.retries!=0)
	        					{
	        					String info = random.getWorldInfomation(target);
	        	               	random.recallTeleport(random.getRandomLoc(info, target), target);
	        					}
	        					else
	        					 {
	        						 target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("No Suitable Location")));
	        					 }
	        				}
	        				
	        			}
	        				else
	        				{

		                		if(PlayMoveEvent.moved.contains(target.getUniqueId()))
		                		{
	        					PlayMoveEvent.moved.remove(target.getUniqueId());
		                		}
		                		else if (PlayMoveEvent.dontTele.contains(target.getUniqueId()))
		                		{
		                			PlayMoveEvent.dontTele.remove(target.getUniqueId());
		                		}
	        				}
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
    	if(PlayMoveEvent.moved.contains(target.getUniqueId()))
		   {
			   PlayMoveEvent.moved.remove(target.getUniqueId());
			   
		   }
		else if (PlayMoveEvent.dontTele.contains(target.getUniqueId()))
		{
			PlayMoveEvent.dontTele.remove(target.getUniqueId());
		}
    }
  
}