package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MainGui {
	public static HashMap<UUID, String> edit = new HashMap<UUID,String>();

	public void OenGUI(Player p)
	{
	ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
	ItemStack Messages = new ItemStack(Material.BOOK_AND_QUILL,1);
	ItemMeta message = Messages.getItemMeta();
	message.setDisplayName("Set Messages");
	ArrayList<String> MessLore = new ArrayList<String>();
	MessLore.add("Click to set the messages");
	message.setLore(MessLore);
	Messages.setItemMeta(message);
	Inventory Wildtp = Bukkit.createInventory(p,9, "WildTp");
	 

	p.openInventory(Wildtp);
	Wildtp.setItem(9, Close);
	Wildtp.setItem(2, Messages);
}
	public static boolean editMode(Player p)
	{
		if(!edit.containsKey(p.getUniqueId()))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static void putEdit(Player p)
	{
		if(!edit.containsKey(p.getUniqueId()))
		{
			edit.put(p.getUniqueId(),p.getCustomName());
		}
	}
	public static void removeEdit(Player p)
	{
		if(edit.containsKey(p.getUniqueId()))
		{
			edit.remove(p.getUniqueId());
			
		}
	}
}
