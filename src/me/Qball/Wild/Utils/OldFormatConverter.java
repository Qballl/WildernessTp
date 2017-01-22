package me.Qball.Wild.Utils;

import java.util.ConcurrentModificationException;
import java.util.List;

import org.bukkit.Bukkit;

import me.Qball.Wild.Wild;

public class OldFormatConverter {
	public static Wild wild = Wild.getInstance();
	public static void convert()
	{
		if(!wild.getConfig().getBoolean("Converted"))
		{
			if(wild.getConfig().getStringList("Worlds")==null) {
				Bukkit.getLogger().info("Already Converted");
				wild.getConfig().set("Converted", true);
				wild.saveConfig();
				return;
			}
				List<String> worlds = wild.getConfig().getStringList("Worlds");
				for (String w : worlds) {
					String[] world = w.split(":");
					String worldName = world[0];
					int minX = Integer.parseInt(world[1]);
					int maxX = Integer.parseInt(world[2]);
					int minZ = Integer.parseInt(world[3]);
					int maxZ = Integer.parseInt(world[4]);
					WorldInfo info = new WorldInfo();
					info.setWorldName(worldName);
					info.setMinX(worldName, minX);
					info.setMaxX(worldName, maxX);
					info.setMinZ(worldName, minZ);
					info.setMaxZ(worldName, maxZ);
				}
				for (String w : worlds) {
					wild.getConfig().getList("Worlds").remove(w);
				}
				wild.getConfig().set("Converted", true);
				worlds.clear();
				wild.saveConfig();
			
			
		}
		Bukkit.getLogger().info("Already Converted");
	}

}
