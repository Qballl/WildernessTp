package me.Qball.Wild.Utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.Qball.Wild.Wild;

public class WorldInfo {
	Wild wild = Wild.getInstance();
	public String getWorldName(Player p){
		ConfigurationSection sec = wild.getConfig().getConfigurationSection("Worlds");
		for(String key : sec.getKeys(false))
		{
			if(key.equals(p.getWorld().getName()))
			{
				return p.getWorld().getName();
			}
		}
		return "";
}
	public int getMinX(String world)
	{
		return wild.getConfig().getInt("Worlds."+ world + ".MinX");
	}
	public int getMaxX(String world)
	{
		return wild.getConfig().getInt("Worlds."+ world + ".MaxX");
	}
	public int getMinZ(String world)
	{
		return wild.getConfig().getInt("Worlds."+ world + ".MinZ");
	}
	public int getMaxZ(String world)
	{
		return wild.getConfig().getInt("Worlds."+ world + ".MaxZ");
	}
	public void setWorldName(String world)
	{
		wild.getConfig().createSection("Worlds."+world);
		wild.saveConfig();
	}
	public void setMinX(String world, int min)
	{
		wild.getConfig().set("Worlds."+world+".MinX", min);
		wild.saveConfig();
	}
	public void setMaxX(String world, int max)
	{
		wild.getConfig().set("Worlds."+world+".MaxX", max);
		wild.saveConfig();
	}
	public void setMinZ(String world, int min)
	{
		wild.getConfig().set("Worlds."+world+".MinZ", min);
		wild.saveConfig();
	}
	public void setMaxZ(String world, int max)
	{
		wild.getConfig().set("Worlds."+world+".MaxZ", max);
		wild.saveConfig();
	}
}
