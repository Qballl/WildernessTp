package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.Collections;

import me.Qball.Wild.Wild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class TrueFalseGui {
    public static void openTrue(Player p) {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory Wildtp = Bukkit.createInventory(p, 9, "Hooks");
        p.openInventory(Wildtp);
        if(!Wild.getInstance().thirteen) {
            Wildtp.setItem(2, MainGui.makeItem(Material.valueOf("WOOL"), "True", Collections.singletonList("Click to to enable the value to true"), (byte) 5));
            Wildtp.setItem(5, MainGui.makeItem(Material.valueOf("WOOL"), "False", Collections.singletonList("Click to to disable the value to false"), (byte) 14));
        }else{
            Wildtp.setItem(2, MainGui.makeItem(Material.valueOf("GREEN_WOOL"), "True", Collections.singletonList("Click to to enable the value to true")));
            Wildtp.setItem(5, MainGui.makeItem(Material.valueOf("RED_WOOL"), "False", Collections.singletonList("Click to to disable the value to false")));
        }
        Wildtp.setItem(8, close);
    }
}