package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import org.bukkit.Location;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ClaimChecks {
	public Wild wild = Wild.getInstance();
	public boolean towny;
	public boolean factions;
	public boolean townyClaim(Location loc)
	{
		 if(wild.getConfig().getBoolean("Towny"))
		 {
		 if(!TownyUniverse.isWilderness(loc.getBlock()))
		 {
			 towny = true;
		 }
		 else
		 {
			 towny = false;
		 }
	  	}
		 return towny;
		 }
	public boolean factionsClaim(Location loc)
	{

	  	 if (wild.getConfig().getBoolean("Factions"))
	  	{
	  		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
	  		if(!faction.isNone())
	  		{
	  			factions = true;
	  			
	  		}
	  		else
	  		{
	  			factions = false;
	  		}
	  	}
	  	 return factions;
	
	}
}
