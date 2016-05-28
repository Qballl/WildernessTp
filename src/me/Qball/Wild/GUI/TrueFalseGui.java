package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrueFalseGui {
public void openTrue(Player p)
{
	ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
	ItemMeta meta = Close.getItemMeta();
	meta.setDisplayName("Close");
	ArrayList<String>lore = new ArrayList<String>();
	lore.add("Click to close the inventory and return to normal gameplay");
	meta.setLore(lore);
	Close.setItemMeta(meta);
	Inventory TF = Bukkit.createInventory(p,18,"WildTp");
	p.openInventory(TF);
	TF.setItem(2, True());
	TF.setItem(5, False());
	TF.setItem(8, Close);
	
}
 public ItemStack True()
{
		ItemStack True = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = True.getItemMeta();
		meta.setDisplayName("True");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to to enable the hook");
		meta.setLore(lore);
		True.setItemMeta(meta);
		return True;
}
 public ItemStack False()
{
		ItemStack False = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = False.getItemMeta();
		meta.setDisplayName("False");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to to disable the hook");
		meta.setLore(lore);
		False.setItemMeta(meta);
		return False;
}

}
