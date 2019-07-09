package me.Qball.Wild.GUI;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import me.Qball.Wild.Wild;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

public class InvClick implements Listener {
    public static HashMap<UUID, String> toSet = new HashMap<>();
    public static ArrayList<UUID> set = new ArrayList<UUID>();
    public static ArrayList<UUID> add = new ArrayList<UUID>();
    public static ArrayList<UUID> messages = new ArrayList<UUID>();
    public static ArrayList<UUID> sounds = new ArrayList<UUID>();
    public static ArrayList<UUID> worlds = new ArrayList<>();
    public Wild wild;

    public InvClick(Wild wild){
        this.wild = wild;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() == null)
            return;
        if (e.getView().getTitle() == null)
            return;
        if (e.getView().getTitle().equalsIgnoreCase("wildtp")) {
            if (e.getCurrentItem() == null)
                return;
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String name = meta.getDisplayName().toUpperCase().replace("/","_").replace("-","_");
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            name = name.replace(" ","_");
            String toDo = ItemNames.valueOf(name).getSet();
            switch (toDo.toLowerCase()){
                case "hooks":
                    e.getWhoClicked().closeInventory();
                    HookGui.openHook((Player) e.getWhoClicked());
                    break;
                case "back":
                    e.getWhoClicked().closeInventory();
                    MainGui.OpenGUI((Player)e.getWhoClicked());
                    set.remove(e.getWhoClicked().getUniqueId());
                    add.remove(e.getWhoClicked().getUniqueId());
                    messages.remove(e.getWhoClicked().getUniqueId());
                    sounds.remove(e.getWhoClicked().getUniqueId());
                    break;
                case "close":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    set.remove(e.getWhoClicked().getUniqueId());
                    add.remove(e.getWhoClicked().getUniqueId());
                    messages.remove(e.getWhoClicked().getUniqueId());
                    sounds.remove(e.getWhoClicked().getUniqueId());
                    break;
                case "add":
                    e.getWhoClicked().closeInventory();
                    AddGui.openMessGui((Player) e.getWhoClicked());
                    add.add(e.getWhoClicked().getUniqueId());
                    break;
                case "messages":
                    e.getWhoClicked().closeInventory();
                    MessageGui.openMessGui((Player) e.getWhoClicked());
                    messages.add(e.getWhoClicked().getUniqueId());
                    break;
                case "set":
                    e.getWhoClicked().closeInventory();
                    SetGui.OpenSet((Player) e.getWhoClicked());
                    set.add(e.getWhoClicked().getUniqueId());
                    break;
                default:
                    if(toDo.contains("Min")||toDo.contains("Max"))
                        worlds.add(e.getWhoClicked().getUniqueId());
                    toSet.put(e.getWhoClicked().getUniqueId(),toDo);
                    e.getWhoClicked().closeInventory();
                    break;
            }
        }
    }

    private static void send(Player p) {
        p.sendMessage(ChatColor.GREEN + "Now just type in chat to set the desired value");
    }

    private static void sendInfo(HumanEntity e) {
        Player p = (Player) e;
        p.sendMessage(ChatColor.GREEN + "Now type the name of the world space the value so for example world 0");
    }
}