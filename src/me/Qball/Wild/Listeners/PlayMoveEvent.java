package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.TeleportTar;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;



public class PlayMoveEvent implements Listener {
	@EventHandler

public void onMove(PlayerMoveEvent e)
{
		TeleportTar tele = new TeleportTar();
	
	if (TeleportTar.CmdUsed.contains(e.getPlayer().getUniqueId()))
	{
		e.getPlayer().sendMessage(ChatColor.RED +"Teleportation canceled");
	}
}
}
