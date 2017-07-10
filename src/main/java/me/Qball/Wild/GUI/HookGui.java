package me.Qball.Wild.GUI;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HookGui {
    public static void openHook(Player p) {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        close.setItemMeta(meta);
        Inventory Wildtp = Bukkit.createInventory(p, 27, "Hooks");
        p.openInventory(Wildtp);
        Wildtp.setItem(0, MainGui.makeItem(Material.DIAMOND_PICKAXE, "Towny Hook", Collections.singletonList("Click to enable or disable Towny Hook")));
        Wildtp.setItem(2, MainGui.makeItem(Material.TNT, "Factions Hook", Collections.singletonList("Click to enable or disable Factions hook")));
        Wildtp.setItem(4, MainGui.makeItem(Material.WOOD_SPADE, "GreifPrevention Hook", Collections.singletonList("Click to enable or disable Grief Prevention Hoo")));
        Wildtp.setItem(6, MainGui.makeItem(Material.WOOD_AXE, "WorldGuard Hook", Collections.singletonList("Click to enable or disable WorldGuard Hook")));
        Wildtp.setItem(8, MainGui.makeItem(Material.WOOD_AXE, "Kingdom Hook", Collections.singletonList("Click to enable or disable Kingdom Hook")));
        Wildtp.setItem(10, MainGui.makeItem(Material.TNT, "FactionsUUD Hook", Collections.singletonList("Click to enable or disable FactionsUuid Hook")));
        Wildtp.setItem(12, MainGui.makeItem(Material.GOLD_SPADE, "Residence Hook", Collections.singletonList("Click to enable or disable Residence Hook")));
        Wildtp.setItem(14, MainGui.makeItem(Material.GOLD_AXE, "LandLord Hook", Collections.singletonList("Click to enable or disable LandLord Hook")));
        Wildtp.setItem(16, MainGui.makeItem(Material.TNT, "LegacyFactions Hook", Collections.singletonList("Click to enable or disable LegacyFactions hook")));
        Wildtp.setItem(24, MainGui.backItem());
        Wildtp.setItem(26, close);
    }
}
