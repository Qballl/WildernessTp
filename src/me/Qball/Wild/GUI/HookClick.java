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
	public static InvClick click;
	public HookClick()
	{}
	public HookClick(InvClick click){
		this.click = click;
	}

	@EventHandler
	 public static void click(InventoryClickEvent e) {
		 if(e.getInventory().getName().equalsIgnoreCase("Hooks"))
		 {

		 e.setCancelled(true);
		 ItemStack item = e.getCurrentItem();
		 ItemMeta meta = item.getItemMeta();
		 String name = meta.getDisplayName().toLowerCase();
		 switch (name)
		 { 
		 case "towny hook":
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"Towny");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked()); 
			 break;
		 case "factions hook":
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"Factions");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "factionsuuid hook":
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"FactionsUUID");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "griefprevention hook":
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"GriefPrevention");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case  "worldguard hook": 
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"WorldGuard");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "kingdom hook":
			 click.toSet.put(e.getWhoClicked().getUniqueId(),"Kingdoms");
			 e.getWhoClicked().closeInventory();
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "true":
			 String val = click.toSet.get(e.getWhoClicked().getUniqueId());
			 click.toSet.remove(e.getWhoClicked().getUniqueId());
			 wild.getConfig().set(val, true);
			 wild.saveConfig();
			 Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
			 e.getWhoClicked().closeInventory();
			 MainGui.removeEdit((Player)e.getWhoClicked());
			 break;
		 case "false":
			 val = click.toSet.remove(e.getWhoClicked().getUniqueId());
			 click.toSet.remove(e.getWhoClicked().getUniqueId());
			 wild.getConfig().set(val, false);
			 wild.saveConfig();
			 Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
			 e.getWhoClicked().closeInventory();
			 MainGui.removeEdit((Player)e.getWhoClicked());
			 break;
		 default:
			  e.getWhoClicked().closeInventory();
			  MainGui.removeEdit((Player)e.getWhoClicked());
			  break;
	 }	 

		 }
}
}
