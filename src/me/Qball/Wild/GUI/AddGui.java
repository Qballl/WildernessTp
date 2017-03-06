package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddGui {
    public static void openMessGui(Player p) {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName("Close");
        close.setItemMeta(meta);
        Inventory Add = Bukkit.createInventory(p, 18, "WildTp");
        p.openInventory(Add);
        Add.setItem(1, MainGui.makeItem(Material.MAP, "World", Collections.singletonList("Click to add a world")));
        Add.setItem(6, MainGui.makeItem(Material.POTION, "Potion", Collections.singletonList("Click me to add a potion effect")));
        Add.setItem(17, close);
        Add.setItem(8, MainGui.makeItem(Material.MAP, "Biome Blacklist", Collections.singletonList("Click me to add a biome to the blacklist")));
    }
}