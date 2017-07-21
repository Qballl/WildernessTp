package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by pi on 18/07/17.
 */
public class UsersFile {

    private Wild wild;
    private File file;
    private YamlConfiguration users;

    public UsersFile(Wild wild){
        this.wild = wild;
        createFile();
    }

    public void createFile(){
        file = new File(wild.getDataFolder()+"/Users.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
                users = YamlConfiguration.loadConfiguration(file);
                users.createSection("Users");
                save();
            }catch (IOException e){
                users = YamlConfiguration.loadConfiguration(file);
            }
        }
    }

    public YamlConfiguration getUsers() {
        file = new File(wild.getDataFolder()+"/Users.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void addUse(UUID uuid){
        int uses = users.getInt("Users."+uuid,0);
        users.set("Users."+uuid,uses+1);
        save();
    }

    public int getUses(UUID uuid){
       return getUsers().getInt("Users."+uuid,0);
    }

    private void save(){
        try{
            users.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
