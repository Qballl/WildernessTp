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


import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ClaimChecks {
	private Wild wild = Wild.getInstance();
	private int range = wild.getConfig().getInt("Distance");
	public boolean townyClaim(Location loc) {
		if (wild.getConfig().getBoolean("Towny")) {
			try {
				if (!TownyUniverse.isWilderness(loc.getBlock())&&!checkSurroudningTowns(loc)) 
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
	
	private boolean checkSurroudningTowns(Location loc)
	{
		if(!TownyUniverse.isWilderness(new Location(loc.getWorld(),loc.getX()+range,loc.getY(),loc.getZ()).getBlock()))
			return true;
		else if(!TownyUniverse.isWilderness(new Location(loc.getWorld(),loc.getX()-range,loc.getY(),loc.getZ()).getBlock()))
			return true;
		else if(!TownyUniverse.isWilderness(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()+range).getBlock()))
			return true;
		if(!TownyUniverse.isWilderness(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()-range).getBlock()))
			return false;
		return false;
	}
	
	public boolean factionsClaim(Location loc) {

		if (wild.getConfig().getBoolean("Factions")) {
			Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (!faction.isNone()&&!checkSurroundingFactions(loc)) 
			
				return true;
			
			else 
				return false;
			
		}else 
			return false;

	}
	
	private boolean checkSurroundingFactions(Location loc)
	{
		
		if (BoardColl.get().getFactionAt(PS.valueOf(new Location(loc.getWorld(),loc.getX()+ range, loc.getY(),loc.getZ()))).isNone()) 
			return true;
		else if (BoardColl.get().getFactionAt(PS.valueOf(new Location(loc.getWorld(),loc.getX()- range, loc.getY(),loc.getZ()))).isNone()) 
			return true;
		else if (BoardColl.get().getFactionAt(PS.valueOf(new Location(loc.getWorld(),loc.getX(), loc.getY(),loc.getZ()+range))).isNone()) 
			return true;
		else if (BoardColl.get().getFactionAt(PS.valueOf(new Location(loc.getWorld(),loc.getX()+ range, loc.getY(),loc.getZ()-range))).isNone()) 
			return true;
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean factionsUUIDClaim(Location loc)
	{
		if(wild.getConfig().getBoolean("FactionsUUID"))
		{
			//Long call to insure it calls FactionsUUID method not massivecraft Factions
			com.massivecraft.factions.Faction faction = com.massivecraft.factions.Board.getInstance().getFactionAt(new com.massivecraft.factions.FLocation(loc));
			if(!faction.isNone()&&!checkSurroundingFactionsUUID(loc))
				return true;
			else 
				return false;
		}
		else 
			return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean checkSurroundingFactionsUUID(Location loc)
	{
		Board board = com.massivecraft.factions.Board.getInstance();
		if(board.getFactionAt(new FLocation(new Location(loc.getWorld(),loc.getX()+range,loc.getY(),loc.getZ()))).isNone())
			return true;
		else if(board.getFactionAt(new FLocation(new Location(loc.getWorld(),loc.getX()-range,loc.getY(),loc.getZ()))).isNone())
			return true;
		else if(board.getFactionAt(new FLocation(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()+range))).isNone())
			return true;
		else if(board.getFactionAt(new FLocation(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()-range))).isNone())
			return true;
		return false;
	}

	public boolean greifPrevnClaim(Location loc) {
		if (wild.getConfig().getBoolean("GriefPrevention")) {
			if (GriefPrevention.instance.dataStore.getClaimAt(loc, false, null) != null && checkSurroundingsClaims(loc)) 
				return true;
			 else 
				return false;
			
		} else 
			return false;
		
	}
	
	private boolean checkSurroundingsClaims(Location loc)
	{
		/*
		if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getBlockX()+range, loc.getBlockY(),loc.getBlockZ()), false, null) != null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX()-range,loc.getY(),loc.getZ()), false, null)!=null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()+range), false, null)!=null)
			return true;
		else if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()-range), false, null)!=null)
			return true;
		return false;
		*/
		for(int i = 0; i <= range;i++){
			if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getBlockX()+i, loc.getBlockY(),loc.getBlockZ()), false, null) != null)
				return true;
		}
		for(int i = range; i >=0; i--) {
			if (GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(), loc.getX() - i, loc.getY(), loc.getZ()), false, null) != null)
				return true;
		}
		for (int i =0; i<= range; i++){
			if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()+i), false, null)!=null)
				return true;
		}
		for(int i = range; i >=0; i--){
			if(GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()-i), false, null)!=null)
				return true;
		}
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
					new SimpleChunkLocation(chunk)) != null&&!checkSurroundingKingdoms(loc)) 
				return true;
			 else 
				return false;
			
		}
		return false;
	}
	private boolean checkSurroundingKingdoms(Location loc)
	{
		if(GameManagement.getLandManager().getOrLoadLand(new SimpleChunkLocation(new Location(loc.getWorld(),loc.getX()+range,loc.getY(),loc.getZ()).getChunk()))!=null)
			return true;
		else  if(GameManagement.getLandManager().getOrLoadLand(new SimpleChunkLocation(new Location(loc.getWorld(),loc.getX()-range,loc.getY(),loc.getZ()).getChunk()))!=null)
			return true;
		else if(GameManagement.getLandManager().getOrLoadLand(new SimpleChunkLocation(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()+range).getChunk()))!=null)
			return true;
		else if(GameManagement.getLandManager().getOrLoadLand(new SimpleChunkLocation(new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ()-range).getChunk()))!=null)
			return true;
		return false;
			
	}

}
