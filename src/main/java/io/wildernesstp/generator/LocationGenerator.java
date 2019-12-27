package io.wildernesstp.generator;

import io.wildernesstp.Main;
import io.wildernesstp.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

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
public final class LocationGenerator {

    public static final String BIOME_PERMISSION = "wildernesstp.biome.{biome}";

    private static final ReentrantLock lock = new ReentrantLock();

    private final Main plugin;
    private final Set<Predicate<Location>> filters;
    private final Map<GeneratorOptions.Option, Object> options;

    public LocationGenerator(Main plugin, Set<Predicate<Location>> filters, GeneratorOptions options) {
        this.plugin = plugin;
        this.filters = filters;
        this.options = new HashMap<GeneratorOptions.Option, Object>() {{
            put(GeneratorOptions.Option.MIN_X, options.getMinX());
            put(GeneratorOptions.Option.MAX_X, options.getMaxX());
//            put(GeneratorOptions.Option.MIN_Y, options.getMinY());
//            put(GeneratorOptions.Option.MAX_Y, options.getMaxY());
            put(GeneratorOptions.Option.MIN_Z, options.getMinZ());
            put(GeneratorOptions.Option.MAX_Z, options.getMaxZ());
            put(GeneratorOptions.Option.LIMIT, options.getLimit());
        }};
    }

    public LocationGenerator(Main plugin) {
        this(plugin, new HashSet<>(), new GeneratorOptions(plugin.getConfig().getInt("retry_limit",0)));
    }

    public void addFilter(Predicate<Location> filter) {
        filters.add(filter);
    }

    public void addOption(GeneratorOptions.Option option, Object value) {
        if (options.containsKey(option)) {
            options.replace(option, value);
        } else {
            options.putIfAbsent(option, value);
        }
    }

    public void removeOption(GeneratorOptions.Option option) {
        options.remove(option);
    }

    public Optional<Location> generate(Player player, Set<Predicate<Location>> filters) throws GenerationException {
        filters.addAll(this.filters);

        final World world = player.getWorld();
        final int minX, maxX, maxZ, minZ;
        Optional<Region> region = plugin.getRegionManager().getRegion(world);

        if (!region.isPresent()) {
            throw new GenerationException("Region is not present.");
        }

        if (!region.get().getWorldTo().isEmpty()) {
            region = plugin.getRegionManager().getRegion(region.get().getWorld());
        }

        minX = region.map(Region::getMinX).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.MIN_X));
        maxX = region.map(Region::getMaxX).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.MAX_X));
//        minY = region.map(Region::getMinY).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.MIN_Y));
//        maxY = region.map(Region::getMaxY).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.MAX_Y));
        minZ = region.map(Region::getMinZ).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.MAX_Z));
        maxZ = region.map(Region::getMaxZ).orElseGet(() -> (Integer) options.get(GeneratorOptions.Option.LIMIT));

        return Optional.ofNullable(generate0(world, filters, 0, minX, maxX, minZ, maxZ));
    }

    private Location generate0(final World world, final Set<Predicate<Location>> filters, int current, int minX, int maxX, int minZ, int maxZ) throws GenerationException {
        try {
            lock.lock();

            final int x = ThreadLocalRandom.current().nextInt(minX, maxX);
            final int z = ThreadLocalRandom.current().nextInt(minZ, maxZ);
            final int y = world.getHighestBlockYAt(x, z);
            final Location loc = new Location(world, x, y, z);


            if (current++ >= (Integer) options.get(GeneratorOptions.Option.LIMIT)) {
                throw new GenerationException("Generator reached limit.");
            }

            return loc;
            //return filters.stream().allMatch(f -> f.test(loc)) ? loc : generate0(world, filters, current, minX, maxX, minZ, maxZ);
        } finally {
            lock.unlock();
        }
    }
}
