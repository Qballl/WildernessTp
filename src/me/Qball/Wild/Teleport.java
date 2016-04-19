package me.Qball.Wild;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class Teleport {
    public static Plugin wild = Wild.getInstance();
    protected static int confWait = wild.getConfig().getInt("Wait");
    public static void Teleport(final Location loc, final Player target)
    {
        int wait = confWait*20;
        if (wait >0) {
            target.sendMessage(ChatColor.RED + "Teleporting in " + confWait + " seconds.");

            new BukkitRunnable() {
                public void run() {

                     target.teleport(loc);
                }
            }.runTaskLater(wild, wait);
        }
        else
        {
            target.teleport(loc);
        }

    }


}
