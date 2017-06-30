package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Listeners.PlayMoveEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTarget {
    public final static ArrayList<UUID> cmdUsed = new ArrayList<UUID>();
    private final Wild wild;

    public TeleportTarget(Wild plugin) {
        wild = plugin;
    }

    public void teleport(final Location loc, final Player p) {
        final TeleportTarget teleportTarget = new TeleportTarget(wild);
        if (cmdUsed.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
        } else {
            int confWait = wild.getConfig().getInt("Wait");
            cmdUsed.add(p.getUniqueId());
            String Wait = String.valueOf(confWait);
            String delayMsg = wild.getConfig().getString("WaitMsg");
            delayMsg = delayMsg.replaceAll("\\{wait}", Wait);
            int wait = confWait * 20;
            if(p.hasPermission("wild.wildtp.wait.bypass")||wild.portalUsed.contains(p.getUniqueId()))
                wait = 0;
            if (wait > 0 ) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', delayMsg));
                new BukkitRunnable() {
                    public void run() {
                        teleportTarget.teleportPlayer(loc, p);
                    }
                }.runTaskLater(wild, wait);
                if (PlayMoveEvent.moved.contains(p.getUniqueId())) {
                    PlayMoveEvent.moved.remove(p.getUniqueId());
                } else if (PlayMoveEvent.dontTele.contains(p.getUniqueId()))
                    PlayMoveEvent.dontTele.remove(p.getUniqueId());
            } else if (wait == 0 ) {
                teleportTarget.teleportPlayer(loc, p);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (Wild.cancel.contains(p.getUniqueId()))
                            Wild.cancel.remove(p.getUniqueId());
                    }
                }.runTaskLater(wild, 60);
            }
        }
        if (PlayMoveEvent.moved.contains(p.getUniqueId()))
            PlayMoveEvent.moved.remove(p.getUniqueId());
        if(wild.biome.containsKey(p.getUniqueId()))
            wild.biome.remove(p.getUniqueId());
    }

    private void doCommands(Player p) {
        if (wild.getConfig().getString("PostCommand") == null)
            return;
        for (String command : wild.getConfig().getStringList("PostCommand")) {
            command = command.replaceAll("\\{player}", p.getDisplayName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    private void teleportPlayer(Location loc, Player p) {
        String location = String.valueOf(loc.getBlockX()) + " " + String.valueOf(loc.getBlockY()) + " " + String.valueOf(loc.getBlockZ());
        String teleport = wild.getConfig().getString("Teleport").replace("<loc>", location);
        TeleportTarget teleportTarget = new TeleportTarget(wild);
        if (!PlayMoveEvent.moved.contains(p.getUniqueId()) && !PlayMoveEvent.dontTele.contains(p.getUniqueId())) {
            cmdUsed.remove(p.getUniqueId());
            Wild.applyPotions(p);
            p.teleport(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + 3, loc.getBlockZ(), 0.0F, 0.0F));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', teleport));
            if (wild.getConfig().getBoolean("Play"))
                p.playSound(loc, Sounds.getSound(), 3, 10);
            teleportTarget.doCommands(p);
        } else if (PlayMoveEvent.moved.contains(p.getUniqueId())) {
            PlayMoveEvent.moved.remove(p.getUniqueId());
        } else if (PlayMoveEvent.dontTele.contains(p.getUniqueId()))
            PlayMoveEvent.dontTele.remove(p.getUniqueId());
        else if (wild.portalUsed.contains(p.getUniqueId()))
            wild.portalUsed.remove(p.getUniqueId());
    }
}