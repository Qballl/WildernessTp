package me.Qball.Wild.GUI;

import java.util.ArrayList;
import me.Qball.Wild.Wild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta; 
import org.bukkit.plugin.Plugin;

public class HookClick implements Listener {
	public static Plugin wild = Wild.getInstance();
	public static ArrayList<String> toSet = new ArrayList<String>();

	@EventHandler
	 public static void click(InventoryClickEvent e) {
		 if(e.getInventory().getName().equalsIgnoreCase("Hooks"))
		 {
			 try{
		 e.setCancelled(true);
		 ItemStack item = e.getCurrentItem();
		 ItemMeta meta = item.getItemMeta();
		 String name = meta.getDisplayName().toLowerCase();
		 switch (name)
		 { 
		 case "towny hook":
			 toSet.add("Towny");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked()); 
			 break;
		 case "factions hook":
			 toSet.add("Factions");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "griefprevention hook":
			 toSet.add("GriefPrevention");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case  "worldguard hook": 
			 toSet.add("WorldGuard");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "true":
			 String val = toSet.get(0);
			 toSet.clear();
			 wild.getConfig().set(val, true);
			 wild.saveConfig();
			 Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
			 e.getWhoClicked().closeInventory();
			 break;
		 case "false":
			 val = toSet.get(0);
			 toSet.clear();
			 wild.getConfig().set(val, false);
			 wild.saveConfig();
			 Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
			 e.getWhoClicked().closeInventory();
			 break;
		 default:
			  e.getWhoClicked().closeInventory();
			  MainGui.removeEdit((Player)e.getWhoClicked());
			  break;
	 }	 
		 }
			 catch(NullPointerException | IndexOutOfBoundsException ex)
			 {
				Bukkit.getLogger().info(toSet.toString());
			 }
			 }
}
}
