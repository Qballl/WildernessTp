package me.qball.wild.util

import com.massivecraft.factions.entity.BoardColl
import com.massivecraft.massivecore.ps.PS
import com.palmergames.bukkit.towny.`object`.TownyUniverse
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import me.qball.wild.Wild
import me.ryanhamshire.GriefPrevention.GriefPrevention
import org.bukkit.Location
import org.kingdoms.constants.land.SimpleChunkLocation
import org.kingdoms.main.Kingdoms
import org.kingdoms.manager.game.GameManagement

object ClaimChecks {
    private val plugin = Wild.instance

    fun townyClaim(loc: Location) : Boolean {
        if(plugin.config.getBoolean("Towny")) {
            return TownyUniverse.isWilderness(loc.block)
        }
        return false
    }

    fun factionClaim(loc: Location) : Boolean {
        if(plugin.config.getBoolean("Factions")) {
            val fac = BoardColl.get().getFactionAt(PS.valueOf(loc))
            return fac.isNone
        }
        return false
    }

    fun griefPreventionClaimed(loc: Location) : Boolean {
        if(plugin.config.getBoolean("GriefPrevention")) {
            return GriefPrevention.instance.dataStore.getClaimAt(loc, false, null) != null
        }
        return false
    }

    fun isInWorldGuardRegion(loc: Location) : Boolean {
        if(plugin.config.getBoolean("WorldGuard")) {
            val wg = plugin.server.pluginManager.getPlugin("WorldGuard") as WorldGuardPlugin
            val man = wg.regionContainer.get(loc.world) ?: return false

            return man.getApplicableRegions(loc) != null
        }
        return false
    }


    fun kingdomsClaim(loc: Location) : Boolean {
        if(plugin.config.getBoolean("Kingdoms")) {
            Kingdoms.getManagers()
            return GameManagement.getLandManager().getOrLoadLand(SimpleChunkLocation(loc.chunk)) != null
        }
        return false
    }
}


