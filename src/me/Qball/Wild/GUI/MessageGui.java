package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MessageGui {
	public static void openMessGui(Player p)
	{
		ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta meta = Close.getItemMeta();
		meta.setDisplayName("Close");
		Close.setItemMeta(meta);
		Inventory Messages = Bukkit.createInventory(p,18, "WildTp");
		ItemStack teleport = Teleport();
		p.openInventory(Messages);
		Messages.setItem(0, teleport);
		Messages.setItem(2, NoSuit());
		Messages.setItem(4,Cost());
		Messages.setItem(6, NoBreak());
		Messages.setItem(8, NoPerm());
		Messages.setItem(10, Cool());
		Messages.setItem(17, Close);
	}
	public static ItemStack Teleport()
	{
		ItemStack Teleport = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = Teleport.getItemMeta();
		meta.setDisplayName("Teleport");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the message to be said on teleport");
		Teleport.setItemMeta(meta);
		return Teleport;
	}
	public static ItemStack NoSuit()
	{
		ItemStack NoSuit = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = NoSuit.getItemMeta();
		meta.setDisplayName("NoSuit");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the no suitable location message");
		NoSuit.setItemMeta(meta);
		return NoSuit;
	}
	public static ItemStack Cost()
	{
		ItemStack cost = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = cost.getItemMeta();
		meta.setDisplayName("CostMsg");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the cost message");
		cost.setItemMeta(meta);
		return cost;
	}
	public static ItemStack NoBreak()
	{
		ItemStack NoBreak = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = NoBreak.getItemMeta();
		meta.setDisplayName("No-Break");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the no perm for sign brak message");
		NoBreak.setItemMeta(meta);
		return NoBreak;
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
	public static ItemStack Cool()
	{
		ItemStack Cool = new ItemStack(Material.BOOK_AND_QUILL,1);
		ItemMeta meta = Cool.getItemMeta();
		meta.setDisplayName("Cooldown");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Click to set the cool down message");
		Cool.setItemMeta(meta);
		return Cool;
	}

}
