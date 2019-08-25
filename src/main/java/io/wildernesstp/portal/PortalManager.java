package io.wildernesstp.portal;

import io.wildernesstp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

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
public final class PortalManager {

    /**
     * Used to speed up look-ups and minimize disk-io.
     * Though, the portals are lazily cached (= Only when accessed it will be attempted to get cached).
     */
    private static final Map<Integer, Portal> cache = new HashMap<>();

    private final Main plugin;
    private final ConfigurationSection root;

    public PortalManager(Main plugin) {
        this.plugin = plugin;
        this.root = plugin.getConfig().getConfigurationSection("portals");
    }

    public void createPortal(Portal portal) {
        final ConfigurationSection cs = root.createSection(String.valueOf(root.getKeys(false).size() + 1));
        cs.set("world", portal.getWorld());
        cs.set("pos-one", portal.getPositionOne().toVector());
        cs.set("pos-two", portal.getPositionTwo().toVector());
        plugin.saveConfig();
    }

    public void destroyPortal(int id) {
        root.set(String.valueOf(id), null);
        plugin.saveConfig();
    }

    public Optional<Portal> getPortal(int id) {
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }

        final ConfigurationSection cs = root.getConfigurationSection(String.valueOf(id));

        if (cs == null) {
            return Optional.empty();
        }

        final World world = Bukkit.getWorld(Objects.requireNonNull(cs.getString("world")));
        final Portal portal = new Portal(
            new Location(world, cs.getDouble("pos-one.x"), cs.getDouble("pos-one.y"), cs.getDouble("pos-one.z")),
            new Location(world, cs.getDouble("pos-two.x"), cs.getDouble("pos-two.y"), cs.getDouble("pos-two.z")));

        cache.putIfAbsent(id, portal);
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
}
