package io.wildernesstp.generator;

import org.bukkit.Location;
import org.bukkit.World;

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
public final class LocationGenerator implements Generator<Location> {

    private static final int MIN_WORLD_WIDTH = -30_000_000;
    private static final int MAX_WORLD_WIDTH = 30_000_000;
    private static final ExecutorService service = Executors.newCachedThreadPool();

    private final World world;
    private final Set<Predicate<Location>> filters;
    private boolean hasFound;

    public LocationGenerator(World world, Set<Predicate<Location>> filters) {
        this.world = world;
        this.filters = filters;
    }

    public LocationGenerator(World world) {
        this(world, new HashSet<>());
    }

    public LocationGenerator filter(Predicate<Location> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public Location generate() {
        final int x = ThreadLocalRandom.current().nextInt(MIN_WORLD_WIDTH, MAX_WORLD_WIDTH + 1);
        final int z = ThreadLocalRandom.current().nextInt(MIN_WORLD_WIDTH, MAX_WORLD_WIDTH + 1);
        final int y = world.getHighestBlockYAt(x, z);
        final Location loc = new Location(world, x, y, z);

        return ((hasFound = filters.stream().allMatch(p -> p.test(loc))) ? loc : generate());
    }

    public boolean hasFound() {
        return hasFound;
    }
}
