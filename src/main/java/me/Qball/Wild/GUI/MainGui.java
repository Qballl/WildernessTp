package me.Qball.Wild.GUI;

import java.util.*;

import me.Qball.Wild.Wild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        p.sendMessage(ChatColor.RED+"Please make sure to use the redstone block to close early or you wont be able to chat");
        Wildtp.setItem(17, Close);
        Wildtp.setItem(2, makeItem(Material.valueOf(getMaterials().get("Book")), "Messages", Collections.singletonList("Click to set the messages")));
        Wildtp.setItem(4, makeItem(Material.PAPER, "Set", Arrays.asList("Click me to set the values for x and z ","along with cooldown and cost")));
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

    public static ItemStack backItem(){
        return makeItem(Material.PAPER, "Back", Collections.singletonList("Click to go back to the main gui"));
    }

    public static HashMap<String,String> getMaterials(){
        HashMap<String,String> materials = new HashMap<>();
        if(Wild.getInstance().thirteen){
            materials.put("Command","COMMAND_BLOCK");
            materials.put("Book", "WRITABLE_BOOK");
            materials.put("Watch","CLOCK");
            materials.put("Wood_Shovel","WOODEN_SHOVEL");
            materials.put("Wood_Axe","WOODEN_AXE");
            materials.put("Gold_Shovel","GOLDEN_SHOVEL");
            materials.put("Gold_Axe","GOLDEN_AXE");
        }else{
            materials.put("Command","COMMAND");
            materials.put("Book","BOOK_AND_QUILL");
            materials.put("Watch","WATCH");
            materials.put("Wood_Shovel","WOOD_SPADE");
            materials.put("Wood_Axe","WOOD_AXE");
            materials.put("Gold_Shovel","GOLD_SPADE");
            materials.put("Gold_Axe","GOLD_AXE");
        }
        return materials;
    }

}
