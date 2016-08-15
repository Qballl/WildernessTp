package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.TeleportTar;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseEvent {

	public void onCmd(PlayerCommandPreprocessEvent e)
	{
		String[] commandUsed = e.getMessage().split(" ");
		String command = e.getMessage().toLowerCase(); 
		if(command.contains("home")
				||command.contains("spawn")
				||command.contains("tpa")
				||command.contains("tp"))
		{
			if(TeleportTar.CmdUsed.contains(e.getPlayer().getUniqueId()))
			{
				e.getPlayer().sendMessage(ChatColor.RED + "Command is forbidden and wont complete please wait to be randomly teleported");
				e.setCancelled(true);
			}
		} 
	}
	 

}
