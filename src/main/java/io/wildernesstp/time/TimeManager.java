package io.wildernesstp.time;

import io.wildernesstp.Main;
import io.wildernesstp.util.Manager;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
public final class TimeManager extends Manager {

    private final Timer timer = new Timer(this);
    private final Map<UUID, Integer> warmups = new ConcurrentHashMap<>();
    private final Map<UUID, Integer> cooldowns = new ConcurrentHashMap<>();

    public TimeManager(Main plugin) {
        super(plugin);

        plugin.getExecutorService().scheduleAtFixedRate(timer, 1, 1, TimeUnit.SECONDS);
    }

    public Timer getTimer() {
        return timer;
    }

    public Map<UUID, Integer> getWarmups() {
        return warmups;
    }

    public Map<UUID, Integer> getCooldowns() {
        return cooldowns;
    }

    public void setWarmup(Player player, int warmup) {
        if (warmups.containsKey(player.getUniqueId())) {
            warmups.replace(player.getUniqueId(), warmup);
        } else {
            warmups.put(player.getUniqueId(), warmup);
        }
    }

    public void removeWarmup(Player player) {
        warmups.remove(player.getUniqueId());
    }

    public void decreaseWarmup(Player player) {
        if (warmups.containsKey(player.getUniqueId())) {
            setWarmup(player, warmups.get(player.getUniqueId()) - 1);
        }
    }
}
