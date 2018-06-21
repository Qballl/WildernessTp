package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.Qball.Wild.Wild;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
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

    public Checks(Wild plugin) {
        wild = plugin;
        worlds = wild.getConfig().getStringList("Worlds");
    }

    public boolean getLiquid(Location loc) {
        loc.setY(loc.getBlockY() - 3.5);
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
        if (loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()) == Biome.HELL || target.getWorld().getName().equals("DIM-1")) {
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
        if(target.getWorld().getBiome(target.getLocation().getBlockX(),target.getLocation().getBlockZ()).equals(Biome.HELL))
            return getSolidBlockNether(x,z,target);
        int y = 0;
        if (!wild.getConfig().getBoolean("InvertYSearch"))
            return invertSearch(x,z,target);
        else {
            if(target.getWorld().getBiome(x,z).equals(Biome.HELL))
                return getSolidBlockNether(x,z,target);
            for (int i = 0; i <= target.getWorld().getMaxHeight(); i++) {
                y = i;
                if (!target.getWorld().getBlockAt(x, y, z).isEmpty() && target.getWorld().getBlockAt(x, y + 1, z).isEmpty()
                        && target.getWorld().getBlockAt(x, y + 2, z).isEmpty()
                        && target.getWorld().getBlockAt(x, y + 3, z).isEmpty()
                        && !checkBlocks(target, x, y, z))
                    return y + 4.5;
            }
        }
        return 5;

    }

    private double invertSearch(int x, int z, Player p){
        int y = 0;
        for (int i = p.getWorld().getMaxHeight(); i >= 0; i--) {
            y = i;
            if (!p.getWorld().getBlockAt(x, y, z).isEmpty()
                    && !checkBlocks(p, x, y, z)) {
                return y + 4.5;
            }
        }
        return 0;
    }


    private boolean checkBlocks(Player p, int x, int y, int z) {
        return p.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES) &&
                p.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES_2)&&
                !p.getWorld().getBlockAt(x,y,z).isLiquid();
    }

    public double getSolidBlock(int x, int z, String w, Player p) {
        int y = 0;
        World world = Bukkit.getWorld(w);
        if (world.getBiome(x, z).equals(Biome.HELL)) {
           return getSolidBlockNether(x,z,p);
        } else {
            for (int i = world.getMaxHeight(); i >= 0; i--) {
                y = i;
                if (!world.getBlockAt(x, y, z).isEmpty()) {
                    return y+ 4.5;
                }
            }
        }
        return 5;
    }

    public double getSoildBlock(int x, int z, String w, Player p) {
        return getSolidBlock(x, z, w, p);
    }
    private double getSolidBlockNether(int x, int z, Player p) {
        if(wild.getConfig().getBoolean("InvertYSearch"))
            return getSolidBlockNetherInverted(x,z,p);
        for (int y = 124; y > 2; y--) {
            if(p.getWorld().getBlockAt(x,y,z).isEmpty()){
                if(p.getWorld().getBlockAt(x,y-1,z).isEmpty() &&
                        !p.getWorld().getBlockAt(x,y-2,z).isEmpty()&&
                        !p.getWorld().getBlockAt(x,y-2,z).isLiquid()){
                    return y-1.5;
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
                    return y + 3.5;                }
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

    public boolean blacklistBiome(Location loc) {
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
    public boolean checkBiome(Location loc, Player p, int x, int z){
        if(wild.biome.containsKey(p.getUniqueId()))
            return loc.getWorld().getBiome(x,z) == wild.biome.get(p.getUniqueId());
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