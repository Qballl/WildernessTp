package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.List;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Checks{
	public static boolean Water;
	public static boolean inNether;
	public static boolean inEnd;
	public static boolean loaded;
	public static boolean World;
	public static boolean Biomes;
	public static Plugin wild = Wild.getInstance();
	@SuppressWarnings("unchecked")
	static List<String> Worlds = (List<String>)wild.getConfig().getList("Worlds");
	 public static boolean getLiquid(Location loc)
	  {
		 int x = loc.getBlockX();
		 int z = loc.getBlockZ();
		  if (loc.getWorld().getBlockAt(loc).isLiquid()
				  ||loc.getWorld().getBiome(x,z).equals(Biome.OCEAN)
				  ||loc.getWorld().getBiome(x, z).equals(Biome.DEEP_OCEAN))
	      {
	    	  Water = true;
	      }
		  else
		  {
			  Water = false;
		  }
		  return Water;
	  }
	  public static boolean inNether(int tempx,int tempz, Player target)
	  {
		 
		  if (target.getWorld().getBiome(tempx, tempz) == Biome.HELL)
	      {
	    	  inNether = true; 
	      }
		  else
		  {
			  inNether = false;
		  }
		  return inNether;
	  }
	  public static boolean inEnd(int tempx,int tempz, Player target)
	  {
		 
		  if (target.getWorld().getBiome(tempx, tempz) == Biome.SKY)
	      {
	    	  inEnd = true;
	      }
		  else
		  {
			  inEnd = false;
		  }
		  return inEnd;
	  }
	  public static void ChunkLoaded(int tempx, int tempz, Player target)
	  {
		 
		
		if (target.getWorld().isChunkLoaded(tempx,tempz) == true)
		  {
			
		  }
		  else
		  {
			  target.getWorld().getChunkAt(tempx, tempz).load();
		  }
	  }	 
	  public static int getSoildBlock(int x, int z, Player target)
	  {
		
		 target.sendMessage(ChatColor.BLUE + String.valueOf(x)  + " " + String.valueOf(z));
		 int Y = 0;
		  for (int y = 256; y>= 0; y --)
		  {
			 Y = y;
			 if(!target.getWorld().getBlockAt(x, Y, z).isEmpty())
			 {
				Y+=1;
				break;
			 }
		  }
		  target.sendMessage(ChatColor.DARK_BLUE + String.valueOf(Y) 
				  + ChatColor.AQUA + String.valueOf(target.getWorld().getHighestBlockYAt(x, z)+2));
		 return Y;
	  }
  public static boolean World(Player p) 
	  {
		String world ="";
		ArrayList<String> allWorlds = new ArrayList<String>();
		for(int i = 0; i<= wild.getConfig().getList("Worlds").size()-1;i++)
		{
			String worldInfo = (String) wild.getConfig().getList("Worlds").get(i);
			String[] worlds = worldInfo.split(":");
			world = worlds[0];
			allWorlds.add(world);
			
		}
				 if (allWorlds.contains(p.getLocation().getWorld().getName()))
				 {
					 World=true;
					 allWorlds.clear();
				 }
				 else
				 {
					 World = false;
					 allWorlds.clear();
							 
				 }
				 return World;
	  }
	  public static boolean blacklistBiome(Location loc)
	  {
		
		  @SuppressWarnings("unchecked")
		  List<String> biomes = (List<String>)wild.getConfig().getList("Blacklisted_Biomes");
		  if(biomes.size()==0)
		  {
			  Biomes = false;
		  }
		  else
		  {
		  for (int i = 0; i <= biomes.size(); i++)
		  {
			  String biome = biomes.get(i).toString().toUpperCase();
			  if(loc.getBlock().getBiome() == Biome.valueOf(biome))
			  {	
				  Biomes= true;
				  break;
			  
			  } 
			else{
			  		if (i==biomes.size())
			  		{
			  			Biomes=false;
			  		}
			  	}
			  	
			  
		  }
		  }
		return Biomes;
	  }
}
