package io.wildernesstp.hook;

import org.bukkit.Location;

public class TownyHook extends Hook {

    public TownyHook(){
        super("Towny");
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public boolean isClaim(Location loc) {
        return false;
    }
}
