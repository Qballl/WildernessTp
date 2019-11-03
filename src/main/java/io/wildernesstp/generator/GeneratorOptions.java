package io.wildernesstp.generator;

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
public final class GeneratorOptions {

    public static final int MIN_WORLD_WIDTH = -30_000_000;
    public static final int MAX_WORLD_WIDTH = 30_000_000;
    public static final int MAX_WORLD_HEIGHT = 255;
    public static final int DEFAULT_LIMIT = 10;

    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    private int limit;

    public GeneratorOptions(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int limit) {
        this.setMinX(minX);
        this.setMinY(minY);
        this.setMinZ(minZ);
        this.setMaxX(maxX);
        this.setMaxY(maxY);
        this.setMaxZ(maxZ);
        this.setLimit(limit);
    }

    public GeneratorOptions() {
        this.minX = MIN_WORLD_WIDTH;
        this.maxX = MAX_WORLD_WIDTH;
        this.minZ = MIN_WORLD_WIDTH;
        this.maxZ = MAX_WORLD_WIDTH;
        this.maxY = MAX_WORLD_HEIGHT;
        this.limit = DEFAULT_LIMIT;
    }

    public void setMinX(int minX) {
        if (minX >= maxX || minX < MIN_WORLD_WIDTH) {
            minX = MIN_WORLD_WIDTH;
        }

        this.minX = minX;
    }
    
    public void setMinY(int minY) {
        if (minY >= maxY || minY < 0) {
            minY = 0;
        }

        this.minY = minY;
    }

    public void setMinZ(int minZ) {
        if (minZ >= maxZ || minZ < MIN_WORLD_WIDTH) {
            minZ = MIN_WORLD_WIDTH;
        }

        this.minZ = minZ;
    }

    public void setMaxX(int maxX) {
        if (maxX <= minX || maxX > MAX_WORLD_WIDTH) {
            maxX = MAX_WORLD_WIDTH;
        }

        this.maxX = maxX;
    }
    
    public void setMaxY(int maxY) {
        if (maxY <= minY || maxY > MAX_WORLD_HEIGHT) {
            maxY = MAX_WORLD_HEIGHT;
        }

        this.maxY = maxY;
    }

    public void setMaxZ(int maxZ) {
        if (maxZ <= minZ || maxZ > MAX_WORLD_WIDTH) {
            maxZ = MAX_WORLD_WIDTH;
        }

        this.maxZ = maxZ;
    }

    public void setLimit(int limit) {
        if (limit <= 0) {
            limit = DEFAULT_LIMIT;
        }

        this.limit = limit;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getLimit() {
        return limit;
    }
}
