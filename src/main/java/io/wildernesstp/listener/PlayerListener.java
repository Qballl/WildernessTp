package io.wildernesstp.listener;

import io.wildernesstp.Main;
import io.wildernesstp.portal.PortalEditSession;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

/**
 * MIT License
 * <p>
 * Copyright (c) 2019 Quintin VanBooven
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public final class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getItem() != null && e.getItem().equals(PortalEditSession.WAND)) {
            if (e.getClickedBlock() == null) {
                return;
            }

            Optional<PortalEditSession> session = plugin.getPortalManager().getSession(player);

            if (!session.isPresent()) {
                session = Optional.of(plugin.getPortalManager().startSession(player));
            }

            final Location loc = e.getClickedBlock().getLocation();

            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                session.get().setPosOne(loc);
                player.sendMessage(String.format("The first position has been set (X=%d, Y=%d, Z=%d).", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
            } else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                session.get().setPosTwo(e.getClickedBlock().getLocation());
                player.sendMessage(String.format("The second position has been set (X=%d, Y=%d, Z=%d).", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
            }

            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
        }
    }

    @EventHandler
    public void on(PlayerMoveEvent e) {
        if (plugin.getPortalManager().getNearbyPortal(e.getPlayer(), 1).isPresent()) {
            e.getPlayer().performCommand("/wild");
        }
    }
}
