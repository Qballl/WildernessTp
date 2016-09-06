package me.Qball.Wild.Listeners;

import java.util.List;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.TeleportTar;

import  org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseEvent {
	private Wild wild= Wild.getInstance();
	public void onCmd(PlayerCommandPreprocessEvent e)
	{
		String command = e.getMessage().toLowerCase(); 
		List<String> blockedCmds = wild.getConfig().getStringList("BlockCommands");
		if(!TeleportTar.CmdUsed.contains(e.getPlayer().getUniqueId())) {
				return;
			}
		for(String cmd : blockedCmds) {
			if(command.contains(cmd)); {
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("Block_Command_Message")));
				e.setCancelled(true);
				break;
			}
		} 
		
	}
	 

}
 