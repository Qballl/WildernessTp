package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

public class SignChange implements Listener {
	public static Plugin wild = Wild.getInstance();
	@EventHandler
	public void onSignChange(SignChangeEvent player) {
		String Message = wild.getConfig().getString("No-Perm");
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		if (player.getLine(1).equalsIgnoreCase("[wild]")&& 
				player.getLine(0).equalsIgnoreCase("wildtp"))
						{
			if(player.getPlayer().hasPermission("wild.wildtp.create.sign"))
			{
			if (player.getPlayer().getWorld().getBiome(x, z) == Biome.HELL) {
				player.getPlayer().sendMessage(ChatColor.RED+ "Signs cannot be put in the nether");
				player.getBlock().breakNaturally();
				player.setCancelled(true);
			} else {
				if (player.getPlayer().getWorld().getBiome(x, z) == Biome.SKY)
					{
					player.getPlayer().sendMessage(ChatColor.RED+ "Signs cannot be put in the end");
					player.getBlock().breakNaturally();
					player.setCancelled(true);
				} else {
					player.setLine(0, "§4====================");
					player.setLine(1, "[§1Wild§0]");
					player.setLine(2, "§4====================");
					player.getPlayer().sendMessage(ChatColor.GREEN+ "Successfully made a new WildTP sign");
				}
			}
		}
			else 
		{
			player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
			player.setCancelled(true);
		}
		}
			

	}
}
