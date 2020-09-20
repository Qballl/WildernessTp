package io.wildernesstp.util;

import io.wildernesstp.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

public class CooldownManager {

    final Map<UUID, Long> cooldowns = new HashMap<>();
    final Main main;
    final long cooldown;

    public CooldownManager(Main main){
        this.main = main;
        this.cooldown = main.getConfig().getLong("cooldown");
    }

    public void setCooldown(Player p) {
        if (cooldowns.containsKey(p.getUniqueId())) {
            cooldowns.replace(p.getUniqueId(), System.currentTimeMillis());
        } else {
            cooldowns.put(p.getUniqueId(), System.currentTimeMillis());
        }
    }

    public boolean hasCooldown(Player p) {
        /*return (cooldowns.containsKey(p.getUniqueId())
            && (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-cooldowns.get(p.getUniqueId())) >= cooldown));*/
        if(cooldowns.containsKey(p.getUniqueId())) {
            if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()- cooldowns.get(p.getUniqueId())) >= cooldown){
                cooldowns.remove(p.getUniqueId());
                return false;
            }else
                return true;
        }else
            return false;
    }

    public long getCooldown(Player p) {
        return cooldown - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - cooldowns.get(p.getUniqueId()));
    }


}
