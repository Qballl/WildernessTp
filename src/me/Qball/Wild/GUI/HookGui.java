package me.Qball.Wild.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HookGui {
    public static void openHook(Player p) {

        ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = Close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        Close.setItemMeta(meta);
        Inventory Wildtp = Bukkit.createInventory(p, 18, "Hooks");
        p.openInventory(Wildtp);
        Wildtp.setItem(0, towny());
        Wildtp.setItem(2, factions());
        Wildtp.setItem(4, griefPreven());
        Wildtp.setItem(6, worldGuard());
        Wildtp.setItem(8, kingdoms());
        Wildtp.setItem(10, factionsUUID());
        Wildtp.setItem(17, Close);
    }

    public static ItemStack factions() {
        ItemStack factions = new ItemStack(Material.TNT, 1);
        ItemMeta meta = factions.getItemMeta();
        meta.setDisplayName("Factions Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable factions hook");
        meta.setLore(lore);
        factions.setItemMeta(meta);
        return factions;
    }

    public static ItemStack factionsUUID() {
        ItemStack factions = new ItemStack(Material.TNT, 1);
        ItemMeta meta = factions.getItemMeta();
        meta.setDisplayName("FactionsUUID Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable factionsuuid hook");
        meta.setLore(lore);
        factions.setItemMeta(meta);
        return factions;
    }

    public static ItemStack griefPreven() {
        ItemStack griefPreven = new ItemStack(Material.WOOD_SPADE, 1);
        ItemMeta meta = griefPreven.getItemMeta();
        meta.setDisplayName("GriefPrevention Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable Grief Prevention Hook");
        meta.setLore(lore);
        griefPreven.setItemMeta(meta);
        return griefPreven;
    }

    public static ItemStack towny() {

        ItemStack towny = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = towny.getItemMeta();
        meta.setDisplayName("Towny Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable Towny Hook");
        meta.setLore(lore);
        towny.setItemMeta(meta);
        return towny;
    }

    public static ItemStack worldGuard() {

        ItemStack worldGuard = new ItemStack(Material.WOOD_AXE, 1);
        ItemMeta meta = worldGuard.getItemMeta();
        meta.setDisplayName("WorldGuard Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable WorldGuard Hook");
        meta.setLore(lore);
        worldGuard.setItemMeta(meta);
        return worldGuard;
    }

    public static ItemStack kingdoms() {
        ItemStack kingdom = new ItemStack(Material.WOOD_AXE, 1);
        ItemMeta meta = kingdom.getItemMeta();
        meta.setDisplayName("Kingdom Hook");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to enable or disable Kingdom Hook");
        meta.setLore(lore);
        kingdom.setItemMeta(meta);
        return kingdom;
    }
}
