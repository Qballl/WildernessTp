package me.Qball.Wild.Utils;

import org.bukkit.Bukkit;

import me.Qball.Wild.Wild;

public class LoadDependencies {
    public static Wild wild = Wild.getInstance();

    public static void loadAll() {
        LoadDependencies load = new LoadDependencies();
        load.loadTowny();
        load.loadFactions();
        load.loadGriefPreven();
        load.loadWorldGuard();
        load.loadKingdoms();
        load.loadResidence();
        load.loadLandLord();
        load.loadLegacyFactions();
    }

    private void loadTowny() {
        if (wild.getConfig().getBoolean("Towny")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Towny") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("Towny hook enabled");
            }
        }
    }

    private void loadFactions() {
        if (wild.getConfig().getBoolean("Factions")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Factions") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("Factions hook enabled");
            }
        }
    }

    private void loadLegacyFactions() {
        if (wild.getConfig().getBoolean("Factions")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("LegacyFactions") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("LegacyFactions hook enabled");
            }
        }
    }
    private void loadGriefPreven() {
        if (wild.getConfig().getBoolean("GriefPrevention")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("GriefPrevention hook enabled");
            }
        }
    }

    private void loadWorldGuard() {
        if (wild.getConfig().getBoolean("WorldGuard")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("WorldGuard hook enabled");
            }
        }
    }

    private void loadKingdoms() {
        if (wild.getConfig().getBoolean("Kingdoms")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Kingdoms") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            } else {
                Bukkit.getLogger().info("Kingdoms hook enabled");
            }
        }
    }
    private void loadResidence(){
        if(wild.getConfig().getBoolean("Residence")){
            if(Bukkit.getServer().getPluginManager().getPlugin("Residence")==null){
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            }else {
                Bukkit.getLogger().info("Residence hook enabled");
            }
        }
    }

    private void loadLandLord(){
        if(wild.getConfig().getBoolean("LandLord")){
            if(Bukkit.getServer().getPluginManager().getPlugin("LandLord")==null){
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                Bukkit.getLogger().info("Plugin will disable due to missing dependency");
            }else{
                Bukkit.getLogger().info("LandLord hook enabled");
            }
        }
    }
}
