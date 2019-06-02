package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.Qball.Wild.Wild;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public class Checks {
    public static boolean inNether;
    public static boolean world;
    public static boolean blacklist = false;
    static List<String> worlds;
    private final Wild wild;
    private String nether;

    public Checks(Wild plugin) {
        wild = plugin;
        worlds = wild.getConfig().getStringList("Worlds");
        if(!wild.thirteen)
            nether = "HELL";
        else
            nether = "NETHER";
    }

    public boolean getLiquid(Location loc) {
        loc.setY(loc.getBlockY() - 5);
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        if (loc.getWorld().getBlockAt(loc).isLiquid()
                || loc.getWorld().getBiome(x, z).equals(Biome.OCEAN)
                || loc.getWorld().getBiome(x, z).equals(Biome.DEEP_OCEAN))
            return true;
        else
            return false;
    }

    public boolean inNether(Location loc, Player target) {
        if (loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()) == Biome.valueOf(nether) || target.getWorld().getName().equals("DIM-1")) {
            inNether = true;
        } else {
            inNether = false;
        }
        return inNether;
    }


    public void isLoaded(int x, int z, Player target) {
        if (!target.getWorld().isChunkLoaded(x, z))
            target.getWorld().getChunkAt(x, z).load();
    }

    public double getSolidBlock(int x, int z, Player target) {
        if(target.getWorld().getBiome(target.getLocation().getBlockX(),target.getLocation().getBlockZ()).equals(Biome.valueOf(nether)))
            return getSolidBlockNether(x,z,target);
        if (wild.getConfig().getBoolean("InvertYSearch"))
            return invertSearch(x,z,target);
        else {
            if(target.getWorld().getBiome(x,z).equals(Biome.valueOf(nether)))
                return getSolidBlockNether(x,z,target);
            else {
                /*for (int i = target.getWorld().getMaxHeight(); i >=0; i--) {
                    if (!target.getWorld().getBlockAt(x, i, z).isEmpty() && target.getWorld().getBlockAt(x, i + 1, z).isEmpty()
                            && target.getWorld().getBlockAt(x, i + 2, z).isEmpty()
                            && target.getWorld().getBlockAt(x, i + 3, z).isEmpty()
                            && !checkBlocks(target, x, i, z)) {
                        target.sendMessage(i+4.5+"");
                        return i + 4.5;
                    }
                }*/
                return (target.getWorld().getHighestBlockAt(x,z).getY()-1)+5;
            }

        }

    }

    private double invertSearch(int x, int z, Player p){
        for (int i = 0; i <= p.getWorld().getMaxHeight(); i++) {
            if (p.getWorld().getBlockAt(x, i, z).isEmpty()
                    && !checkBlocks(p, x, i, z)) {
                if(p.getWorld().getBlockAt(x,i+1,z).isEmpty()&&
                    p.getWorld().getBlockAt(x,i+2,z).isEmpty())
                    return i + 5;
            }
        }
        return 0;
    }


    //Returns true if there is a liquid or levaes at a block.
    private boolean checkBlocks(Player p, int x, int y, int z) {
        return p.getWorld().getBlockAt(x, y, z).getType().toString().contains("LEAVES")||
                p.getWorld().getBlockAt(x,y,z).isLiquid();
    }

    public double getSolidBlock(int x, int z, String w, Player p) {
        int y = 0;
        World world = Bukkit.getWorld(w);
        if (world.getBiome(x, z).equals(Biome.valueOf(nether))) {
           return getSolidBlockNether(x,z,p);
        } else {
            if(wild.getConfig().getBoolean("InvertYSearch"))
                return invertSearch(x,z,w);
            /*for (int i = world.getMaxHeight(); i >= 0; i--) {
                y = i;
                if (!world.getBlockAt(x, y, z).isEmpty()) {
                    return y+ 4.5;
                }
            }*/
            return Bukkit.getWorld(w).getHighestBlockAt(x,z).getRelative(BlockFace.DOWN).getY()+5;
        }
    }

    private double invertSearch(int x, int z, String world){
        for (int i = 0; i <= Bukkit.getWorld(world).getMaxHeight(); i++) {
            if (!Bukkit.getWorld(world).getBlockAt(x, i, z).isEmpty()
                    && !checkBlocks(world, x, i, z)) {
                return i + 5;
            }
        }
        return 0;
    }

    private boolean checkBlocks(String world, int x, int y, int z) {
        return Bukkit.getWorld(world).getBlockAt(x, y, z).getType().toString().contains("LEAVES")&&
                !Bukkit.getWorld(world).getBlockAt(x,y,z).isLiquid();
    }

    private double getSolidBlockNether(int x, int z, Player p) {
        if(wild.getConfig().getBoolean("InvertYSearch"))
            return getSolidBlockNetherInverted(x,z,p);
        for (int y = 124; y > 2; y--) {
            if(p.getWorld().getBlockAt(x,y,z).isEmpty()){
                if(p.getWorld().getBlockAt(x,y-1,z).isEmpty() &&
                        !p.getWorld().getBlockAt(x,y-2,z).isEmpty()&&
                        !p.getWorld().getBlockAt(x,y-2,z).isLiquid()){
                    return y-2;
                }
            }
        }
        return 10;
    }

    private double getSolidBlockNetherInverted(int x, int z, Player p){
        for(int y = 0; y <=124; y++) {
            if (p.getWorld().getBlockAt(x, y, x).isEmpty()) {
                Location loc = new Location(p.getWorld(), x, y, z, 0.0F, 0.0F);
                if (p.getWorld().getBlockAt(x, loc.getBlockY() + 2, z).isEmpty()
                        && !p.getWorld().getBlockAt(x, loc.getBlockY() + 4, z).isEmpty()
                        && !p.getWorld().getBlockAt(x, loc.getBlockY() + 4, z).isLiquid()) {
                    return y + 4;                }
            }
        }
        return 123;
    }

    public boolean world(Player p) {
        ArrayList<String> allWorlds = new ArrayList<>();
        ConfigurationSection sec = wild.getConfig().getConfigurationSection("Worlds");
        if(sec == null)
            return false;
        for (String key : sec.getKeys(false)) {
            allWorlds.add(key);
        }
        if (allWorlds.contains(p.getLocation().getWorld().getName())) {
            world = true;
        } else {
            world = false;
        }
        allWorlds.clear();
        return world;
    }

    public boolean isBlacklistedBiome(Location loc) {
        List<String> biomes = wild.getConfig().getStringList("Blacklisted_Biomes");
        if (biomes.size() == 0) {
            return false;
        } else {
            for (String biome : biomes) {
                biome = biome.toUpperCase();
                if (loc.getBlock().getBiome() == Biome.valueOf(biome)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkBiome(Location loc, Player p){
        if(wild.biome.containsKey(p.getUniqueId()))
            return loc.getWorld().getBiome(loc.getBlockX(),loc.getBlockZ()) == wild.biome.get(p.getUniqueId());
        else
            return true;
    }

    public boolean isVillage(Location loc,Player p){
        if(!wild.village.contains(p.getUniqueId()))
            return true;
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc,200,50,200);
        for(Entity entity : entities){
            if(entity.getType().equals(EntityType.VILLAGER))
                return true;
        }
        return false;
    }

    public boolean checkLocation(Location loc, Player p){
        LocationsFile locationsFile = new LocationsFile(wild);
        for(String location : locationsFile.getLocations()){
            String[] info = location.split(".");
            if(p.getWorld().getChunkAt(Integer.parseInt(info[0]),Integer.parseInt(info[2]))==loc.getChunk())
                return true;
        }
        return false;
    }
}