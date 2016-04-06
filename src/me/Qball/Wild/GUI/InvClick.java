package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvClick implements Listener {
	public static ArrayList<String> toSet = new ArrayList<String>();
	 @EventHandler
	    public void onInventoryClick(InventoryClickEvent e) {
		
		
		 if(e.getInventory().getName().equalsIgnoreCase("wildtp"))
		 {
			   e.setCancelled(true);
		 ItemStack item = e.getCurrentItem();
		ItemMeta meta = item.getItemMeta();
		String name = meta.getDisplayName().toLowerCase();
		 Bukkit.getLogger().info(name);
		 switch (name)
		 {
		 case "close":
			e.getWhoClicked().closeInventory();
			MainGui.removeEdit((Player)e.getWhoClicked());
			break;
		 case "messages":
			 e.getWhoClicked().closeInventory();
			 MessageGui.openMessGui((Player)e.getWhoClicked());
			 break;
		 case "set":
			 e.getWhoClicked().closeInventory();
			 SetGui.OpenSet((Player)e.getWhoClicked());
		 case "add a potion or world":
			 e.getWhoClicked().closeInventory();
			 MainGui.removeEdit((Player)e.getWhoClicked());
		 case "minx":
			 e.getWhoClicked();
			 toSet.add("MinX");
			 break;
		 }
		 }
	  }
}
