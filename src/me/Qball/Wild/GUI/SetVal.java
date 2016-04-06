package me.Qball.Wild.GUI;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class SetVal implements Listener {
	public static Plugin wild = Wild.getInstance();

	@EventHandler
	public  void onChat(AsyncPlayerChatEvent e)
	{	
		Player p = e.getPlayer();
		if(MainGui.editMode(p))
		{
			String value = e.getMessage();
			e.setCancelled(true);
			String val = InvClick.toSet.get(0);
			InvClick.toSet.remove(0);
			String x = value;
			 int X = Integer.parseInt(x);
			 wild.getConfig().set(val,(Object) X);
			 p.sendMessage(ChatColor.GREEN+"You have set the MinX");
			 wild.saveConfig();
		}
	}
}
 