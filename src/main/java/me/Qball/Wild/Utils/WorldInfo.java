package me.Qball.Wild.Utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.Qball.Wild.Wild;

public class WorldInfo {
    private Wild wild;

    public WorldInfo(Wild wild) {
        this.wild = wild;
    }

    public WorldInfo() {
        this.wild = Wild.getInstance();
    }

    public String getWorldName(Player p) {
        ConfigurationSection sec = wild.getConfig().getConfigurationSection("Worlds");
        for (String key : sec.getKeys(false)) {
            if (key.equals(p.getWorld().getName()) || replace(key).equals(p.getWorld().getName())) {
                return p.getWorld().getName();
            }
        }
        return "";
    }

    public int getMinX(String world) {
        return wild.getConfig().getInt("Worlds." + replace(world) + ".MinX");
    }

    public int getMaxX(String world) {
        return wild.getConfig().getInt("Worlds." + replace(world) + ".MaxX");
    }

    public int getMinZ(String world) {
        return wild.getConfig().getInt("Worlds." + replace(world) + ".MinZ");
    }

    public int getMaxZ(String world) {
        return wild.getConfig().getInt("Worlds." + replace(world) + ".MaxZ");
    }

    public boolean doInvertY(String world){
        return wild.getConfig().getBoolean("Worlds."+replace(world)+".InvertY",false);
    }


    public void setWorldName(String world) {
        wild.getConfig().createSection("Worlds." + replace(world));
        wild.saveConfig();
    }

    public void setWorldInfo(String value, String world, int val) {
        wild.getConfig().set("Worlds." + replace(world) +"."+ value, val);
        wild.saveConfig();
    }

    public void setMinX(String world, int min) {
        wild.getConfig().set("Worlds." + replace(world) + ".MinX", min);
        wild.saveConfig();
    }

    public void setMaxX(String world, int max) {
        wild.getConfig().set("Worlds." + replace(world) + ".MaxX", max);
        wild.saveConfig();
    }

    public void setMinZ(String world, int min) {
        wild.getConfig().set("Worlds." + replace(world) + ".MinZ", min);
        wild.saveConfig();
    }

    public void setMaxZ(String world, int max) {
        wild.getConfig().set("Worlds." + replace(world) + ".MaxZ", max);
        wild.saveConfig();
    }
    
    private String replace(String str) {
      return str.replace("\\.", "_");   
    }
}
