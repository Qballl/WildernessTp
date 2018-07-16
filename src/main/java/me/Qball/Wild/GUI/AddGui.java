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
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory add = Bukkit.createInventory(p, 18, "WildTp");
        p.openInventory(add);
        add.setItem(0, MainGui.makeItem(Material.MAP, "World", Collections.singletonList("Click to add a world")));
        add.setItem(4, MainGui.makeItem(Material.POTION, "Potion", Collections.singletonList("Click me to add a potion effect")));
        add.setItem(8, MainGui.makeItem(Material.MAP, "Biome Blacklist", Collections.singletonList("Click me to add a biome to the blacklist")));
        add.setItem(10, MainGui.makeItem(Material.valueOf(MainGui.getMaterials().get("Command")), "Blocked Commands", Collections.singletonList("Clicke me to add a blocked command when waiting to be teleported")));
        add.setItem(12, MainGui.makeItem(Material.valueOf(MainGui.getMaterials().get("Command")), "Post Commands", Collections.singletonList("Clicke me to add a post command to be executed on the player once teleported")));
        add.setItem(15, MainGui.backItem());
        add.setItem(17, close);
    }
}