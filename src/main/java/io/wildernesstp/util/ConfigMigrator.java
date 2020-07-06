package io.wildernesstp.util;

import io.wildernesstp.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * MIT License
 * <p>
 * Copyright (c) 2019 Quintin VanBooven
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ConfigMigrator {

    public static void migrate(Main main){
        File file = new File(main.getDataFolder(),"config.yml");
        FileConfiguration oldConfig = YamlConfiguration.loadConfiguration(file);
        file.delete();
        file.renameTo(new File(main.getDataFolder(),"config(old).yml"));
        main.saveDefaultConfig();
        migrateWorlds(main, oldConfig);
        otherStuff(main, oldConfig);
        main.getConfig().set("config-version",400);
        main.saveConfig();


    }

    private static void migrateWorlds(Main main, FileConfiguration oldConfig){
        for(String world : oldConfig.getConfigurationSection("Worlds").getKeys(false)){
            int minX = oldConfig.getInt("Worlds."+world+".MinX");
            int maxX = oldConfig.getInt("Worlds."+world+".MaxX");
            int minZ = oldConfig.getInt("Worlds."+world+".MinZ");
            int maxZ = oldConfig.getInt("Worlds."+world+".MaxZ");
            String worldTo = oldConfig.getString("Worlds."+world+".WorldTo","");
            main.getConfig().set("regions."+world+".minX",minX);
            main.getConfig().set("regions."+world+".maxX",maxX);
            main.getConfig().set("regions."+world+".minZ",minZ);
            main.getConfig().set("regions."+world+".maxZ",maxZ);
            if(!worldTo.equalsIgnoreCase(""))
                main.getConfig().set("regions."+world+".WorldTo",worldTo);
        }
    }

    private static void otherStuff(Main main, FileConfiguration oldConfig){
        main.getConfig().set("cost",oldConfig.getInt("Cost"));
        main.getConfig().set("blacklisted-biomes", oldConfig.get("Blacklisted_Biomes"));
        main.getConfig().set("distance", oldConfig.getInt("Distance"));
    }

}
