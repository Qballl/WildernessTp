package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.TeleportTar;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;


public class PlayMoveEvent implements Listener {
	public static ArrayList<UUID> moved = new ArrayList<UUID>();
	public Plugin wild = Wild.getInstance();
	@EventHandler
public void onMove(PlayerMoveEvent e)
{
	if (TeleportTar.CmdUsed.contains(e.getPlayer().getUniqueId()))
	{
		e.setCancelled(true);
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("CancelMsg")));
		TeleportTar.CmdUsed.remove(e.getPlayer().getUniqueId());
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("CancelMsg")));
		if(!moved.contains(e.getPlayer().getUniqueId())){
			moved.add(e.getPlayer().getUniqueId());
			
			}
		
		
	}
}
} 