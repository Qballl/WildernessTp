package me.Qball.Wild.GUI;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import me.Qball.Wild.Wild;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if (e.getInventory().getName().equalsIgnoreCase("wildtp")) {
            if (e.getCurrentItem() == null)
                return;
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String name = meta.getDisplayName().toLowerCase();
            switch (name) {
                case "close":
                    e.getWhoClicked().closeInventory();
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    if (set.contains(e.getWhoClicked().getUniqueId())) {
                        set.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (add.contains(e.getWhoClicked().getUniqueId())) {
                        add.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (messages.contains(e.getWhoClicked().getUniqueId())) {
                        messages.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (sounds.contains(e.getWhoClicked().getUniqueId())) {
                        sounds.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (toSet.containsKey(e.getWhoClicked().getUniqueId()))
                        toSet.remove(e.getWhoClicked().getUniqueId());
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
                case "add a potion or world":
                    e.getWhoClicked().closeInventory();
                    AddGui.openMessGui((Player) e.getWhoClicked());
                    add.add(e.getWhoClicked().getUniqueId());
                    break;
                case "minx":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MinX");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    send((Player) e.getWhoClicked());
                    sendInfo(e.getWhoClicked());
                    break;
                case "maxx":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxX");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "minz":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MinZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "maxz":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "cooldown":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldown");
                    send((Player) e.getWhoClicked());
                    break;
                case "cost":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cost");
                    send((Player) e.getWhoClicked());
                    break;
                case "retries":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retries");
                    send((Player) e.getWhoClicked());
                    break;
                case "do retry":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retry");
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "distance":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Distance");
                    send((Player) e.getWhoClicked());
                    break;
                case "teleport":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Teleport");
                    send((Player) e.getWhoClicked());
                    break;
                case "nosuit":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No Suitable Location");
                    send((Player) e.getWhoClicked());
                    break;
                case "costmsg":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Costmsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "no-break":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Break");
                    send((Player) e.getWhoClicked());
                    break;
                case "no-perm":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Perm");
                    send((Player) e.getWhoClicked());
                    break;
                case "cooldown message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldownmsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "potion":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Potions");
                    send((Player) e.getWhoClicked());
                    break;
                case "world":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Worlds");
                    send((Player) e.getWhoClicked());
                    break;
                case "sounds":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Sound");
                    sounds.add(e.getWhoClicked().getUniqueId());
                    send((Player) e.getWhoClicked());
                    break;
                case "wait":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Wait");
                    send((Player) e.getWhoClicked());
                    break;
                case "wait/warmUp message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "WaitMsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "refund message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Refund Message");
                    send((Player) e.getWhoClicked());
                    break;
                case "used command message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "UsedCmd");
                    send((Player) e.getWhoClicked());
                    break;
                case "biome blacklist":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Blacklisted_Biomes");
                    send((Player) e.getWhoClicked());
                    break;
                case "blocked commands":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "BlockedCommands");
                    send((Player) e.getWhoClicked());
                    break;
                case "post commands":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "PostCommands");
                    send((Player) e.getWhoClicked());
                    break;
                case "hooks":
                    e.getWhoClicked().closeInventory();
                    HookGui.openHook((Player) e.getWhoClicked());
                    break;
                case "back":
                    e.getWhoClicked().closeInventory();
                    MainGui.OpenGUI((Player)e.getWhoClicked());
                    if (set.contains(e.getWhoClicked().getUniqueId())) {
                        set.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (add.contains(e.getWhoClicked().getUniqueId())) {
                        add.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (messages.contains(e.getWhoClicked().getUniqueId())) {
                        messages.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (sounds.contains(e.getWhoClicked().getUniqueId())) {
                        sounds.remove(e.getWhoClicked().getUniqueId());
                    }
                    break;
                default:
                    e.getWhoClicked().closeInventory();
                    MainGui.removeEdit((Player) e.getWhoClicked());
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