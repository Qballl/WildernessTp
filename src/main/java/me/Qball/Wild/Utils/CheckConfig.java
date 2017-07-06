package me.Qball.Wild.Utils;

import java.util.List;

import me.Qball.Wild.Wild;

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
} 