package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Utils.TeleportTarget;
import me.Qball.Wild.Utils.WildTpBack;


import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;


import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class PlayMoveEvent implements Listener {
	public static ArrayList<UUID> moved = new ArrayList<UUID>();
	public static ArrayList<UUID>dontTele = new ArrayList<>();
	private final Wild plugin;
	public PlayMoveEvent(Wild plugin)
	{
		this.plugin = plugin;
	}
@EventHandler
public void onMove(PlayerMoveEvent e)
{
	if (TeleportTarget.cmdUsed.contains(e.getPlayer().getUniqueId()))
	{
		
		if(e.getTo().getBlockX()==e.getFrom().getBlockX() &&
				e.getTo().getBlockY() == e.getFrom().getBlockY()&&
				e.getTo().getBlockZ() == e.getFrom().getBlockZ())
			return;
		TeleportTarget.cmdUsed.remove(e.getPlayer().getUniqueId());
		if(!moved.contains(e.getPlayer().getUniqueId())){
			moved.add(e.getPlayer().getUniqueId()); 
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("CancelMsg")));
			Wild.cooldownTime.remove(e.getPlayer().getUniqueId());
			Wild.cooldownCheck.remove(e.getPlayer().getUniqueId());
			dontTele.add(e.getPlayer().getUniqueId());
			}	
	}
	
	for(String name : plugin.portals.keySet())
	{
		String portal = plugin.portals.get(name);
		String[] info = portal.split(":");
		String[] max = info[0].split(",");
		String[] min = info[1].split(",");
		Vector maxVec = new Vector(Integer.parseInt(max[0]),Integer.parseInt(max[1]),Integer.parseInt(max[2]));
		Vector minVec = new Vector(Integer.parseInt(min[0]),Integer.parseInt(min[1]),Integer.parseInt(min[2]));
		CuboidRegion region = new CuboidRegion(maxVec,minVec);
		Vector vec = new Vector(e.getTo().getBlockX(),e.getTo().getBlockY(),e.getTo().getBlockZ());
		if(region.contains(vec))
		{
			WildTpBack save = new WildTpBack();
			save.saveLoc(e.getPlayer(),e.getFrom());
			CheckPerms perms = new CheckPerms();
			perms.check(e.getPlayer());
			break;
		}
	}
}
}