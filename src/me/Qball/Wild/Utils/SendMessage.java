package me.Qball.Wild.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class SendMessage {
    public static void send(Player p) {
        p.sendMessage(ChatColor.GREEN + "Now just type in chat to set the desired value");
    }

    public static void sendInfo(HumanEntity e) {
        Player p = (Player) e;
        p.sendMessage(ChatColor.GREEN + "Now type the name of the world space the value so for example world 0");
    }
}
