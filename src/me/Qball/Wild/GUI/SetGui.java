package me.Qball.Wild.GUI;

import java.util.ArrayList;

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
		Close.setItemMeta(meta);
		Inventory Set = Bukkit.createInventory(p,18,"WildTp");
		p.openInventory(Set);
		Set.setItem(0,MinX());
		Set.setItem(2,MaxX());
		Set.setItem(6,MinZ());
		Set.setItem(6,MaxZ());
		Set.setItem(8, Cool());
		Set.setItem(9, Cost());
		Set.setItem(17,Close);
	}
	public static ItemStack MinX()
	{
		ItemStack MinX = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MinX.getItemMeta();
		meta.setDisplayName("MinX");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the No permission to make a sign message");
		MinX.setItemMeta(meta);
		return MinX;
	}
	public static ItemStack MaxX()
	{
		ItemStack MaxX = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MaxX.getItemMeta();
		meta.setDisplayName("MaxX");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the No permission to make a sign message");
		MaxX.setItemMeta(meta);
		return MaxX;
	}
	public static ItemStack MinZ()
	{
		ItemStack MinZ = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MinZ.getItemMeta();
		meta.setDisplayName("MinZ");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the No permission to make a sign message");
		MinZ.setItemMeta(meta);
		return MinZ;
	}
	public static ItemStack MaxZ()
	{
		ItemStack MaxZ = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = MaxZ.getItemMeta();
		meta.setDisplayName("MaxZ");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the No permission to make a sign message");
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
		cost.setItemMeta(meta);
		return cost;
	}
	
}
