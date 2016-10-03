package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.SendMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class InvClick implements Listener {
	public static ArrayList<String> toSet = new ArrayList<String>();
	public static ArrayList<UUID> Set = new ArrayList<UUID>();
	public static ArrayList<UUID> Add = new ArrayList<UUID>();
	public static ArrayList<UUID> Messages = new ArrayList<UUID>();
	public static ArrayList<UUID> Sounds = new ArrayList<UUID>();
	public static ArrayList<UUID> worlds = new ArrayList<>();
	public static Plugin wild = Wild.getInstance();
	
	 @EventHandler
	    public void onInventoryClick(InventoryClickEvent e) {
		
		
		 if(e.getInventory().getName().equalsIgnoreCase("wildtp"))
		 {
			 try{
		 e.setCancelled(true);
		 ItemStack item = e.getCurrentItem();
		 ItemMeta meta = item.getItemMeta();
		 String name = meta.getDisplayName().toLowerCase();
		 switch (name)
		 {
		 case "close":
			e.getWhoClicked().closeInventory();
			MainGui.removeEdit((Player)e.getWhoClicked());
			if(Set.contains(e.getWhoClicked().getUniqueId()))
			{
				Set.remove(e.getWhoClicked().getUniqueId());
			}
			 if(Add.contains(e.getWhoClicked().getUniqueId()))
			{
				Add.remove(e.getWhoClicked().getUniqueId());
			}
			 if(Messages.contains(e.getWhoClicked().getUniqueId()))
			{
				Messages.remove(e.getWhoClicked().getUniqueId());
			}
			 if(Sounds.contains(e.getWhoClicked().getUniqueId()))
					{
					Sounds.remove(e.getWhoClicked().getUniqueId());
					}
			break;
		 case "messages":
			 e.getWhoClicked().closeInventory();
			 MessageGui.openMessGui((Player)e.getWhoClicked());
			 Messages.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "set":
			 e.getWhoClicked().closeInventory();
			 SetGui.OpenSet((Player)e.getWhoClicked()); 
			 Set.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "add a potion or world":
			 e.getWhoClicked().closeInventory();
			 AddGui.openMessGui((Player)e.getWhoClicked());
			 Add.add(e.getWhoClicked().getUniqueId());
			 break;
		 case "minx":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MinX");
			 worlds.add(e.getWhoClicked().getUniqueId());
			 SendMessage.send((Player) e.getWhoClicked());
			 SendMessage.sendInfo(e.getWhoClicked());
			 break;
		 case "maxx":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MaxX");
			 worlds.add(e.getWhoClicked().getUniqueId());
			 SendMessage.send((Player) e.getWhoClicked());
			 SendMessage.sendInfo(e.getWhoClicked());
			 break;
		 case "minz":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MinZ");
			 worlds.add(e.getWhoClicked().getUniqueId());
			 SendMessage.send((Player) e.getWhoClicked());
			 SendMessage.sendInfo(e.getWhoClicked());
			 break;
		 case "maxz":
			 e.getWhoClicked().closeInventory();
			 toSet.add("MaxZ");
			 worlds.add(e.getWhoClicked().getUniqueId());
			 SendMessage.send((Player) e.getWhoClicked());
			 SendMessage.sendInfo(e.getWhoClicked());
			 break;
		 case "cool":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Cooldown");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "cost":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Cost");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "retries":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Retries");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "do retry":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Retry");
			 TrueFalseGui.openTrue((Player)e.getWhoClicked());
			 break;
		 case "teleport":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Teleport");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "nosuit":
			 e.getWhoClicked().closeInventory();
			 toSet.add("No Suitable Location");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "costmsg":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Costmsg");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "no-break": 
			 e.getWhoClicked().closeInventory();
			 toSet.add("No-Break");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		 case "no-perm":
			 e.getWhoClicked().closeInventory();
			 toSet.add("No-Perm"); 
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		  case "cooldown message":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Cooldownmsg");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		  case "potion":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Potions");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		  case "world":
			 e.getWhoClicked().closeInventory();
			 toSet.add("Worlds");
			 SendMessage.send((Player) e.getWhoClicked());
			 break;
		  case "sounds":
			 e.getWhoClicked().closeInventory();
		     toSet.add("Sound");
		     Sounds.add(e.getWhoClicked().getUniqueId());
		     SendMessage.send((Player) e.getWhoClicked());
			 break;
		  case "wait":
			  e.getWhoClicked().closeInventory();
			  toSet.add("Wait");
			  SendMessage.send((Player) e.getWhoClicked());
			  break;
		  case "wait/warmUp message":
			  e.getWhoClicked().closeInventory();
			  toSet.add("WaitMsg");
			  SendMessage.send((Player) e.getWhoClicked());
			  break;
		  case "used command message":
			  e.getWhoClicked().closeInventory();
			  toSet.add("UsedCmd");
			  SendMessage.send((Player) e.getWhoClicked());
			  break;
		  case "bome blacklist":
			  e.getWhoClicked().closeInventory();
			  toSet.add("Blacklisted_Biomes");
			  SendMessage.send((Player) e.getWhoClicked());
			  break;
		  case "hooks":
			  e.getWhoClicked().closeInventory();
			  HookGui.openHook((Player)e.getWhoClicked());
			  break;			   
		 default:
			  e.getWhoClicked().closeInventory();
			  MainGui.removeEdit((Player)e.getWhoClicked());
			  break;
			 
		
		 }
			 }
			 catch(NullPointerException ex)
			 {
				 
			 }
		 }
	  }
}