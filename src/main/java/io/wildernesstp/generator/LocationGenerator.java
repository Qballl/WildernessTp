package io.wildernesstp.generator;

import io.wildernesstp.Main;
import io.wildernesstp.region.Region;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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

    private final Object lock = new Object();

    private final Main plugin;
    private final Set<Predicate<Location>> filters;
    private GeneratorOptions options;

    public LocationGenerator(Main plugin, Set<Predicate<Location>> filters, GeneratorOptions options) {
        this.plugin = plugin;
        this.filters = filters;
        this.options = options;
    }

    public LocationGenerator(Main plugin) {
        this(plugin, new HashSet<>(), new GeneratorOptions());
    }

    public LocationGenerator filter(Predicate<Location> filter) {
        filters.add(filter);
        return this;
    }

    public LocationGenerator options(GeneratorOptions options) {
        this.options = options;
        return this;
    }

    public Location generate(Player player, Set<Predicate<Location>> filters) {
        filters.addAll(this.filters);

        synchronized (lock) {
            final World world = player.getWorld();
            final Optional<Region> region = plugin.getRegionManager().getRegion(world);

            final int min = region.map(Region::getMin).orElseGet(() -> options.getMinX());
            final int max = region.map(Region::getMax).orElseGet(() -> options.getMaxX());

            final int x = ThreadLocalRandom.current().nextInt(min, max);
            final int z = ThreadLocalRandom.current().nextInt(min, max);
            final int y = world.getHighestBlockYAt(x, z);
            final Location loc = new Location(world, x, y, z);

            return filters.stream().allMatch(p -> p.test(loc)) ? loc : generate(player, filters);
        }
    }
}
