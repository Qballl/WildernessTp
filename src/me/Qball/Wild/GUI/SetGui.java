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
		Set.setItem(17,Close);
	}
	public static ItemStack NoPerm()
	{
		ItemStack NoPerm = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = NoPerm.getItemMeta();
		meta.setDisplayName("No-Perm");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the No permission to make a sign message");
		NoPerm.setItemMeta(meta);
		return NoPerm;
	}
}
