package me.Qball.Wild.GUI;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.SendMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class InvClick implements Listener {
    public static HashMap<UUID, String> toSet = new HashMap<>();
    public static ArrayList<UUID> set = new ArrayList<UUID>();
    public static ArrayList<UUID> add = new ArrayList<UUID>();
    public static ArrayList<UUID> messages = new ArrayList<UUID>();
    public static ArrayList<UUID> sounds = new ArrayList<UUID>();
    public static ArrayList<UUID> worlds = new ArrayList<>();
    public static Plugin wild = Wild.getInstance();

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
                    SendMessage.send((Player) e.getWhoClicked());
                    SendMessage.sendInfo(e.getWhoClicked());
                    break;
                case "maxx":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxX");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    SendMessage.send((Player) e.getWhoClicked());
                    SendMessage.sendInfo(e.getWhoClicked());
                    break;
                case "minz":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MinZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    SendMessage.send((Player) e.getWhoClicked());
                    SendMessage.sendInfo(e.getWhoClicked());
                    break;
                case "maxz":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    SendMessage.send((Player) e.getWhoClicked());
                    SendMessage.sendInfo(e.getWhoClicked());
                    break;
                case "cooldown":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldown");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "cost":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cost");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "retries":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retries");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "do retry":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retry");
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "distance":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Distance");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "teleport":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Teleport");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "nosuit":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No Suitable Location");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "costmsg":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Costmsg");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "no-break":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Break");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "no-perm":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Perm");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "cooldown message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldownmsg");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "potion":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Potions");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "world":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Worlds");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "sounds":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Sound");
                    sounds.add(e.getWhoClicked().getUniqueId());
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "wait":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Wait");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "wait/warmUp message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "WaitMsg");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "refund message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Refund Message");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "used command message":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "UsedCmd");
                    SendMessage.send((Player) e.getWhoClicked());
                    break;
                case "biome blacklist":
                    e.getWhoClicked().closeInventory();
                    toSet.put(e.getWhoClicked().getUniqueId(), "Blacklisted_Biomes");
                    SendMessage.send((Player) e.getWhoClicked());
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
}