package io.wildernesstp.generator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final Object lock = new Object();
    private final Set<Predicate<Location>> filters;
    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public LocationGenerator(Set<Predicate<Location>> filters) {
        this.filters = filters;
    }

    public LocationGenerator() {
        this(new HashSet<>());
    }

    public LocationGenerator filter(Predicate<Location> filter) {
        filters.add(filter);
        return this;
    }

    public Location generate(Player player, Set<Predicate<Location>> filters, GeneratorOptions options) {
        filters.addAll(this.filters);

        synchronized (lock) {
            final World world = player.getWorld();
            final int x = (ThreadLocalRandom.current().nextInt(options.getMinX(), options.getMaxX()));
            final int z = ThreadLocalRandom.current().nextInt(options.getMinZ(), options.getMaxZ());
            final int y = world.getHighestBlockYAt(x, z);
            final Location loc = new Location(world, x, y, z);

            return filters.stream().allMatch(p -> p.test(loc)) ? loc : generate(player, filters, options);
        }
    }
}
