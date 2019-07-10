package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Utils.GetRandomLocation;
import me.Qball.Wild.Wild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayRespawnEvent implements Listener {

    private final GetRandomLocation loc = new GetRandomLocation(Wild.getInstance());

    @EventHandler
    public void on(PlayerRespawnEvent e) {
        if (e.isBedSpawn() || e.getPlayer().getBedLocation() != null) {
            return;
        }

        if (e.getPlayer().hasPermission("wild.wildtp.respawn.except")) {
            return;
        }

        e.setRespawnLocation(loc.getRandomLoc(loc.getWorldInformation(e.getPlayer().getLocation()), e.getPlayer()));
    }
}
