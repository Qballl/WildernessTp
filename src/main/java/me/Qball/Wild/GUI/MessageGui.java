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
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory messages = Bukkit.createInventory(p, 27, "WildTp");
        p.openInventory(messages);
        messages.setItem(0, makeItem("Teleport" , "Click to set the message to be said on teleport"));
        messages.setItem(2, makeItem("NoSuit", "Click to set the no suitable location message"));
        messages.setItem(4, makeItem("CostMsg", "Click to set the cost message"));
        messages.setItem(6, makeItem("No-Break", "Click to set the no perm for sign break message"));
        messages.setItem(8, makeItem("No-Perm", "Click to set the No permission to make a sign message"));
        messages.setItem(10, makeItem( "Cooldown Message", "Click to set the cool down message"));
        messages.setItem(12, makeItem( "Wait/Warmup Message", "Click to set the warmp/delay/wait message"));
        messages.setItem(14, makeItem("Used command Message", "Click to set the command used message"));
        messages.setItem(17, makeItem("Refund Message", "Click to set the RefundMessage"));
        messages.setItem(19, makeItem("Blocked Command Message", "Click me to set the Blocked Command Message"));
        messages.setItem(21, makeItem("World Message","Click me to set the message for when command is used \nin a non configured world"));
        messages.setItem(23, makeItem("Cancel Message", "Click me to set the message for when a player \ncauses teleportation to be canceld"));
        messages.setItem(24, MainGui.backItem());
        messages.setItem(26, close);
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
