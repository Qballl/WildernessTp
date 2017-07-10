package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetGui {
    public static void OpenSet(Player p) {

        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory set = Bukkit.createInventory(p, 27, "WildTp");
        p.openInventory(set);
        set.setItem(0, MainGui.makeItem(Material.BOOK_AND_QUILL, "MinX", Collections.singletonList("Click to set the minx")));
        set.setItem(2, MainGui.makeItem(Material.BOOK_AND_QUILL, "MaxX", Collections.singletonList("Click to set the maxx")));
        set.setItem(4, MainGui.makeItem(Material.BOOK_AND_QUILL, "MinZ", Collections.singletonList("Click to set the minz")));
        set.setItem(6, MainGui.makeItem(Material.BOOK_AND_QUILL, "MaxZ", Collections.singletonList("Click to set the maxz")));
        set.setItem(8, MainGui.makeItem(Material.WATCH, "Cooldown", Collections.singletonList("Click me to set the cooldown for the command")));
        set.setItem(10, MainGui.makeItem(Material.GOLD_BLOCK, "Cost", Collections.singletonList("Click me to set the cost for the command")));
        set.setItem(12, MainGui.makeItem(Material.WATCH, "Wait", Collections.singletonList("Click to set the wait before telepoting happens")));
        set.setItem(14, MainGui.makeItem(Material.WATCH, "Retries", Collections.singletonList("Click to set the number of retries if a location is unsuitable")));
        set.setItem(16, MainGui.makeItem(Material.WATCH, "Do Retry", Collections.singletonList("Click to set true or false for doing retries")));
        set.setItem(18, MainGui.makeItem(Material.MAP, "Distance", Arrays.asList("Click to set the distance the plugin checks for a claim")));
        set.setItem(24, MainGui.backItem());
        set.setItem(26, close);
    }
}
