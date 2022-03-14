package io.wildernesstp.gui;

import io.wildernesstp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetupGUI {

    private static Inventory gui;
    private static final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(2000, 5000, 15000, 30000, 50000, 70000, 90000, 100000));

    public static void showSetupGui(Player p){
       gui = Bukkit.createInventory(p,36,"WildernessTp setup: World");
       getWorlds(gui);
       p.openInventory(gui);
        //showWorldToGui(p);
    }

    private static void getWorlds(Inventory gui){
     int i = 0;
        for(World w : Bukkit.getWorlds()){
            ItemStack world = new ItemStack(Material.MAP);
            ItemMeta meta = world.getItemMeta();
            meta.setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+w.getName());
            world.setItemMeta(meta);
            if(i == 5)
                i++;
            gui.setItem(i,world);
            i+=2;
        }
    }

    public static void showWorldToGui(Player p){
        gui = Bukkit.createInventory(p,36,"WildernessTp setup: WorldTo");
        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName("Info");
        meta.setLore(Stream.of(ChatColor.RESET+""+ChatColor.AQUA+"Click the world you want",
            ChatColor.RESET+""+ChatColor.AQUA+"the command to take you",
            ChatColor.RESET+""+ChatColor.AQUA+"to when you do /wild","or click none to stay in same world").collect(Collectors.toList()));
        info.setItemMeta(meta);
        gui.setItem(4,info);
        getWorlds(gui);
        ItemStack none = new ItemStack(Material.ANVIL);
        ItemMeta noneMeta = none.getItemMeta();
        noneMeta.setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+"None");
        none.setItemMeta(noneMeta);
        gui.setItem(26,none);
        fillEmpty(gui);
        p.openInventory(gui);
    }

    public static void showMinMaxGUI(Player p, String type){
        gui = Bukkit.createInventory(p,27,"WildernessTp setup: "+type);
        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+"Info");
        meta.setLore(Collections.singletonList(ChatColor.RESET+""+
            ChatColor.AQUA+"Click whatever number you want to be the limit for "+type));
        info.setItemMeta(meta);
        gui.setItem(4,info);
        ItemStack number  = Main.getServerVer() >= 13 ? new ItemStack(Material.COMMAND_BLOCK) :
                                                        new ItemStack(Material.valueOf("COMMAND"));
        ItemMeta numMeta = number.getItemMeta();
        int invLoc = 9;
        int j = 0;
        for(int i = 0; i <= 8; i++){
            if(type.startsWith("min"))
                numMeta.setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+values.get(j)*-1+"");
            else
                numMeta.setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+values.get(j)+"");
            number.setItemMeta(numMeta);
            if(invLoc != 13)
                gui.setItem(invLoc,number);
            invLoc++;
            if(j!=7)
                j++;
        }
        fillEmpty(gui);
        p.openInventory(gui);
    }

    private static void fillEmpty(Inventory gui){
        for(int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null) {
                if (Main.getServerVer() > 13)
                    gui.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                else {
                    ItemStack glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"));
                    glass.setDurability((short) 7);
                    gui.setItem(i, glass);
                }
            }
        }

        /*if (Main.getServerVer() > 13)
            gui.setItem(13, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        else {
            ItemStack glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"));
            glass.setDurability((short) 7);
            gui.setItem(13, glass);
        }*/
    }

}
