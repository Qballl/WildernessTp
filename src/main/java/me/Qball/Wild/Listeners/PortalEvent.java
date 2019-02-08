package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.Region;
import me.Qball.Wild.Wild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;

public class PortalEvent implements Listener {
    Wild plugin;

    public PortalEvent(Wild plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalEnter(PlayerPortalEvent e){
        for (String name : plugin.portals.keySet()) {
            String portal = plugin.portals.get(name);
            String[] info = portal.split(":");
            if (e.getTo().getWorld().getName().equals(info[0])) {
                if (Wild.cancel.contains(e.getPlayer().getUniqueId())) {
                    return;
                } else {
                    String[] max = info[1].split(",");
                    String[] min = info[2].split(",");
                    Vector maxVec = new Vector(Integer.parseInt(max[0]), Integer.parseInt(max[1]), Integer.parseInt(max[2]));
                    Vector minVec = new Vector(Integer.parseInt(min[0]), Integer.parseInt(min[1]), Integer.parseInt(min[2]));
                    Region region = new Region(maxVec, minVec);
                    Vector vec = new Vector(e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
                    if (region.contains(vec)) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}
