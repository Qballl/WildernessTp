package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ClaimChecks {
	public Wild wild = Wild.getInstance();
	public boolean towny;
	public boolean factions;
	public boolean griefPreven;
	public boolean worldGuard;
	public boolean townyClaim(Location loc) {
		if (wild.getConfig().getBoolean("Towny")) {
			if (!TownyUniverse.isWilderness(loc.getBlock())) {
				towny = true;
			} else {
				towny = false;
			}
		}
		return towny;
	}

	public boolean factionsClaim(Location loc) {

		if (wild.getConfig().getBoolean("Factions")) {
			Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (!faction.isNone()) {
				factions = true;

			} else {
				factions = false;
			}
		}
		return factions;

	}
	public boolean greifPrevnClaim(Location loc)
	{
		if(wild.getConfig().getBoolean("GriefPrevention"))
	  	{
	  		if(GriefPrevention.instance.dataStore.getClaimAt(loc, false, null)!=null)
	  		{
	  			griefPreven = true;
	  		
	  		}
	  		else
	  		{
	  			griefPreven = false;
	  		}
	  	}
		return griefPreven;
	}
	public boolean worldGuardClaim(Location loc)
	{
		if (wild.getConfig().getBoolean("WorldGuard"))
	  	{
	  		WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	  	
	  		RegionContainer container = wg.getRegionContainer();
	  		RegionManager regions = container.get(loc.getWorld());
	  		// Check to make sure that "regions" is not null
	  		ApplicableRegionSet set = regions.getApplicableRegions(BukkitUtil.toVector(loc));
	  		if(set!=null)
	  		{
	  			worldGuard = true;
	  		}
	  		else
	  		{
	  			worldGuard = false;
	  		}
	  	}
		return worldGuard;

	}
}
