package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvClick implements Listener {
	public static ArrayList<String> toSet = new ArrayList<String>();
	public static ArrayList<UUID> Set = new ArrayList<UUID>();
	public static ArrayList<UUID> Add = new ArrayList<UUID>();
	public static ArrayList<UUID> Messages = new ArrayList<UUID>();
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
			if(Set.contains(e.getWhoClicked().getUniqueId()))
			{
				Set.remove(e.getWhoClicked().getUniqueId());
			}
			else if(Add.contains(e.getWhoClicked().getUniqueId()))
			{
				Add.remove(e.getWhoClicked().getUniqueId());
			}
			else if(Messages.contains(e.getWhoClicked().getUniqueId()))
			{
				Messages.remove(e.getWhoClicked().getUniqueId());
			}
			break;
		 case "messages":
			 e.getWhoClicked().closeInventory();
			 MessageGui.openMessGui((Player)e.getWhoClicked());
			 break;
		 case "set":
			 e.getWhoClicked().closeInventory();
			 SetGui.OpenSet((Player)e.getWhoClicked());
			 break;
		 case "add a potion or world":
			 e.getWhoClicked().closeInventory();
			 AddGui.openMessGui((Player)e.getWhoClicked());
			 break;
		 case "minx":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MinX");
			 Set.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "maxx":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MaxX");
			 Set.add(e.getWhoClicked().getUniqueId());
		 case "minz":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MinZ");
			 Set.add(e.getWhoClicked().getUniqueId());
		 case "maxz":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MaxZ");
			 Set.add(e.getWhoClicked().getUniqueId());
		 case "cool":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Cooldown");
			 Set.add(e.getWhoClicked().getUniqueId());
		 case "cost":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Cost");
			 Set.add(e.getWhoClicked().getUniqueId());
		 case "teleport":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Teleport");
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "nosuit":
			 e.getWhoClicked().closeInventory();
			 toSet.add("No Suitable Location");
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "costmsg":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Costmsg");
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "no-break": 
			 e.getWhoClicked().closeInventory();
			 toSet.add("No-Break");
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "no-perm":
			 e.getWhoClicked().closeInventory();
			 toSet.add("No-Perm"); 
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		  case "cooldown":
			  e.getWhoClicked().closeInventory();
			  toSet.add("Cooldownmsg");
			  Messages.add(e.getWhoClicked().getUniqueId());
		  case "potion":
			  e.getWhoClicked().closeInventory();
			  toSet.add("Potions");
			  Add.add(e.getWhoClicked().getUniqueId());
		  case "world":
			  e.getWhoClicked().closeInventory();
			  toSet.add("Worlds");
			  Add.add(e.getWhoClicked().getUniqueId()); 
		 default:
			 e.getWhoClicked().closeInventory();
			 MainGui.removeEdit((Player)e.getWhoClicked());
			 break;
			 
		
		 }
		 }
	  }
}
