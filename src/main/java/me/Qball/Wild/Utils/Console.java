package me.Qball.Wild.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Console {

    public static void send(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
