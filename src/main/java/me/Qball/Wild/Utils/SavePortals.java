package me.Qball.Wild.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.Qball.Wild.Wild;

public class SavePortals {
    private final File file;
    private final Wild plugin;
    public FileConfiguration portals;

    public SavePortals(Wild plugin) {
        this.plugin = plugin;
        file = new File(this.plugin.getDataFolder() + "/Portals.yml");
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                portals = YamlConfiguration.loadConfiguration(file);
                portals.createSection("Portals");
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            portals = YamlConfiguration.loadConfiguration(file);
            fillMap();
        }
    }

    private void fillMap() {
        ConfigurationSection sec = portals.getConfigurationSection("Portals");
        if(sec == null){
            plugin.getLogger().info("Portals file is empty probably not a problem");
            return;
        }
        for (String name : sec.getKeys(false)) {
            if (name == null) {
                plugin.getLogger().info("Portals file is empty probably not a problem if it is report this");
                return;
            }
            String loc = portals.getString("Portals." + name);
            plugin.portals.put(name, loc);
        }
    }

    public void saveMap() {
        portals = YamlConfiguration.loadConfiguration(file);
        for (String name : plugin.portals.keySet()) {
            portals.set("Portals." + name, plugin.portals.get(name));
        }
        for (String name : portals.getConfigurationSection("Portals").getKeys(false)) {
            if (!plugin.portals.containsKey(name))
                portals.set("Portals." + name, null);
        }
        saveFile();
    }

    private void saveFile() {
        try {
            portals.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
