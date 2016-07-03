package me.Qball.Wild.Utils;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import me.Qball.Wild.Wild;

public class GetRandomLocation {
	public static Wild wild = Wild.getInstance();
	public void getWorldInfo(Player p)
	{
		String world ="";
		int minX = 0;
		int maxX = 0;
		int minZ = 0;
		int maxZ = 0;
		for(int i = 0; i<= wild.getConfig().getList("Worlds").size();i++)
		{
			String worldInfo = (String) wild.getConfig().getList("Worlds").get(i);
			String[] worlds = worldInfo.split(":");
			world = worlds[0];
			if (world.equalsIgnoreCase(p.getWorld().toString()))
			{
				minX = Integer.parseInt(worlds[1]);
				maxX = Integer.parseInt(worlds[2]);
				minZ = Integer.parseInt(worlds[3]);
				maxZ = Integer.parseInt(worlds[4]);
				World w = Bukkit.getWorld(world);
				getRandomLoc(p,w,maxX,minX,maxZ,minZ);
				break;
			}
			if(i == wild.getConfig().getList("Worlds").size())
			{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("WorldMsg")));
			}
		}
	
	}
	public void getRandomLoc(Player p,World w,int maxX,int minX,int maxZ,int minZ)
	{
		Random rand = new Random();
		int x = rand.nextInt(maxX - minX + 1) + minX;
		int z = rand.nextInt(maxZ - minZ + 1) + minZ;
		int y = 0;
		if(p.getWorld().getBiome(x, z) != Biome.HELL)
		{
			y = Checks.getSoildBlock(x, z, p);
		}
		else
		{
			y = GetHighestNether.getSoildBlock(x, z, p);
		}
		Location loc = new Location(w,x,y,z,0.0F,0.0F);
		wild.Random(p, loc);
	}

}
