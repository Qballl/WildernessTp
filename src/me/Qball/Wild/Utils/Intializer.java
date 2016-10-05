package me.Qball.Wild.Utils;


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
		TeleportTarget tele = new TeleportTarget(plugin);
		GetRandomLocation random = new GetRandomLocation(plugin);
		Checks check = new Checks(plugin);
		CheckPerms perms = new CheckPerms(plugin);
	}
}
