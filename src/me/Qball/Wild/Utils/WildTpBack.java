package me.Qball.Wild.Utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WildTpBack {
	public static HashMap<UUID,Location> locations = new HashMap<UUID,Location>();
	public void saveLoc(Player p, Location loc)
	{
		if(locations.containsKey(p.getUniqueId()))
		{
			locations.remove(p.getUniqueId());
			locations.put(p.getUniqueId(), loc);
		}
		else
		{
			locations.put(p.getUniqueId(), loc);
		}
	}
	public void back(Player p)
	{
		Location loc = locations.get(p.getUniqueId());
		p.teleport(loc);
		locations.remove(p.getUniqueId());
	}
}
