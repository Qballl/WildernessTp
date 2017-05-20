package me.Qball.Wild.Utils;

import java.util.ArrayList;
import java.util.List;

import me.Qball.Wild.Wild;
import org.bukkit.potion.PotionEffectType;

public class CheckConfig {
    Wild wild = Wild.getInstance();


    public boolean isCorrectPots() {
        ArrayList<String> potions = new ArrayList<>();
        List<String> pots = wild.getListPots();
        for(PotionEffectType effect : PotionEffectType.values())
            potions.add(effect.getName());
        for (String s : pots) {
            String[] pot = s.split(":");
            if (pot.length != 2 || !potions.contains(pot[0])) {
                return false;
            }
        }

        return true;
    }
} 