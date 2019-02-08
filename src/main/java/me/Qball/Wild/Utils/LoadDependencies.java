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
        load.loadFactionsUUID();
        load.loadFeudal();
    }

    private void loadTowny() {
        if (wild.getConfig().getBoolean("Towny")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Towny") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency Towny");
            } else {
                wild.getLogger().info("Towny hook enabled");
            }
        }
    }

    private void loadFactions() {
        if (wild.getConfig().getBoolean("Factions")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Factions") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency Factions");
            } else {
                wild.getLogger().info("Factions hook enabled");
            }
        }
    }

    private void loadFactionsUUID() {
        if (wild.getConfig().getBoolean("FactionsUUID")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Factions") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency FactionsUUID");
            } else {
                wild.getLogger().info("Factions hook enabled");
            }
        }
    }

    private void loadLegacyFactions() {
        if (wild.getConfig().getBoolean("LegacyFactions")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("LegacyFactions") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency LegacyFactions");
            } else {
                wild.getLogger().info("LegacyFactions hook enabled");
            }
        }
    }
    private void loadGriefPreven() {
        if (wild.getConfig().getBoolean("GriefPrevention")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency GriefPrevention");
            } else {
                wild.getLogger().info("GriefPrevention hook enabled");
            }
        }
    }

    private void loadWorldGuard() {
        if (wild.getConfig().getBoolean("WorldGuard")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency WorldGuard");
            } else {
                wild.getLogger().info("WorldGuard hook enabled");
            }
        }
    }

    private void loadKingdoms() {
        if (wild.getConfig().getBoolean("Kingdoms")) {
            if ((Bukkit.getServer().getPluginManager().getPlugin("FabledKingdoms") == null)) {
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency Kingdoms");
            } else {
                wild.getLogger().info("Kingdoms hook enabled");
            }
        }
    }
    private void loadResidence(){
        if(wild.getConfig().getBoolean("Residence")){
            if(Bukkit.getServer().getPluginManager().getPlugin("Residence")==null){
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency Residence");
            }else {
                wild.getLogger().info("Residence hook enabled");
            }
        }
    }

    private void loadLandLord(){
        if(wild.getConfig().getBoolean("LandLord")){
            if(Bukkit.getServer().getPluginManager().getPlugin("LandLord")==null){
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency LandLord");
            }else{
                wild.getLogger().info("LandLord hook enabled");
            }
        }
    }

    private void loadFeudal(){
        if(wild.getConfig().getBoolean("Feudal")){
            if(Bukkit.getServer().getPluginManager().getPlugin("Feudal")==null){
                Bukkit.getServer().getPluginManager().disablePlugin(wild);
                wild.getLogger().info("Plugin will disable due to missing dependency Feudal");
            }else{
                wild.getLogger().info("Feudal hook enabled");
            }
        }
    }
}
