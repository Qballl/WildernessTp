package io.wildernesstp.util;

import io.wildernesstp.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

public class LimitManager {

    private File file;
    private YamlConfiguration users;
    private Main main;

    public LimitManager(Main main){
        this.main = main;
        createFile();
    }

    public void createFile(){
        file = new File(main.getDataFolder()+File.separator+"Users.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
                users = YamlConfiguration.loadConfiguration(file);
                users.createSection("Users");
                save(users);
            }catch (IOException e){
                users = YamlConfiguration.loadConfiguration(file);
            }
        }
    }

    public YamlConfiguration getUsers() {
        file = new File(main.getDataFolder()+ File.pathSeparator+"Users.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void addUse(UUID uuid){
        int uses = getUsers().getInt("Users."+uuid,0);
        YamlConfiguration users = getUsers();
        users.set("Users."+uuid,uses+1);
        save(users);
    }

    public int getUses(UUID uuid){
        return getUsers().getInt("Users."+uuid,0);
    }

    private void save(YamlConfiguration users){
        try{
            users.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public int getLimit(Player p){
        int limit = 0;
        for(String key : main.getConfig().getConfigurationSection("Limits").getKeys(false)) {
            if (p.hasPermission("wildernesstp.limit." + key)) {
                limit = Math.max(main.getConfig().getInt("use_limit." + key), limit);
            }
        }
        return limit;
    }
}
