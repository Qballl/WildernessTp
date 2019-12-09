package io.wildernesstp.generator;

import io.wildernesstp.Main;
import io.wildernesstp.hook.Hook;
import io.wildernesstp.region.Region;
import org.bukkit.ChatColor;
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
    private final Set<Hook> hookFilters;
    private GeneratorOptions options;

    public LocationGenerator(Main plugin, Set<Predicate<Location>> filters, Set<Hook> hookFilters, GeneratorOptions options) {
        this.plugin = plugin;
        this.filters = filters;
        this.options = options;
        this.hookFilters = hookFilters;
    }

    public LocationGenerator(Main plugin) {
        this(plugin, new HashSet<>(), new HashSet<>(), new GeneratorOptions());
    }

    public LocationGenerator filter(Predicate<Location> filter) {
        filters.add(filter);
        return this;
    }

    public LocationGenerator hookFilter(Hook hook){
        hookFilters.add(hook);
        return this;
    }

    public LocationGenerator options(GeneratorOptions options) {
        this.options = options;
        return this;
    }

    public Optional<Location> generate(Player player, Set<Predicate<Location>> filters) throws GenerationException {
        filters.addAll(this.filters);

        final World world = player.getWorld();
        final int minX, maxX, maxZ, minZ;
        final Optional<Region> region = plugin.getRegionManager().getRegion(world);
        if(!region.isPresent()) {
            player.sendMessage(ChatColor.RED+"World probably isn't setup");
            return Optional.empty();
        }
        if(region.get().getWorldTo().equals("")) {
            minX = region.map(Region::getMinX).orElseGet(() -> options.getMinX());
            maxX = region.map(Region::getMaxX).orElseGet(() -> options.getMaxX());
            minZ = region.map(Region::getMinZ).orElseGet(() -> options.getMinZ());
            maxZ = region.map(Region::getMaxZ).orElseGet(() -> options.getMaxZ());
        }else{
            Optional<Region> region1 = plugin.getRegionManager().getRegion(region.get().getWorld());
            minX = region1.map(Region::getMinX).orElseGet(() -> options.getMinX());
            maxX = region1.map(Region::getMaxX).orElseGet(() -> options.getMaxX());
            minZ = region1.map(Region::getMinZ).orElseGet(() -> options.getMinZ());
            maxZ = region1.map(Region::getMaxZ).orElseGet(() -> options.getMaxZ());
        }
        return Optional.ofNullable(generate0(world, filters, 0, minX, maxX, minZ, maxZ));
    }

    private Location generate0(final World world, final Set<Predicate<Location>> filters, int current, int minX, int maxX, int minZ, int maxZ) throws GenerationException {
        synchronized (lock) {
            final int x = ThreadLocalRandom.current().nextInt(minX, maxX);
            final int z = ThreadLocalRandom.current().nextInt(minZ, maxZ);
            final int y = world.getHighestBlockYAt(x, z);
            final Location loc = new Location(world, x, y, z);

            if (current++ >= options.getLimit()) {
                return null;
            }

            return filters.stream().allMatch(f -> f.test(loc)) ? loc : generate0(world, filters, current, minX, maxX, minZ, maxZ);
        }
    }
}
