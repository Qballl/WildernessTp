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
        Inventory wildTp = Bukkit.createInventory(p, 27, "Hooks");
        p.openInventory(wildTp);
        wildTp.setItem(0, MainGui.makeItem(Material.DIAMOND_PICKAXE, "Towny Hook", Collections.singletonList("Click to enable or disable Towny Hook")));
        wildTp.setItem(2, MainGui.makeItem(Material.TNT, "Factions Hook", Collections.singletonList("Click to enable or disable Factions hook")));
        wildTp.setItem(4, MainGui.makeItem(Material.WOOD_SPADE, "GreifPrevention Hook", Collections.singletonList("Click to enable or disable Grief Prevention Hoo")));
        wildTp.setItem(6, MainGui.makeItem(Material.WOOD_AXE, "WorldGuard Hook", Collections.singletonList("Click to enable or disable WorldGuard Hook")));
        wildTp.setItem(8, MainGui.makeItem(Material.WOOD_AXE, "Kingdom Hook", Collections.singletonList("Click to enable or disable Kingdom Hook")));
        wildTp.setItem(10, MainGui.makeItem(Material.TNT, "FactionsUUD Hook", Collections.singletonList("Click to enable or disable FactionsUuid Hook")));
        wildTp.setItem(12, MainGui.makeItem(Material.GOLD_SPADE, "Residence Hook", Collections.singletonList("Click to enable or disable Residence Hook")));
        wildTp.setItem(14, MainGui.makeItem(Material.GOLD_AXE, "LandLord Hook", Collections.singletonList("Click to enable or disable LandLord Hook")));
        wildTp.setItem(16, MainGui.makeItem(Material.TNT, "LegacyFactions Hook", Collections.singletonList("Click to enable or disable LegacyFactions hook")));
        wildTp.setItem(18, MainGui.makeItem(Material.DIAMOND_AXE,"Feudal Hook", Collections.singletonList("Click to enable or disable Feudal hook")));
        wildTp.setItem(24, MainGui.backItem());
        wildTp.setItem(26, close);
    }
}
