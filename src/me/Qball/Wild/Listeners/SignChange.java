package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.Checks;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

public class SignChange implements Listener {
	public static Plugin wild = Wild.getInstance();
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		String Message = wild.getConfig().getString("No-Perm");
		if (e.getLine(1).equalsIgnoreCase("[wild]")&& 
				e.getLine(0).equalsIgnoreCase("wildtp"))
						{
			
			if(e.getPlayer().hasPermission("wild.wildtp.create.sign"))
			{
			if(Checks.World(e.getPlayer()))
				
				 {
					e.setLine(0, "§4====================");
					e.setLine(1, "[§1Wild§0]");
					e.setLine(2, "§4====================");
					e.getPlayer().sendMessage(ChatColor.GREEN+ "Successfully made a new WildTP sign");
				} 
			else
			{
				e.getPlayer().sendMessage(ChatColor.RED+"Signs cannot be placed in this world as the command isnt allowed in this world");
				e.getBlock().breakNaturally();
				e.setCancelled(true);
				
			}
			}
				else 
				{
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
			e.setCancelled(true);
				}
			}	
	
		
			
		}
	}
			

	

