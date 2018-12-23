package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.UUID;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Listeners.PlayMoveEvent;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTarget {
    public final static ArrayList<UUID> cmdUsed = new ArrayList<UUID>();
    private final Wild wild;

    public TeleportTarget(Wild plugin) {
        wild = plugin;
    }

    public void teleport(final Location loc, final Player p) {
        loc.setY(loc.getY()+5.5);
        final TeleportTarget teleportTarget = new TeleportTarget(wild);
        if (cmdUsed.contains(p.getUniqueId())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("UsedCmd")));
        } else {
            int confWait = wild.getConfig().getInt("Wait");
            cmdUsed.add(p.getUniqueId());
            String delayMsg = wild.getConfig().getString("WaitMsg");
            delayMsg = delayMsg.replaceAll("\\{wait}", String.valueOf(confWait));
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
                }
                if (PlayMoveEvent.dontTele.contains(p.getUniqueId()))
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
        PlayMoveEvent.moved.remove(p.getUniqueId());
        wild.biome.remove(p.getUniqueId());
        wild.portalUsed.remove(p.getUniqueId());
        PlayMoveEvent.dontTele.remove(p.getUniqueId());
        wild.village.remove(p.getUniqueId());
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
        String location = String.valueOf(loc.getBlockX()) + " " + String.valueOf(loc.getY()) + " " + String.valueOf(loc.getBlockZ());
        String teleport = wild.getConfig().getString("Teleport").replace("<loc>", location);
        TeleportTarget teleportTarget = new TeleportTarget(wild);
        if (!PlayMoveEvent.moved.contains(p.getUniqueId()) && !PlayMoveEvent.dontTele.contains(p.getUniqueId())) {
            cmdUsed.remove(p.getUniqueId());
            Wild.applyPotions(p);
            p.teleport(loc);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', teleport));
            if (wild.getConfig().getBoolean("Play"))
                p.playSound(loc, Sounds.getSound(), 3, 10);
            if(wild.getConfig().getBoolean("DoParticle")){
                String[] tmp = Bukkit.getVersion().split("MC: ");
                String version = tmp[tmp.length - 1].substring(0, 3);
                loc.setY(loc.getY()-1);
                if (version.equals("1.9") || version.equals("1.1"))
                    loc.getWorld().spawnParticle(Particle.valueOf(wild.getConfig().getString("Particle").toUpperCase()),loc,30,2,2,2);
                else {
                    Effect effect = Effect.valueOf(wild.getConfig().getString("Particle").toUpperCase());
                    loc.getWorld().playEffect(loc,effect,effect.getData(),2);
                }
            }
            teleportTarget.doCommands(p);
        }
        PlayMoveEvent.moved.remove(p.getUniqueId());
        wild.biome.remove(p.getUniqueId());
        wild.portalUsed.remove(p.getUniqueId());
        PlayMoveEvent.dontTele.remove(p.getUniqueId());
        wild.village.remove(p.getUniqueId());
    }
}