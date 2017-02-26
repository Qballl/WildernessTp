package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetGui {
	public static void OpenSet(Player p)
	{
		
		ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta meta = Close.getItemMeta();
		meta.setDisplayName("Close");
		ArrayList<String>lore = new ArrayList<String>();
		lore.add("Click to close the inventory and return to normal gameplay");
		meta.setLore(lore);
		Close.setItemMeta(meta);
		Inventory set = Bukkit.createInventory(p,27,"WildTp");
		p.openInventory(set);
		
		set.setItem(0,minX());
		set.setItem(2,maxX());
		set.setItem(4,minZ());
		set.setItem(6,maxZ());
		set.setItem(8, Cool());
		set.setItem(10, Cost());
		set.setItem(12, delay());
		set.setItem(14, retry());
		set.setItem(16, doRetry());
		set.setItem(18, distance());
		set.setItem(26, Close);
	}
	public static ItemStack minX()
	{
		ItemStack MinX = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MinX.getItemMeta();
		meta.setDisplayName("MinX");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the minx");
		meta.setLore(lore);
		MinX.setItemMeta(meta);
		return MinX;
	}
	public static ItemStack maxX()
	{
		ItemStack MaxX = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MaxX.getItemMeta();
		meta.setDisplayName("MaxX");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the maxx");
		meta.setLore(lore);
		MaxX.setItemMeta(meta);
		return MaxX;
	}
	public static ItemStack minZ()
	{
		ItemStack MinZ = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MinZ.getItemMeta();
		meta.setDisplayName("MinZ");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the minz");
		meta.setLore(lore);
		MinZ.setItemMeta(meta);
		return MinZ;
	}
	public static ItemStack maxZ()
	{
		ItemStack MaxZ = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MaxZ.getItemMeta();
		meta.setDisplayName("MaxZ");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the maxz");
		meta.setLore(lore);
		MaxZ.setItemMeta(meta);
		return MaxZ;
	}
	public static ItemStack Cool()
	{
		ItemStack Cool = new ItemStack(Material.WATCH,1);
		ItemMeta meta = Cool.getItemMeta();
		meta.setDisplayName("Cooldown");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click me to set the cooldown for the command");
		meta.setLore(lore);
		Cool.setItemMeta(meta);
		return Cool;
	}
	public static ItemStack Cost()
	{
		ItemStack cost = new ItemStack(Material.GOLD_BLOCK,1);
		ItemMeta meta = cost.getItemMeta();
		meta.setDisplayName("Cost");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click me to set the cost for the command");
		meta.setLore(lore);
		cost.setItemMeta(meta);
		return cost; 
	}
	public static ItemStack delay()
	{
		ItemStack Wait = new ItemStack(Material.WATCH,1);
		ItemMeta meta = Wait.getItemMeta();
		meta.setDisplayName("Wait");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the wait before telepoting happens");
		meta.setLore(lore);
		Wait.setItemMeta(meta);
		return Wait;

	}
	public static ItemStack retry()
	{
		ItemStack retry = new ItemStack(Material.WATCH,1);
		ItemMeta meta = retry.getItemMeta();
		meta.setDisplayName("Retries");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the number of retries if a location is unsuitable");
		meta.setLore(lore);
		retry.setItemMeta(meta);
		return retry;

	}
	public static ItemStack doRetry()
	{
		ItemStack retry = new ItemStack(Material.WATCH,1);
		ItemMeta meta = retry.getItemMeta();
		meta.setDisplayName("Do Retry");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set true or false for doing retries");
		meta.setLore(lore);
		retry.setItemMeta(meta);
		return retry;
	}

	public static ItemStack distance(){
		ItemStack distance = new ItemStack(Material.MAP,1);
		ItemMeta meta = distance.getItemMeta();
		meta.setDisplayName("Distance");
		meta.setLore(Arrays.asList(new String[]{"Click to set the distance the plugin checks for a claim"}));
		distance.setItemMeta(meta);
		return distance;
	}
	
	
}
