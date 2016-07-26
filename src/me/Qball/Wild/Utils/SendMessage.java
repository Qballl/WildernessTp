package me.Qball.Wild.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendMessage {
	public static void send(Player p)
	{
		p.sendMessage(ChatColor.GREEN + "Now just type in chat to set the desired value");
	}
}
