package me.Qball.Wild.GUI;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MainGui {
	public static Map<UUID, String> edit = new HashMap<UUID,String>();
	
	public static void OpenGUI(Player p)
	{
	ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
	ItemMeta meta = Close.getItemMeta();
	meta.setDisplayName("Close");
	ArrayList<String>lore = new ArrayList<String>();
	lore.add("Click to close the inventory and return to normal gameplay");
	meta.setLore(lore);
	Close.setItemMeta(meta);
	Inventory Wildtp = Bukkit.createInventory(p,18 ,"WildTp");
	putEdit(p);
	p.openInventory(Wildtp);
	Wildtp.setItem(17, Close);
	Wildtp.setItem(2, message());
	Wildtp.setItem(4, set());
	Wildtp.setItem(6, add()); 
	Wildtp.setItem(0, sounds());
	Wildtp.setItem(8, hooks());
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
	public static ItemStack makeItem(Material material, String name, List<String> lore){
		ItemStack stack = new ItemStack(material);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	private static ItemStack set()
	{
		ItemStack set = new ItemStack(Material.PAPER,1);
		ItemMeta Set = set.getItemMeta();
		Set.setDisplayName("Set");
		ArrayList<String> Setlore = new ArrayList<String>();
		Setlore.add("Click me to set the values for x and z ");
		Setlore.add("along with cooldown and cost");
		Set.setLore(Setlore);
		set.setItemMeta(Set);
		return set;
	}
	private static ItemStack message()
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
		Add.setDisplayName("Add a Potion or World");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to add a potion or world");
		Add.setLore(lore);
		add.setItemMeta(Add);
		return add;
		
	}
	public static ItemStack sounds()
	{
		ItemStack sound = new ItemStack(Material.JUKEBOX,1);
		ItemMeta meta = sound.getItemMeta();
		meta.setDisplayName("Sounds");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click me to set the sound");
		meta.setLore(lore);
		sound.setItemMeta(meta);
		return sound;
	}
	public static ItemStack hooks()
	{
		ItemStack hook = new ItemStack(Material.TRIPWIRE_HOOK,1);
		ItemMeta meta = hook.getItemMeta();
		meta.setDisplayName("Hooks");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click me to enable or disable a hook");
		meta.setLore(lore);
		hook.setItemMeta(meta);
		return hook;
	}
	 
	   
	  
}
