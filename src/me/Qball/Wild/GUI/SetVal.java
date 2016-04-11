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
			if(InvClick.Set.contains(e.getPlayer().getUniqueId()))
			{
			InvClick.Set.remove(e.getPlayer().getUniqueId());
			String val = InvClick.toSet.get(0);
			InvClick.toSet.clear();
			String x = value;
			 int X = Integer.parseInt(x);
			 wild.getConfig().set(val,(Object) X);
			 p.sendMessage(ChatColor.GREEN+"You have set the " + val);
			 wild.saveConfig();
			}
			else if(InvClick.Messages.contains(e.getPlayer().getUniqueId()))
			{
				InvClick.Messages.remove(e.getPlayer().getUniqueId());
				String message = value;
				String val = InvClick.toSet.get(0);
				InvClick.toSet.clear();
				wild.getConfig().set(val, message);
				
				p.sendMessage(ChatColor.GREEN+"You have set the " + val + " message");
			}
			else if(InvClick.Add.contains(e.getPlayer().getUniqueId()))
			{
				
				InvClick.Add.remove(e.getPlayer().getUniqueId());
				String message = value;
				String val = InvClick.toSet.get(0);
				InvClick.toSet.clear();
				wild.getConfig().set(val, message);
				p.sendMessage(ChatColor.GREEN+"You added " + val + " to the list");
			}
			else if(InvClick.Sounds.contains(e.getPlayer().getUniqueId()))
			{
				InvClick.Sounds.remove(e.getPlayer().getUniqueId());
				String message = value;
				String val = InvClick.toSet.get(0);
				InvClick.toSet.clear();
				wild.getConfig().set(val, message);
				p.sendMessage(ChatColor.GREEN+"You have set the " + val + " as the sound that will be heard");
			}
		}
	}
}
 