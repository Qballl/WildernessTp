package me.Qball.Wild.GUI;

import java.util.List;
import me.Qball.Wild.Wild;
import me.Qball.Wild.Commands.CmdWildTp;
import me.Qball.Wild.Utils.WorldInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SetVal implements Listener {

	public static Wild wild = Wild.getInstance();

	@EventHandler
	public  void onChat( AsyncPlayerChatEvent  e)
	{	
		final Player p = e.getPlayer();
		
		if(MainGui.editMode(p))
		{
			final String value = e.getMessage();
			e.setCancelled(true);
			new BukkitRunnable()
			{

				@Override
				public void run() {
					
					if (value.equalsIgnoreCase("exit")||value.equalsIgnoreCase("cancel"))
					{
						MainGui.removeEdit(p);
						if(InvClick.Add.contains(p.getUniqueId()))
						{
							InvClick.Add.remove(p.getUniqueId());
						}
						else if(InvClick.Set.contains(p.getUniqueId()))
						{
							InvClick.Set.remove(p.getUniqueId());
						} 
						else if(InvClick.Messages.contains(p.getUniqueId()))
						{
							InvClick.Messages.remove(p.getUniqueId());
						}
						else if(InvClick.Sounds.contains(p.getUniqueId()))
						{
							InvClick.Sounds.remove(p.getUniqueId());						
						}
					
						p.sendMessage(ChatColor.GREEN+ " You have exited edit mode. Game play will return to normal");
					}
					else
					{
					if(InvClick.Set.contains(p.getUniqueId()))
					{
					 InvClick.Set.remove(p.getUniqueId());
					 String val = InvClick.toSet.get(0);
					 InvClick.toSet.clear();
					 String x = value;
					 x = x.replaceAll("[^\\d-]", "");
					 int X = Integer.parseInt(x);
					 wild.getConfig().set(val,(Object) X);
					 p.sendMessage(ChatColor.GREEN+"You have set the " + val);
					 wild.saveConfig();
					 MainGui.removeEdit(p);
					 Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
					}
					else if(InvClick.Messages.contains(p.getUniqueId()))
					{
						InvClick.Messages.remove(p.getUniqueId());
						String message = value;
						String val = InvClick.toSet.get(0);
						InvClick.toSet.clear();
						wild.getConfig().set(val, message);
						p.sendMessage(ChatColor.GREEN+"You have set the " + val + " message");
						wild.saveConfig();
						MainGui.removeEdit(p);
						Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
					}
					else if(InvClick.Add.contains(p.getUniqueId()))
					{
						
						InvClick.Add.remove(p.getUniqueId());
						String message = value;
						String val = InvClick.toSet.get(0); 
						InvClick.toSet.clear();
						if(val.equalsIgnoreCase("potions"))
						{
							
							List<String> Potions = wild.getListPots();
						    Potions.add(message);
						    System.out.println(wild);
						    wild.getConfig().set("Potions", Potions);
							wild.saveConfig();
							p.sendMessage("You have added " + message + " to the list of potions");
							
						}
						else if(val.equalsIgnoreCase("worlds") &&!CmdWildTp.dev.contains(p.getUniqueId()))
						{
							String[] info = message.split(" ");
							WorldInfo world = new WorldInfo();
							world.setWorldName(info[0]);
							world.setMinX(info[0], Integer.parseInt(info[1]));
							world.setMaxX(info[0], Integer.parseInt(info[2]));
							world.setMinZ(info[0], Integer.parseInt(info[3]));
							world.setMaxZ(info[0], Integer.parseInt(info[4]));
							p.sendMessage(ChatColor.GREEN+"You have added " + message + " to the allowed worlds");
							 

						}
						Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
						MainGui.removeEdit(p);
					}
					else if(InvClick.Sounds.contains(p.getUniqueId()))
					{
						InvClick.Sounds.remove(p.getUniqueId());
						String message = value;
						String val = InvClick.toSet.get(0);
						InvClick.toSet.clear();
						wild.getConfig().set(val, message);
						p.sendMessage(ChatColor.GREEN+"You have set the " + val + " as the sound that will be heard");
						wild.saveConfig();
						MainGui.removeEdit(p);
						Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
					}
				}
					
					
				}
				
			}.runTaskLater(wild, 1);
				
			
		
		
	}
	}
}
 