package io.wildernesstp.util;

import io.wildernesstp.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    final Map<UUID, Long> cooldowns = new HashMap<>();
    final Main main;
    final long cooldown;

    public CooldownManager(Main main){
        this.main = main;
        this.cooldown = main.getConfig().getLong("Cooldown");
    }

    public void setCooldown(Player p) {
        if (cooldowns.containsKey(p.getUniqueId())) {
            cooldowns.replace(p.getUniqueId(), cooldown);
        } else {
            cooldowns.put(p.getUniqueId(), cooldown);
        }
    }

    public boolean hasCooldown(Player p) {
        return cooldowns.containsKey(p.getUniqueId()) && (cooldowns.get(p.getUniqueId()) - System.currentTimeMillis()) > 0;
    }

    public long getCooldown(Player p) {
        return cooldowns.get(p.getUniqueId());
    }

}
