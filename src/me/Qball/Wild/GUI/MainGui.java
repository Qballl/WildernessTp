package me.Qball.Wild.GUI;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MainGui {
    public static Map<UUID, String> edit = new HashMap<UUID, String>();

    public static void OpenGUI(Player p) {
        ItemStack Close = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta = Close.getItemMeta();
        meta.setDisplayName("Close");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Click to close the inventory and return to normal gameplay");
        meta.setLore(lore);
        Close.setItemMeta(meta);
        Inventory Wildtp = Bukkit.createInventory(p, 18, "WildTp");
        putEdit(p);
        p.openInventory(Wildtp);
        Wildtp.setItem(17, Close);
        Wildtp.setItem(2, makeItem(Material.BOOK_AND_QUILL, "Messages", Collections.singletonList("Click to set the messages")));
        Wildtp.setItem(4, makeItem(Material.PAPER, "Set", Arrays.asList(new String[]{"Click me to set the values for x and z ","along with cooldown and cost"})));
        Wildtp.setItem(6, makeItem(Material.BOOK,"Add a potion or world", Collections.singletonList("Click to add a potion or world")));
        Wildtp.setItem(0, makeItem(Material.JUKEBOX, "Sounds", Collections.singletonList("Click me to set the sound")));
        Wildtp.setItem(8, makeItem(Material.TRIPWIRE_HOOK,"Hooks", Collections.singletonList("Click me to enable or disable a hook")));
    }

    public static boolean editMode(Player p) {
        return edit.containsKey(p.getUniqueId());
    }

    public static void putEdit(Player p) {
        if (!edit.containsKey(p.getUniqueId())) {
            edit.put(p.getUniqueId(), p.getCustomName());
        }
    }

    public static void removeEdit(Player p) {
        if (edit.containsKey(p.getUniqueId())) {
            edit.remove(p.getUniqueId());
        }
    }

    public static ItemStack makeItem(Material material, String name, List<String> lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack makeItem(Material material, String name, List<String> lore, byte data){
        ItemStack stack = new ItemStack(material,1,data);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
