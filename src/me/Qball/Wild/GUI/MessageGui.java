package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MessageGui {
    public static void openMessGui(Player p) {
        ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = Close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        Close.setItemMeta(meta);
        Inventory Messages = Bukkit.createInventory(p, 18, "WildTp");
        p.openInventory(Messages);
        Messages.setItem(0, makeItem("Teleport" , "Click to set the message to be said on teleport"));
        Messages.setItem(2, makeItem("NoSuit", "Click to set the no suitable location message"));
        Messages.setItem(4, makeItem("CostMsg", "Click to set the cost message"));
        Messages.setItem(6, makeItem("No-Break", "Click to set the no perm for sign break message"));
        Messages.setItem(8, NoPerm());
        Messages.setItem(10, Cool());
        Messages.setItem(12, WarmUp());
        Messages.setItem(14, UsedCmd());
        Messages.setItem(17, Close);
    }
    private static ItemStack makeItem(String name, String lore) {
        ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = stack.getItemMeta();
        ArrayList<String> loreArray = new ArrayList<>();
        loreArray.add(lore);
        meta.setLore(loreArray);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack NoPerm() {
        ItemStack NoPerm = new ItemStack(Material.BOOK_AND_QUILL, 1);
        ItemMeta meta = NoPerm.getItemMeta();
        meta.setDisplayName("No-Perm");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to set the No permission to make a sign message");
        meta.setLore(lore);
        NoPerm.setItemMeta(meta);
        return NoPerm;
    }

    public static ItemStack Cool() {
        ItemStack Cool = new ItemStack(Material.BOOK_AND_QUILL, 1);
        ItemMeta meta = Cool.getItemMeta();
        meta.setDisplayName("Cooldown Message");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to set the cool down message");
        meta.setLore(lore);
        Cool.setItemMeta(meta);
        return Cool;
    }

    public static ItemStack WarmUp() {
        ItemStack Warm = new ItemStack(Material.BOOK_AND_QUILL, 1);
        ItemMeta meta = Warm.getItemMeta();
        meta.setDisplayName("Wait/WarmUp Message");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to set the warmp/delay/wait message");
        meta.setLore(lore);
        Warm.setItemMeta(meta);
        return Warm;
    }

    public static ItemStack UsedCmd() {
        ItemStack Use = new ItemStack(Material.BOOK_AND_QUILL, 1);
        ItemMeta meta = Use.getItemMeta();
        meta.setDisplayName("Used command Message");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to set the command used message");
        meta.setLore(lore);
        Use.setItemMeta(meta);
        return Use;
    }
}
