package io.wildernesstp.hook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

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
public abstract class Hook {

    private final String name;
    private final String version;
    private final String[] authors;

    public Hook(String name, String version, String[] authors) {
        this.name = name;
        this.version = (version != null ? version : "");
        this.authors = (authors != null ? authors : new String[0]);
    }

    public Hook(String name, String version) {
        this(name, version, null);
    }

    public Hook(String name, String[] authors) {
        this(name, null, authors);
    }

    public Hook(String name) {
        this(name, new String[0]);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }

    public boolean canHook() {
        final Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);

        if (p != null && p.isEnabled() && authors.length!=0) {
            return Arrays.asList(authors).equals(p.getDescription().getAuthors());
        }

        return false;
    }

    public abstract void enable();

    public abstract void disable();

    public abstract boolean isClaim(Location loc);
}
