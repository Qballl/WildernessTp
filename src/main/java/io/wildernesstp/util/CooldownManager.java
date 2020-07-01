package io.wildernesstp.util;

import io.wildernesstp.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
