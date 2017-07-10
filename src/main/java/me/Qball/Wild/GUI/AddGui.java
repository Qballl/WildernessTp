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
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory Add = Bukkit.createInventory(p, 18, "WildTp");
        p.openInventory(Add);
        Add.setItem(0, MainGui.makeItem(Material.MAP, "World", Collections.singletonList("Click to add a world")));
        Add.setItem(4, MainGui.makeItem(Material.POTION, "Potion", Collections.singletonList("Click me to add a potion effect")));
        Add.setItem(8, MainGui.makeItem(Material.MAP, "Biome Blacklist", Collections.singletonList("Click me to add a biome to the blacklist")));
        Add.setItem(10, MainGui.makeItem(Material.COMMAND, "Blocked Commands", Collections.singletonList("Clicke me to add a blocked command when waiting to be teleported")));
        Add.setItem(12, MainGui.makeItem(Material.COMMAND, "Post Commands", Collections.singletonList("Clicke me to add a post command to be executed on the player once teleported")));
        Add.setItem(15, MainGui.backItem());
        Add.setItem(17, close);
    }
}