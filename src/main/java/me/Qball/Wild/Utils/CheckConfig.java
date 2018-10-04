package me.Qball.Wild.Utils;

import java.util.List;

import me.Qball.Wild.Wild;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;

public class CheckConfig {
    Wild wild = Wild.getInstance();


    public boolean isCorrectPots() {
        List<String> pots = wild.getListPots();
        for (String s : pots) {
            String[] pot = s.split(":");
            if (pot.length != 2 || PotionEffectType.getByName(pot[0])==null) {
                return false;
            }
        }
        return true;
    }

    public boolean checkParticle(){
        if(wild.getConfig().getBoolean("DoParticle")) {
            try {
                String[] tmp = Bukkit.getVersion().split("MC: ");
                String version = tmp[tmp.length - 1].substring(0, 3);
                if (version.equals("1.9") || version.equals("1.1"))
                    Particle.valueOf(wild.getConfig().getString("Particle").toUpperCase());
                else
                    Effect.valueOf(wild.getConfig().getString("Particle").toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }else
            return true;
        return true;
    }
} 