package me.Qball.Wild.Utils;

import org.bukkit.plugin.Plugin;

import me.Qball.Wild.Wild;

public class Intializer {
	private final Wild plugin;
	public Intializer(Wild plugin)
	{
		this.plugin = plugin;
	}
	public void intializeAll()
	{
		SavePortals portals = new SavePortals(plugin);
	}
}
