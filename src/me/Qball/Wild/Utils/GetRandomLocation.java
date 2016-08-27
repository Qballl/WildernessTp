package me.Qball.Wild.Utils;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import me.Qball.Wild.Wild;

public class GetRandomLocation {
	public static Wild wild = Wild.getInstance();
	public WorldInfo wInfo = new WorldInfo();
	public void getWorldInfo(Player p)
	{
		
		String w = wInfo.getWorldName(p);
		int minX = wInfo.getMinX(w);
		int maxX = wInfo.getMaxX(w);
		int minZ = wInfo.getMinZ(w);
		int maxZ = wInfo.getMaxZ(w);
		getRandomLoc(p,Bukkit.getWorld(w),maxX,minX,maxZ,minZ);
		/*for(int i = 0; i<= wild.getConfig().getList("Worlds").size()-1;i++)
		{ 
			String worldInfo = (String) wild.getConfig().getList("Worlds").get(i);
			String[] worlds = worldInfo.split(":");
			world = worlds[0];
			String pWorld = p.getLocation().getWorld().getName();
			if (world.equals(pWorld))
			{ 
				try{
				minX = Integer.parseInt(worlds[1]);
				maxX = Integer.parseInt(worlds[2]);
				minZ = Integer.parseInt(worlds[3]);
				maxZ = Integer.parseInt(worlds[4]);
				World w = Bukkit.getWorld(world);
				getRandomLoc(p,w,maxX,minX,maxZ,minZ);
				break;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					p.sendMessage(ChatColor.RED+"Please report this to an admin");
					Bukkit.getServer().getLogger().info("Config is misconfigured: " + worldInfo);
					e.printStackTrace();
				}
			}
		
		}*/
	
	
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
		wild.random(p, loc);
	}
	public String getWorldInfomation(Player p)
	{
		String world =wInfo.getWorldName(p);
		String minX = String.valueOf(wInfo.getMinX(world));
		String maxX = String.valueOf(wInfo.getMaxX(world));
		String minZ = String.valueOf(wInfo.getMinZ(world));
		String maxZ = String.valueOf(wInfo.getMaxZ(world));
		String info = world+":"+minX+":"+maxX+":"+minZ+":"+maxZ;;
		return info;
	}
	public Location getRandomLoc(String info, Player p)
	{
		Random rand = new Random();
		String[] worldInfo = info.split(":");
		World w = Bukkit.getWorld(worldInfo[0]);
		int minX = Integer.parseInt(worldInfo[1]);
		int maxX = Integer.parseInt(worldInfo[2]);
		int minZ = Integer.parseInt(worldInfo[3]);
		int maxZ = Integer.parseInt(worldInfo[4]);
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
		return loc;
	}
	public void recallTeleport(Location loc, Player p)
	{
		TeleportTar tele = new TeleportTar();
		tele.TP(loc, p);
	}
	

}
