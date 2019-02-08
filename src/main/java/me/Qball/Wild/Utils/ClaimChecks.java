package me.Qball.Wild.Utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.jcdesimp.landlord.persistantData.OwnedLand;
import com.songoda.kingdoms.constants.land.SimpleChunkLocation;
import com.songoda.kingdoms.main.Kingdoms;
import com.songoda.kingdoms.manager.game.GameManagement;
import me.Qball.Wild.Wild;
import me.ryanhamshire.GriefPrevention.GriefPrevention;



import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.codemc.worldguardwrapper.WorldGuardWrapper;


import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import us.forseth11.feudal.core.Feudal;

public class ClaimChecks {
    private Wild wild = Wild.getInstance();
    private int range = wild.getConfig().getInt("Distance");

    public boolean townyClaim(Location loc) {
        if (wild.getConfig().getBoolean("Towny")) {
            try {
                if (!TownyUniverse.isWilderness(loc.getBlock()) && !checkSurroundingTowns(loc))
                    return true;
                else
                    return false;
            } catch (NullPointerException e) {
                wild.getLogger().info(loc.toString());
            }
        } else
            return false;
        return false;
    }

    private boolean checkSurroundingTowns(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                Block block = new Location(loc.getWorld(), x, loc.getWorld().getHighestBlockYAt(x, z), z).getBlock();
                if (!TownyUniverse.isWilderness(block))
                    return true;
            }
        }
        return false;
    }

    public boolean factionsClaim(Location loc) {

        if (wild.getConfig().getBoolean("Factions")) {
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
            if (!faction.isNone() && !checkSurroundingFactions(loc))

                return true;

            else
                return false;

        } else
            return false;

    }

    private boolean checkSurroundingFactions(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (BoardColl.get().getFactionAt(PS.valueOf(new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ() + z))).isNone())
                    return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean factionsUUIDClaim(Location loc) {
        if (wild.getConfig().getBoolean("FactionsUUID")) {
            //Long call to insure it calls FactionsUUID method not massivecraft Factions
            com.massivecraft.factions.Faction faction = com.massivecraft.factions.Board.getInstance().getFactionAt(new com.massivecraft.factions.FLocation(loc));
            if (!faction.isNone() && !checkSurroundingFactionsUUID(loc))
                return true;
            else
                return false;
        } else
            return false;
    }

    @SuppressWarnings("deprecation")
    private boolean checkSurroundingFactionsUUID(Location loc) {
        Board board = com.massivecraft.factions.Board.getInstance();
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (board.getFactionAt(new FLocation(new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ() + z))).isNone())
                    return true;
            }
        }
        return false;
    }

    public boolean legacyFactionsClaim(Location loc){
        //WHY DO THEY REFACTOR THE PACKAGE
        //Long call to get the board of legacy factions
        if(wild.getConfig().getBoolean("LegacyFactions")){
            net.redstoneore.legacyfactions.entity.Board board = net.redstoneore.legacyfactions.entity.Board.get();
            net.redstoneore.legacyfactions.entity.Faction faction = board.getFactionAt(new net.redstoneore.legacyfactions.FLocation(loc));
            if(faction.isWilderness()&&!checkSurroundingLegacyFactions(loc))
                return true;
            else
                return false;
        }
        return false;
    }
    private boolean checkSurroundingLegacyFactions(Location loc) {
        net.redstoneore.legacyfactions.entity.Board board = net.redstoneore.legacyfactions.entity.Board.get();        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (board.getFactionAt(new net.redstoneore.legacyfactions.FLocation(new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ() + z))).isWilderness())
                    return true;
            }
        }
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

    private boolean checkSurroundingsClaims(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (GriefPrevention.instance.dataStore.getClaimAt(new Location(loc.getWorld(), x, loc.getWorld().getHighestBlockYAt(x, z), z), false, null) != null)
                    return true;
            }
        }

        return false;
    }

    public boolean worldGuardClaim(Location loc) {
        if (wild.getConfig().getBoolean("WorldGuard"))
            return !WorldGuardWrapper.getInstance().getRegions(loc).isEmpty() && !checkSurroundingWGClaims(loc);
        return false;
    }

    private boolean checkSurroundingWGClaims(Location loc){
        if(wild.getConfig().getBoolean("WorldGuard")){
                int distance = range / 2;
                Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
                Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
                for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
                    for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                        loc.setX(x);
                        loc.setY(Bukkit.getWorld(loc.getWorld().getName()).getHighestBlockYAt(x, z));
                        loc.setZ(z);
                        if (!WorldGuardWrapper.getInstance().getRegions(loc).isEmpty())
                            return true;
                    }
                }
            }
        return false;
    }


    public boolean kingdomClaimCheck(Location loc) {
        Chunk chunk = loc.getChunk();

        if (wild.getConfig().getBoolean("Kingdoms")) {
            Kingdoms.getManagers();
            if (GameManagement.getLandManager().getOrLoadLand(
                    new SimpleChunkLocation(chunk)) != null && !checkSurroundingKingdoms(loc))
                return true;
            else
                return false;

        }
        return false;
    }

    private boolean checkSurroundingKingdoms(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (GameManagement.getLandManager().getOrLoadLand(new SimpleChunkLocation(new Location(loc.getWorld(), loc.getX() + x, loc.getY(), loc.getZ() + z).getChunk())) != null)
                    return true;
            }
        }
        return false;

    }

    public boolean residenceClaimCheck(Location loc){
        if(wild.getConfig().getBoolean("Residence")) {
            ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
            if (res != null && !checkSurroundingResidences(loc))
                return true;
            else
                return false;
        }
        else{
            return false;
        }
    }

    private boolean checkSurroundingResidences(Location loc){
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                loc = new Location(loc.getWorld(), loc.getBlockX()+x, loc.getBlockY(), loc.getBlockZ()+z,loc.getPitch(),loc.getYaw());
                ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
                if(res != null)
                    return true;
            }
            }
            return false;
    }

    public boolean landLordClaimCheck(Location loc){
        if(wild.getConfig().getBoolean("LandLord")){
            OwnedLand land = OwnedLand.getApplicableLand(loc);
            if(land != null && !checkSurroundingLandClaims(loc)) {
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    private boolean checkSurroundingLandClaims(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                loc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z, loc.getPitch(), loc.getYaw());
                OwnedLand land = OwnedLand.getApplicableLand(loc);
                if(land != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean feudalClaimCheck(Location loc){
        if(wild.getConfig().getBoolean("Feudal")){
            if(Feudal.getAPI().getKingdom(loc) ==null && !checkSurroundingFeudalKingdoms(loc)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    private boolean checkSurroundingFeudalKingdoms(Location loc) {
        int distance = range / 2;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                loc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z, loc.getPitch(), loc.getYaw());
                if(Feudal.getAPI().getKingdom(loc) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
