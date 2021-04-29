package io.wildernesstp.hook;

import io.wildernesstp.Main;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LandsHook extends Hook {

    private final Main main;

    public LandsHook(Main main) {
        super("Lands");
        this.main = main;
    }


    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public boolean isClaim(Location loc) {
        LandsIntegration landsIntegration = new LandsIntegration(main);
        if(landsIntegration.isClaimed(loc))
            return true;
        int distance = main.getConfig().getInt("distance");
        Vector top = new Vector(loc.getX() + distance, loc.getY(), loc.getZ() + distance);
        Vector bottom = new Vector(loc.getX() - distance, loc.getY(), loc.getZ() - distance);
        for (int z = bottom.getBlockZ(); z <= top.getBlockZ(); z++) {
            for (int x = bottom.getBlockX(); x <= top.getBlockX(); x++) {
                if (landsIntegration.isClaimed(new Location(loc.getWorld(), x, loc.getWorld().getHighestBlockYAt(x, z), z)))
                    return true;
            }
        }
        return false;
    }
}
