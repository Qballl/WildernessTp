package io.wildernesstp.region;

import org.bukkit.World;

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
public final class Region {

    private final World world;
    private  int minX, maxX, minZ, maxZ;
    private  String worldTo;

    public Region(World world, int minX, int maxX, int minZ, int maxZ, String worldTo) {
        this.world = world;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.worldTo = worldTo;
    }

    public void setMinX(int minX){
        this.minX = maxX;
    }

    public void setMaxX(int maxX){
        this.maxX = maxX;
    }

    public void setMinZ(int minZ){
        this.minZ = minZ;
    }

    public void setMaxZ(int maxZ){
        this.maxZ = maxZ;
    }

    public void setWorldTo(String worldTo){
        this.worldTo = worldTo;
    }

    public World getWorld() {
        return world;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public String getWorldTo() {
        return worldTo;
    }

    @Override
    public String toString() {
        return "Region{" +
            "world=" + world.getName() +
            ", minX=" + minX +
            ", maxX=" + maxX +
            ", minZ=" + minZ +
            ", maxZ=" + maxZ +
            ", worldTo='" + worldTo + '\'' +
            '}';
    }
}
