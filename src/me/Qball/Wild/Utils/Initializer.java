package me.Qball.Wild.Utils;


import me.Qball.Wild.Wild;

public class Initializer {
	private final Wild plugin;
	public Initializer(Wild plugin)
	{
		this.plugin = plugin;
	}
	@SuppressWarnings("unused")
	public void initializeAll()
	{
		SavePortals portals = new SavePortals(plugin);
		TeleportTarget tele = new TeleportTarget(plugin);
		GetRandomLocation random = new GetRandomLocation(plugin);
		Checks check = new Checks(plugin);
		CheckPerms perms = new CheckPerms(plugin);
	}
}
