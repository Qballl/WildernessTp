package io.wildernesstp.portal;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    private final World worldTo;
    private final String biome;

    public Portal(Location posOne, Location posTwo,World worldTo,String biome) {
        this.world = posOne.getWorld();
        this.posOne = posOne;
        this.posTwo = posTwo;
        this.worldTo = worldTo;
        this.biome = biome;
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

    public World getWorldTo() {
        return worldTo;
    }

    public String getBiome(){
        return biome;
    }

    public void generate() {
        iterator().forEachRemaining(vector -> world.getBlockAt(vector.toLocation(world)).setType(Material.WATER,false));
    }

    public void degenerate() {
        iterator().forEachRemaining(vector -> world.getBlockAt(vector.toLocation(world)).setType(Material.AIR));
    }

    public boolean contains(Vector position) {
        double x = position.getBlockX();
        double y = position.getBlockY();
        double z = position.getBlockZ();

        Vector min = Vector.getMinimum(getPositionOne().toVector(),getPositionTwo().toVector());
        Vector max = Vector.getMaximum(getPositionOne().toVector(),getPositionTwo().toVector());

        if (x >= min.getBlockX() && x <= max.getBlockX()
            && y >= min.getBlockY() && y <= max.getBlockY()
            && z >= min.getBlockZ() && z <= max.getBlockZ())
            return true;
        else
            return false;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Portal))
            return false;
        Portal p = (Portal)obj;
        return p.getWorld().equals(this.getWorld())&&p.getPositionOne().equals(this.getPositionOne())&&
            p.getPositionTwo().equals(this.getPositionTwo());

    }

    @Override
    public int hashCode() {
        return Objects.hash(world, posOne, posTwo);
    }

    @Override
    public String toString() {
        return String.format("%s %d,%d,%d,:%d,%d,%d,%s",posOne.getWorld().getName(),posOne.getBlockX(),posOne.getBlockY(),posOne.getBlockZ(),
            posTwo.getBlockX(),posTwo.getBlockY(),posTwo.getBlockZ(),worldTo.getName());
    }


    public Iterator<Vector> iterator() {
        return new Iterator<Vector>() {
            private final Vector min = Vector.getMinimum(getPositionOne().toVector(), getPositionTwo().toVector());
            private final Vector max = Vector.getMaximum(getPositionOne().toVector(), getPositionTwo().toVector());
            private int nextX = min.getBlockX();
            private int nextY = min.getBlockY();
            private int nextZ = min.getBlockZ();

            @Override
            public boolean hasNext() {
                return (nextX != Integer.MIN_VALUE);
            }

            @Override
            public Vector next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Vector answer = new Vector(nextX, nextY, nextZ);
                if (++nextX > max.getBlockX()) {
                    nextX = min.getBlockX();
                    if (++nextZ > max.getBlockZ()) {
                        nextZ = min.getBlockZ();
                        if (++nextY > max.getBlockY()) {
                            nextX = Integer.MIN_VALUE;
                        }
                    }
                }
                return answer;
            }
        };
    }
}
