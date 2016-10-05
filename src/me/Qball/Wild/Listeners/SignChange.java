package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.Checks;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

public class SignChange implements Listener {
	private final Wild wild;
	public SignChange(Wild plugin)
	{
		wild = plugin;
	}
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		String noPermMsg = wild.getConfig().getString("No-Perm");
		Checks check = new Checks(wild);
		if (e.getLine(1).equalsIgnoreCase("[wild]")&& 
				e.getLine(0).equalsIgnoreCase("wildtp"))
						{
			
			if(e.getPlayer().hasPermission("wild.wildtp.create.sign"))
			{
			if(check.world(e.getPlayer()))
				
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
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&',noPermMsg));
			e.setCancelled(true);
				}
			}	
	
		
			
		}
	}
			

	

