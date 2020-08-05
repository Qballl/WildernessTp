package io.wildernesstp.hook;

import io.wildernesstp.Main;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import us.forseth11.feudal.core.Feudal;

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
public final class FeudalHook extends Hook {

    private final Main main;

    public FeudalHook(Main main) {
        super("Feudal");
        this.main = main;
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @Override
    public boolean isClaim(Location loc) {
        int distance = main.getConfig().getInt("distance");
        if (Feudal.getAPI().getKingdom(loc) != null)
            return true;
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                loc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z, loc.getPitch(), loc.getYaw());
                if (Feudal.getAPI().getKingdom(loc) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
