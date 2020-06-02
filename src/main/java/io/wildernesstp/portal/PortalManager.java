package io.wildernesstp.portal;

import io.wildernesstp.Main;
import io.wildernesstp.util.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

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
public final class PortalManager extends Manager {

    /**
     * Used to speed up look-ups and minimize disk-io.
     * Though, the portals are lazily cached (= Only when accessed it will be attempted to get cached).
     */
    private static final Map<Integer, Portal> portalCache = new HashMap<>();
    private static final Set<PortalEditSession> sessionCache = new HashSet<>();

    private final ConfigurationSection root;

    public PortalManager(Main plugin) {
        super(plugin);
        fillCache();
        this.root = plugin.getConfig().getConfigurationSection("portals");
    }

    private void fillCache(){
        for(String portalNum : plugin.getConfig().getConfigurationSection("portals").getKeys(false)){
            final World world = Bukkit.getWorld(plugin.getConfig().getString("portals."+portalNum+".world"));
            final Double[] one = Arrays.stream(plugin.getConfig().getString("portals."+portalNum+".pos-one").split(", ")).map(Double::valueOf).toArray(Double[]::new);
            final Double[] two = Arrays.stream(plugin.getConfig().getString("portals."+portalNum+".pos-two").split(", ")).map(Double::valueOf).toArray(Double[]::new);
            final Portal portal = new Portal(
                new Location(world, one[0], one[1], one[2]),
                new Location(world, two[0], two[1], two[2]));

            portalCache.putIfAbsent(Integer.parseInt(portalNum), portal);
        }
    }

    public Portal createPortal(Portal portal) {
        if (this.getPortal(this.getPortalId(portal)).isPresent()) {
            throw new IllegalStateException("Portal does already exists.");
        }

        final ConfigurationSection cs = root.createSection(String.valueOf(root.getKeys(false).size() + 1));
        cs.set("world", portal.getWorld().getName());
        cs.set("pos-one", String.format("%d, %d, %d", portal.getPositionOne().getBlockX(), portal.getPositionOne().getBlockY(), portal.getPositionOne().getBlockZ()));
        cs.set("pos-two", String.format("%d, %d, %d", portal.getPositionTwo().getBlockX(), portal.getPositionTwo().getBlockY(), portal.getPositionTwo().getBlockZ()));
        portalCache.put(portalCache.size(),portal);
        plugin.saveConfig();
        return portal;
    }

    public void destroyPortal(int id) {
        root.set(String.valueOf(id), null);
        plugin.saveConfig();

        portalCache.remove(id);
    }

    public void destroyPortal(Portal portal) {
        destroyPortal(getPortalId(portal));
    }

    public int getPortalId(Portal portal) {
        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getPortal(i).isPresent() && this.getPortal(i).get().equals(portal)) {
                return i;
            }
        }

        return -1;
    }

    public Optional<Portal> getPortal(int id) {
        if (portalCache.containsKey(id)) {
            return Optional.of(portalCache.get(id));
        }

        final ConfigurationSection cs = root.getConfigurationSection(String.valueOf(id));

        if (cs == null) {
            return Optional.empty();
        }

        final World world = Bukkit.getWorld(Objects.requireNonNull(cs.getString("world")));
        final Double[] one = Arrays.stream(Objects.requireNonNull(cs.getString("pos-one")).split(", ")).map(Double::valueOf).toArray(Double[]::new);
        final Double[] two = Arrays.stream(Objects.requireNonNull(cs.getString("pos-two")).split(", ")).map(Double::valueOf).toArray(Double[]::new);
        final Portal portal = new Portal(
            new Location(world, one[0], one[1], one[2]),
            new Location(world, two[0], two[1], two[2]));

        portalCache.putIfAbsent(id, portal);
        return Optional.of(portal);
    }

    public Set<Portal> getPortals() {
        final Set<Portal> portals = new HashSet<>();

        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getPortal(i).isPresent()) {
                portals.add(this.getPortal(i).get());
            }
        }

        return portals;
    }

    public Optional<Portal> getNearbyPortal(Player player, int radius) {
        return portalCache.values().stream().filter(p -> p.getPositionOne().distance(player.getLocation()) <= radius || p.getPositionTwo().distance(player.getLocation()) <= radius).findAny();
    }

    /*public Optional<Portal> getNearbyPortal(Player player, int radius){
        for(Portal p : portalCache.values()){
            player.sendMessage(p.toString());
            if(p.contains(player.getLocation().toVector(),radius))
                return Optional.of(p);
        }
        return Optional.empty();
    }*/

    public PortalEditSession startSession(Player player) {
        if (sessionCache.stream().anyMatch(s -> s.getPlayer().equals(player))) {
            throw new IllegalStateException("Cannot start session twice.");
        }

        final PortalEditSession session = new PortalEditSession(player);
        sessionCache.add(session);

        return session;
    }

    public void endSession(Player player) {
        sessionCache.removeIf(s -> s.getPlayer().equals(player));
    }

    public Optional<PortalEditSession> getSession(Player player) {
        return sessionCache.stream().filter(s -> s.getPlayer().equals(player)).findAny();
    }
}
