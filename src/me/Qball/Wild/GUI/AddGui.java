package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddGui {
	public static void openMessGui(Player p)
	{
		ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta meta = Close.getItemMeta();
		meta.setDisplayName("Close");
		Close.setItemMeta(meta);
		Inventory Add = Bukkit.createInventory(p,9, "WildTp");
		p.openInventory(Add);
		Add.setItem(1, World());
		Add.setItem(6, Potion());
		Add.setItem(8, Close);
	}
	public static ItemStack World()
	{
		ItemStack World = new ItemStack(Material.MAP,1);
		ItemMeta meta = World.getItemMeta();
		meta.setDisplayName("World");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to add a world");
		meta.setLore(lore);
		World.setItemMeta(meta);
		return World;
	}
	public static ItemStack Potion()
	{
		ItemStack Potion = new ItemStack(Material.POTION,1);
		ItemMeta meta = Potion.getItemMeta();
		meta.setDisplayName("Potion");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click me to ad a potion effect");
		meta.setLore(lore);
		Potion.setItemMeta(meta);
		return Potion;
	}
}
