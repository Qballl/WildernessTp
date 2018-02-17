package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LocationsFile {
    private final File file;
    private YamlConfiguration locations;

    public LocationsFile(Wild wild) {
        file = new File(wild.getDataFolder() + "/Locations.yml");
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                locations = YamlConfiguration.loadConfiguration(file);
                locations.createSection("Locations");
                saveFile(locations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            locations = YamlConfiguration.loadConfiguration(file);

        }
    }

    public void addLocation(Location loc){
        locations = getLocationsFile();
        locations.set("Locations.",loc.getChunk().getX()+","+loc.getChunk().getZ()+","+loc.getWorld().getName());
        saveFile(locations);
    }

    public ArrayList<String> getLocations(){
        locations = getLocationsFile();
        return new ArrayList<>(locations.getConfigurationSection("Locations").getKeys(false));
    }

    private YamlConfiguration getLocationsFile(){
        locations = YamlConfiguration.loadConfiguration(file);
        return locations;
    }

    private void saveFile(YamlConfiguration locations) {
        try {
            locations.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
