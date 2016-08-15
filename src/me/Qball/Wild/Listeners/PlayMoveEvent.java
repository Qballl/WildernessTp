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
	public static ArrayList<UUID>dontTele = new ArrayList<>();
	@EventHandler
public void onMove(PlayerMoveEvent e)
{
	if (TeleportTar.CmdUsed.contains(e.getPlayer().getUniqueId()))
	{
		
		if(e.getTo().getBlockX()==e.getFrom().getBlockX() &&
				e.getTo().getBlockY() == e.getFrom().getBlockY()&&
				e.getTo().getBlockZ() == e.getFrom().getBlockZ())
		{
			return;
		}
		TeleportTar.CmdUsed.remove(e.getPlayer().getUniqueId());
		if(!moved.contains(e.getPlayer().getUniqueId())){
			moved.add(e.getPlayer().getUniqueId()); 
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("CancelMsg")));
			Wild.cooldownTime.remove(e.getPlayer().getUniqueId());
			Wild.cooldownCheck.remove(e.getPlayer().getUniqueId());
			dontTele.add(e.getPlayer().getUniqueId());
			}		
		
	} 
}
} 