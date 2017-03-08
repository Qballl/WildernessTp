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
        Messages.setItem(8, makeItem("No-Perm", "Click to set the No permission to make a sign message"));
        Messages.setItem(10, makeItem( "Cooldown Message", "Click to set the cool down message"));
        Messages.setItem(12, makeItem( "Wait/Warmup Message", "Click to set the warmp/delay/wait message"));
        Messages.setItem(14, makeItem("Used command Message", "Click to set the command used message"));
        Messages.setItem(17, Close);
    }
    private static ItemStack makeItem(String name, String lore) {
        ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = stack.getItemMeta();
        ArrayList<String> loreArray = new ArrayList<>();
        loreArray.add(lore);
        meta.setLore(loreArray);
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
}
