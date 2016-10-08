package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.kingdoms.constants.land.SimpleChunkLocation;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.manager.game.GameManagement;


import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ClaimChecks {
	public Wild wild = Wild.getInstance();
	public boolean townyClaim(Location loc) {
		if (wild.getConfig().getBoolean("Towny")) {
			try {
				if (!TownyUniverse.isWilderness(loc.getBlock())) 
					return true;
				else
					return false;
			} catch (NullPointerException e) {
				Bukkit.getLogger().info(loc.toString());
			}
		} else 
			return false;
		return false;
	}
	
	public boolean factionsClaim(Location loc) {

		if (wild.getConfig().getBoolean("Factions")) {
			Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (!faction.isNone()) 
			
				return true;
			
			else 
				return false;
			
		}else 
			return false;

	}
	
	@SuppressWarnings("deprecation")
	public boolean 	factionsUUIDClaim(Location loc)
	{
		if(wild.getConfig().getBoolean("FactionsUUID"))
		{
			//Long call to insure it calls FactionsUUID method not massivecraft Factions
			com.massivecraft.factions.Faction faction = com.massivecraft.factions.Board.getInstance().getFactionAt(new com.massivecraft.factions.FLocation(loc));
			if(!faction.isNone())
				return true;
			else 
				return false;
		}
		else 
			return false;
	}

	public boolean greifPrevnClaim(Location loc) {
		if (wild.getConfig().getBoolean("GriefPrevention")) {
			if (GriefPrevention.instance.dataStore.getClaimAt(loc, false, null) != null && checkSurroundings(loc)) 
				return true;
			 else 
				return false;
			
		} else 
			return false;
		
	}
	
	public boolean checkSurroundings(Location loc)
	{
		loc.setX(loc.getX()+20);
		if(GriefPrevention.instance.dataStore.getClaimAt(loc, false, null) != null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX()-30,loc.getY(),loc.getZ()), false, null)!=null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX()-20,loc.getY(),loc.getZ()+20), false, null)!=null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX()-20,loc.getY(),loc.getZ()-20), false, null)!=null)
			return true;
		return false;
	}

	public boolean worldGuardClaim(Location loc) {
		if (wild.getConfig().getBoolean("WorldGuard")) {
			WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer()
					.getPluginManager().getPlugin("WorldGuard");
			RegionContainer container = wg.getRegionContainer();
			RegionManager regions = container.get(loc.getWorld());
			
			ApplicableRegionSet set = regions.getApplicableRegions(loc);
			
			if (!set.getRegions().isEmpty()) 
				return true;
			 else 
				return false;
			
		} else 
			return false;
		
	}

	public boolean kingdomClaimCheck(Location loc) {
		Chunk chunk = loc.getChunk();

		if (wild.getConfig().getBoolean("Kingdoms")) {
			Kingdoms.getManagers();
			if (GameManagement.getLandManager().getOrLoadLand(
					new SimpleChunkLocation(chunk)) != null) 
				return true;
			 else 
				return false;
			
		}
		return false;
	}

}
