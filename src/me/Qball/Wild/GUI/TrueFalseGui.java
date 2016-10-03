package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class TrueFalseGui {
public static void openTrue(Player p)
{
	ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
 	ItemMeta meta = Close.getItemMeta();
	meta.setDisplayName("Close");
	ArrayList<String>lore = new ArrayList<String>();
	lore.add("Click to close the inventory and return to normal gameplay");
	meta.setLore(lore);
	Close.setItemMeta(meta);
	Inventory Wildtp = Bukkit.createInventory(p,9,"Hooks");
	p.openInventory(Wildtp);
	Wildtp.setItem(2, True()); 
	Wildtp.setItem(5, False()); 
	Wildtp.setItem(8, Close);
}
 public static ItemStack True()
{
	 	ItemStack True = new ItemStack(Material.WOOL,1,(byte)5);
		ItemMeta meta = True.getItemMeta();
		meta.setDisplayName("True");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to to enable the value to true");
		meta.setLore(lore);
		True.setItemMeta(meta);
		return True;
}
 public static ItemStack False()
{
	   	ItemStack wool = new ItemStack(Material.WOOL,1,(byte)14);
		ItemMeta meta = wool.getItemMeta();
		meta.setDisplayName("False");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to to disable the value to false");
		meta.setLore(lore);
		wool.setItemMeta(meta);
		return wool;
}

}