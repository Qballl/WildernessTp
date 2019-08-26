package io.wildernesstp.region;

import io.wildernesstp.Main;
import io.wildernesstp.generator.GeneratorOptions;
import io.wildernesstp.util.Manager;
import org.bukkit.Bukkit;
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
public final class RegionManager extends Manager {

    /**
     * Used to speed up look-ups and minimize disk-io.
     * Though, the regions are lazily cached (= Only when accessed it will be attempted to get cached).
     */
    private static final Map<Integer, Region> regionCache = new HashMap<>();
    private final ConfigurationSection root;

    public RegionManager(Main plugin) {
        super(plugin);

        this.root = plugin.getConfig().getConfigurationSection("regions");
    }

    public Region createRegion(Region region) {
        if (this.getRegion(this.getRegionId(region)).isPresent()) {
            throw new IllegalStateException("Region does already exists.");
        }

        final ConfigurationSection cs = root.createSection(String.valueOf(root.getKeys(false).size() + 1));
        cs.set("world", region.getWorld().getName());
        cs.set("min", region.getMin());
        cs.set("max", region.getMax());
        plugin.saveConfig();
        return region;
    }

    public void destroyRegion(int id) {
        root.set(String.valueOf(id), null);
        plugin.saveConfig();

        regionCache.remove(id);
    }

    public void destroyRegion(Region region) {
        destroyRegion(getRegionId(region));
    }

    public int getRegionId(Region region) {
        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getRegion(i).isPresent() && this.getRegion(i).get().equals(region)) {
                return i;
            }
        }

        return -1;
    }

    public Optional<Region> getRegion(World world) {
        return getRegions().stream().filter(r -> r.getWorld().getName().equalsIgnoreCase(world.getName())).findAny();
    }

    public Optional<Region> getRegion(int id) {
        if (regionCache.containsKey(id)) {
            return Optional.of(regionCache.get(id));
        }

        final ConfigurationSection cs = root.getConfigurationSection(String.valueOf(id));

        if (cs == null) {
            return Optional.empty();
        }

        final World world = Bukkit.getWorld(Objects.requireNonNull(cs.getString("world")));
        final int min = cs.getInt("min", GeneratorOptions.MIN_WORLD_WIDTH);
        final int max = cs.getInt("max", GeneratorOptions.MAX_WORLD_WIDTH);
        final Region region = new Region(world, min, max);

        regionCache.putIfAbsent(id, region);
        return Optional.of(region);
    }

    public Set<Region> getRegions() {
        final Set<Region> regions = new HashSet<>();

        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getRegion(i).isPresent()) {
                regions.add(this.getRegion(i).get());
            }
        }

        return regions;
    }
}
