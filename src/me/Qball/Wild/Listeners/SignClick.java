package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.GetRandomLocation;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
public class SignClick implements Listener {
	private final Wild wild;
	public SignClick(Wild plugin)
	{
		wild = plugin;
	}
	

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player target = e.getPlayer();
		Sign sign;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getClickedBlock().getState() instanceof Sign) {
			sign = (Sign) e.getClickedBlock().getState();
			if (sign.getLine(1).equalsIgnoreCase("[§1Wild§0]")&& sign.getLine(0).equalsIgnoreCase("§4====================")) {
				CheckPerms perms = new CheckPerms(wild);
				perms.check(e.getPlayer());

				
			
			}
		}
	}

	
}
