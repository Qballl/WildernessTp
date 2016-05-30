package me.Qball.Wild.GUI;

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
			 InvClick.toSet.add("Towny");
			e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked()); 
			 break;
		 case "factions hook":
			 InvClick.toSet.add("Factions");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "griefprevention hook":
			 InvClick.toSet.add("GriefPrevention");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case  "worldguard hook": 
			 InvClick.toSet.add("WorldGuard");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "true":
			 String val = InvClick.toSet.get(0);
			 InvClick.toSet.clear();
			 wild.getConfig().set(val, true);
			 break;
		 case "false":
			 val = InvClick.toSet.get(0);
			 InvClick.toSet.clear();
			 wild.getConfig().set(val, false);
			 break;
		 default:
			  e.getWhoClicked().closeInventory();
			  MainGui.removeEdit((Player)e.getWhoClicked());
			  break;
	 }	 
		 }
			 catch(NullPointerException | IndexOutOfBoundsException ex)
			 {
				Bukkit.getLogger().info(InvClick.toSet.toString());
			 }
			 }
}
}
