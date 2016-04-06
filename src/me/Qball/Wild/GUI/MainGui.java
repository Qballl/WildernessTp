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

	public static void OpenGUI(Player p)
	{
	ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
	ItemMeta meta = Close.getItemMeta();
	meta.setDisplayName("Close");
	Close.setItemMeta(meta);
	ItemStack set = set();
	ItemStack Messages = Message();
	ItemStack add = add();
	Inventory Wildtp = Bukkit.createInventory(p,9, "WildTp");
	putEdit(p);
	p.openInventory(Wildtp);
	Wildtp.setItem(8, Close);
	Wildtp.setItem(2, Messages);
	Wildtp.setItem(4, set);
	Wildtp.setItem(6, add); 
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
	private static ItemStack set()
	{
		ItemStack set = new ItemStack(Material.PAPER,1);
		ItemMeta Set = set.getItemMeta();
		Set.setDisplayName("Set");
		ArrayList<String> Setlore = new ArrayList<String>();
		Setlore.add("Click me to set the values for x and z along with cooldown and cost");
		Set.setLore(Setlore);
		set.setItemMeta(Set);
		return set;
	}
	private static ItemStack Message()
	{
		ItemStack Messages = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta message = Messages.getItemMeta();
		message.setDisplayName("Messages");
		ArrayList<String> MessLore = new ArrayList<String>();
		MessLore.add("Click to set the messages");
		message.setLore(MessLore);
		Messages.setItemMeta(message);
		return Messages;
	}
	public static ItemStack add()
	{
		ItemStack add = new ItemStack(Material.BOOK,1); 
		ItemMeta Add = add.getItemMeta();
		Add.setDisplayName("Add a potion or world");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to add a potion or world");
		add.setItemMeta(Add);
		return add;
		
	}
	 
	  
	  
}
