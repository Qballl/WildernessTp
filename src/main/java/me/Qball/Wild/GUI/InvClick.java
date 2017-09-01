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
        if (e.getInventory().getName() == null)
            return;
        if (e.getInventory().getName().equalsIgnoreCase("wildtp")) {
            if (e.getCurrentItem() == null)
                return;
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String name = meta.getDisplayName().toLowerCase();
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            switch (name) {
                case "close":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
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
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MessageGui.openMessGui((Player) e.getWhoClicked());
                    messages.add(e.getWhoClicked().getUniqueId());
                    break;
                case "set":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    SetGui.OpenSet((Player) e.getWhoClicked());
                    set.add(e.getWhoClicked().getUniqueId());
                    break;
                case "add a potion or world":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    AddGui.openMessGui((Player) e.getWhoClicked());
                    add.add(e.getWhoClicked().getUniqueId());
                    break;
                case "minx":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "MinX");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    send((Player) e.getWhoClicked());
                    sendInfo(e.getWhoClicked());
                    break;
                case "maxx":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxX");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "minz":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "MinZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "maxz":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "MaxZ");
                    worlds.add(e.getWhoClicked().getUniqueId());
                    sendInfo(e.getWhoClicked());
                    break;
                case "cooldown":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldown");
                    send((Player) e.getWhoClicked());
                    break;
                case "cost":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cost");
                    send((Player) e.getWhoClicked());
                    break;
                case "retries":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retries");
                    send((Player) e.getWhoClicked());
                    break;
                case "do retry":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Retry");
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "distance":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Distance");
                    send((Player) e.getWhoClicked());
                    break;
                case "teleport":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Teleport");
                    send((Player) e.getWhoClicked());
                    break;
                case "nosuit":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "No Suitable Location");
                    send((Player) e.getWhoClicked());
                    break;
                case "costmsg":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Costmsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "no-break":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Break");
                    send((Player) e.getWhoClicked());
                    break;
                case "no-perm":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "No-Perm");
                    send((Player) e.getWhoClicked());
                    break;
                case "cooldown message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Cooldownmsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "potion":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Potions");
                    send((Player) e.getWhoClicked());
                    break;
                case "world":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Worlds");
                    send((Player) e.getWhoClicked());
                    break;
                case "sounds":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Sound");
                    sounds.add(e.getWhoClicked().getUniqueId());
                    send((Player) e.getWhoClicked());
                    break;
                case "wait":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Wait");
                    send((Player) e.getWhoClicked());
                    break;
                case "wait/warmUp message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "WaitMsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "refund message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Refund Message");
                    send((Player) e.getWhoClicked());
                    break;
                case "used command message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "UsedCmd");
                    send((Player) e.getWhoClicked());
                    break;
                case "blocked command message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Blocked_Command_Message");
                    send((Player) e.getWhoClicked());
                    break;
                case "world message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "WorldMsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "cancel message":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "CancelMsg");
                    send((Player) e.getWhoClicked());
                    break;
                case "biome blacklist":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "Blacklisted_Biomes");
                    send((Player) e.getWhoClicked());
                    break;
                case "blocked commands":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "BlockedCommands");
                    send((Player) e.getWhoClicked());
                    break;
                case "post commands":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    toSet.put(e.getWhoClicked().getUniqueId(), "PostCommands");
                    send((Player) e.getWhoClicked());
                    break;
                case "hooks":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    HookGui.openHook((Player) e.getWhoClicked());
                    break;
                case "back":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
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
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
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