package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Listeners.PlayMoveEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTarget {
	private final Wild wild;
	private GetRandomLocation random;
	public Checks check;
	public TeleportTarget(Wild plugin)
	{
		wild = plugin;
		random = new GetRandomLocation(plugin);
		check = new Checks(wild);
	}
    public static ArrayList<UUID> cmdUsed = new ArrayList<UUID>();
    
   
    
  public void TP(final Location loc, final Player target)
    {		    
	    TeleportTarget teleportTarget = new TeleportTarget(wild);
	  	int confWait = wild.getConfig().getInt("Wait");
    	if (cmdUsed.contains(target.getUniqueId()))
    	{
    		target.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
    	}
    	else 
    	{
    		 cmdUsed.add(target.getUniqueId()); 
    		 String Wait = String.valueOf(confWait);
    	     String delayMsg = wild.getConfig().getString("WaitMsg");
    	     String DelayMsg = delayMsg.replaceAll("\\{wait}", Wait);
    	    String location = String.valueOf(loc.getBlockX()) + " " + String.valueOf(loc.getBlockY()) + " " + String.valueOf(loc.getBlockZ());
 			final String Teleport = wild.getConfig().getString("Teleport").replace("<loc>",location);
	        int wait = confWait*20;
	        if(wild.getConfig().getBoolean("Play"))
	        {
	        if (wait >0) {
	        	if(wild.portalUsed.contains(target.getUniqueId()))
	        	{
	        		if(!check.blacklistBiome(loc))
                	{
                	 cmdUsed.remove(target.getUniqueId());
                	 Wild.applyPotions(target);
                     target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
                     target.sendMessage(ChatColor.translateAlternateColorCodes('&',Teleport));
 					 target.playSound(loc, Sounds.getSound(), 3, 10);
 					 teleportTarget.doCommands(target);
 					 if(Wild.cancel.contains(target.getUniqueId())){
							Wild.cancel.remove(target.getUniqueId());
						}
 					 }
	        	}
	        	else{
	            target.sendMessage(ChatColor.translateAlternateColorCodes('&',DelayMsg));

	            new BukkitRunnable() {
	                public void run() {
	                	if(!PlayMoveEvent.moved.contains(target.getUniqueId())&& !PlayMoveEvent.dontTele.contains(target.getUniqueId()))
	                			{        			
	                	if(!check.blacklistBiome(loc))
	                	{
	                	 cmdUsed.remove(target.getUniqueId());
	                	 Wild.applyPotions(target);
	                	 target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
	                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	 					 target.playSound(loc, Sounds.getSound(), 3, 10);
	 					 teleportTarget.doCommands(target);
	 					 if(Wild.cancel.contains(target.getUniqueId())){
							Wild.cancel.remove(target.getUniqueId());
						 }
	 					 if(PlayMoveEvent.moved.contains(target.getUniqueId()))
	 					   {
	 						   PlayMoveEvent.moved.remove(target.getUniqueId());
	 					   }
	 					   if(wild.portalUsed.contains(target.getUniqueId()))
	 						   wild.portalUsed.remove(target.getUniqueId());
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
	                		else if(wild.portalUsed.contains(target.getUniqueId()))
	                			wild.portalUsed.remove(target.getUniqueId());
        				
        				}
	                }
	            }.runTaskLater(wild, wait);
	        	}
	        }  
	        
	        else
	        {
	        	if(!check.blacklistBiome(loc))
	        	{
                    cmdUsed.remove(target.getUniqueId());
	        	    Wild.applyPotions(target);
	        	    target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
	                target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
				    target.playSound(loc, Sounds.getSound(), 3, 10);
				    teleportTarget.doCommands(target);
				if(Wild.cancel.contains(target.getUniqueId())){
					Wild.cancel.remove(target.getUniqueId());
				}
				if(wild.portalUsed.contains(target.getUniqueId()))
	            	wild.portalUsed.remove(target.getUniqueId());
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
	        		if(wild.portalUsed.contains(target.getUniqueId()))
		        	{
		        		if(!check.blacklistBiome(loc))
	                	{
	                	 cmdUsed.remove(target.getUniqueId());
	                	 Wild.applyPotions(target);
	                	 target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
	                     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
	                     teleportTarget.doCommands(target);
	                     if(Wild.cancel.contains(target.getUniqueId())){
								Wild.cancel.remove(target.getUniqueId());
							}
	 					 }
		        	}
	        		else
		            target.sendMessage(ChatColor.translateAlternateColorCodes('&',DelayMsg));
	        		new BukkitRunnable()
	        		{
	        			public void run()
	        			{
	        				if(!PlayMoveEvent.moved.contains(target.getUniqueId()) && !PlayMoveEvent.dontTele.contains(target.getUniqueId()))
	        				{
	        				if(!check.blacklistBiome(loc))
	        				{
	        				cmdUsed.remove(target.getUniqueId());
	        				 Wild.applyPotions(target);
	        				 target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
		                     target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Teleport));
		                     teleportTarget.doCommands(target);
		                     if(Wild.cancel.contains(target.getUniqueId())){
									Wild.cancel.remove(target.getUniqueId());
								}
		 					  if(PlayMoveEvent.moved.contains(target.getUniqueId()))
		 					   {
		 						   PlayMoveEvent.moved.remove(target.getUniqueId());
		 					   }
		 					  if(wild.portalUsed.contains(target.getUniqueId()))
		 						  wild.portalUsed.remove(target.getUniqueId());
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
		                		else if(wild.portalUsed.contains(target.getUniqueId()))
		                			wild.portalUsed.remove(target.getUniqueId());
	        				}
	        			}
	        		}.runTaskLater(wild, wait);
	        	}
	        	else
	        	{
					cmdUsed.remove(target.getUniqueId());
		        	Wild.applyPotions(target);
					target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));
		            target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Teleport));
					teleportTarget.doCommands(target);
		            if(wild.portalUsed.contains(target.getUniqueId()))
						target.teleport(new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ(),0.0F,0.0F));   	wild.portalUsed.remove(target.getUniqueId());
					if(Wild.cancel.contains(target.getUniqueId())){
						Wild.cancel.remove(target.getUniqueId());
					}
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
		else if(wild.portalUsed.contains(target.getUniqueId())) {
        	wild.portalUsed.remove(target.getUniqueId());
         }
    }
    private void doCommands(Player p){
  		if(wild.getConfig().getString("PostCommand")==null)
  			return;
  		for(String command : wild.getConfig().getStringList("PostCommand")) {
  			command = command.replaceAll("\\{player}",p.getDisplayName());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		}
	}
  
}