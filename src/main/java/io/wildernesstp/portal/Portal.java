package io.wildernesstp.portal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
public final class Portal {

    private static final int DEFAULT_PORTAL_WIDTH = 4;
    private static final int DEFAULT_PORTAL_HEIGHT = 8;
    private static final Material DEFAULT_PORTAL_FRAME_BLOCK = Material.QUARTZ_BLOCK;
    private static final Material DEFAULT_PORTAL_BLOCK = Material.WATER;

    private final World world;
    private final Location posOne;
    private final Location posTwo;

    public Portal(Location posOne, Location posTwo) {
        this.world = posOne.getWorld();
        this.posOne = posOne;
        this.posTwo = posTwo;
    }

    public World getWorld() {
        return world;
    }

    public Location getPositionOne() {
        return posOne;
    }

    public Location getPositionTwo() {
        return posTwo;
    }

    public void generate(Player player) {
    }

    public void degenerate(Player player) {
    }
}
