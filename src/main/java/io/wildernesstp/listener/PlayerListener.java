package io.wildernesstp.listener;

import io.wildernesstp.Main;
import io.wildernesstp.portal.PortalEditSession;
import io.wildernesstp.util.TeleportManager;
import io.wildernesstp.util.WTPConstants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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

    private static final String SIGN_CREATE_PERMISSION = "wildernesstp.sign.create";
    private static final String SIGN_CREATE_BIOME_PERMISSION = SIGN_CREATE_PERMISSION + ".%s";
    private static final String SIGN_USE_PERMISSION = "wildernesstp.sign.use";
    private static final String SIGN_USE_BIOME_PERMISSION = SIGN_USE_PERMISSION + ".%s";

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        final Player player = e.getPlayer();


        if (e.getItem() != null && e.getItem().equals(WTPConstants.WAND)) {//Somehow something is null
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
        } else {
            if(e.getClickedBlock() == null)
                return;
            if (e.getClickedBlock().getState() instanceof Sign) {
                if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                    return;
                final Sign sign = (Sign) e.getClickedBlock().getState();
                final String[] lines = sign.getLines();

                if (Stream.of(ChatColor.DARK_BLUE + "[WildernessTP]", ChatColor.DARK_BLUE + "[WTP]").anyMatch(s -> Objects.requireNonNull(lines[0]).equalsIgnoreCase(s)))
                {
                    if (e.getPlayer().hasPermission(SIGN_USE_PERMISSION)) {
                        if (lines[1] != null && !lines[1].isEmpty()) {
                            if (e.getPlayer().hasPermission(String.format(SIGN_USE_BIOME_PERMISSION, lines[1].toLowerCase()))) {
                                if (plugin.getPortalManager().getNearbyPortal(e.getPlayer(), 1).isPresent()) {
                                    if (!e.getPlayer().hasPermission("wildernesstp.bypass.cooldown") &&
                                        plugin.getCooldownManager().hasCooldown(e.getPlayer())) {
                                        e.getPlayer().sendMessage(plugin.getLanguage().general().cooldown().replace("{wait}",
                                            String.valueOf(TimeUnit.MILLISECONDS.toSeconds(plugin.getCooldownManager().getCooldown(e.getPlayer())))));
                                        return;
                                    } else {
                                        plugin.getCooldownManager().setCooldown(e.getPlayer());
                                        plugin.teleport(player, Collections.singleton((l) -> l.getBlock().getBiome() == Biome.valueOf(lines[1].toUpperCase())));
                                        e.setCancelled(true);
                                        e.setUseInteractedBlock(Event.Result.DENY);
                                        e.setUseItemInHand(Event.Result.DENY);
                                    }
                                } else {
                                    e.getPlayer().sendMessage(String.format("No permission to use WTP sign (biome: %s).", lines[1]));
                                    e.setCancelled(true);
                                    e.setUseInteractedBlock(Event.Result.DENY);
                                    e.setUseItemInHand(Event.Result.DENY);
                                }
                            }
                        } else {
                            plugin.teleport(player);
                            e.setCancelled(true);
                            e.setUseInteractedBlock(Event.Result.DENY);
                            e.setUseItemInHand(Event.Result.DENY);
                        }
                    } else {
                        e.getPlayer().sendMessage("No permission to use WTP sign.");
                        e.setCancelled(true);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        e.setUseItemInHand(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerMoveEvent e) {
        if (e.getTo().getBlockX() == e.getFrom().getBlockX() &&
            e.getTo().getBlockY() == e.getFrom().getBlockY() &&
            e.getTo().getBlockZ() == e.getFrom().getBlockZ())
            return;
        if (plugin.getPortalManager().getNearbyPortal(e.getPlayer()).isPresent()) {
        //if (plugin.getPortalManager().getNearbyPortal(e.getTo()).isPresent()) {
            /*if(!e.getPlayer().hasPermission("wildernesstp.bypass.cooldown") &&
                plugin.getCooldownManager().hasCooldown(e.getPlayer())){
                e.getPlayer().sendMessage(plugin.getLanguage().general().cooldown().replace("{wait}",
                    String.valueOf(TimeUnit.MILLISECONDS.toSeconds(plugin.getCooldownManager().getCooldown(e.getPlayer())))));
            }
            else {*/
                plugin.getCooldownManager().setCooldown(e.getPlayer());
                plugin.teleport(e.getPlayer());
            //}
        }

        if (TeleportManager.checkTeleport(e.getPlayer().getUniqueId()) && plugin.getConfig().getInt("delay") > 0) {
            TeleportManager.moved(e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(plugin.getLanguage().general().moved());
        }

    }

    @EventHandler
    public void on(SignChangeEvent e) {
        final String[] lines = e.getLines();

        if (Stream.of("[WildernessTP]", "[WTP]").anyMatch(s -> Objects.requireNonNull(ChatColor.stripColor(lines[0])).equalsIgnoreCase(s)) && e.getPlayer().hasPermission(SIGN_CREATE_PERMISSION)) {
            e.setLine(0, ChatColor.DARK_BLUE + lines[0]);
        }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        if(e.getInventory() == null)
            return;
        if (e.getInventory().equals(WTPConstants.BIOME_SELECTOR)) {
            final ItemStack i = e.getCurrentItem();

            if (i != null && i.hasItemMeta()) {
                ((Player) e.getWhoClicked()).performCommand("/wild " + i.getItemMeta().getDisplayName().toUpperCase());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(plugin.getConfig().getBoolean("TeleportNewbies")){
            if(!e.getPlayer().hasPlayedBefore())
                plugin.generate(e.getPlayer());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
         if(plugin.getConfig().getBoolean("teleport_on_respawn")){
             plugin.getGenerator().generate(e.getPlayer(), e.getRespawnLocation().getWorld(), new HashSet<>()).ifPresent(e::setRespawnLocation);
        }
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent e){
        if(e.getBlock().getState() instanceof Sign){
            final Sign sign = (Sign) e.getBlock().getState();
            final String[] lines = sign.getLines();
            if(Stream.of(ChatColor.DARK_BLUE + "[WildernessTP]", ChatColor.DARK_BLUE + "[WTP]").anyMatch(s -> Objects.requireNonNull(lines[0]).equalsIgnoreCase(s))){
                if(!e.getPlayer().hasPermission(SIGN_CREATE_PERMISSION))
                    e.setCancelled(true);
            }
        }
    }

}
