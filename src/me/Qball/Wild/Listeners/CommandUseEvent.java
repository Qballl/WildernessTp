package me.Qball.Wild.Listeners;

import java.util.List;

import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Utils.Checks;
import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.TeleportTarget;

import  org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseEvent {
	private Wild wild = Wild.getInstance();
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCmd(PlayerCommandPreprocessEvent e) {
		String command = e.getMessage().toLowerCase();
		List<String> blockedCmds = wild.getConfig().getStringList("BlockCommands");
		if (TeleportTarget.cmdUsed.contains(e.getPlayer().getUniqueId())) {
			for (String cmd : blockedCmds) {
				if (command.contains(cmd)) ;
				{
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("Block_Command_Message")));
					e.setCancelled(true);
					break;
				}
			}
		}
		else if(e.getMessage().equalsIgnoreCase("wild")&&wild.getConfig().getBoolean("FBasics")) {
			e.setCancelled(true);
			CheckPerms check = new CheckPerms(wild);
			Checks checks = new Checks(wild);
			Player p = e.getPlayer();
				if(!checks.world(p))
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',wild.getConfig().getString("WorldMsg")));
				else
					check.check(p);
		}
	}
}