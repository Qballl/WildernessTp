package me.Qball.Wild.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GetHighestNether {

    public int getSolidBlock(int tempX, int tempZ, Player target) {
        for (int y = 124; y > 2; y--) {
            if (target.getWorld().getBlockAt(tempX, y, tempZ).isEmpty()) {
                Location loc = new Location(target.getWorld(), tempX, y, tempZ, 0.0F, 0.0F);
                if (target.getWorld().getBlockAt(tempX, loc.getBlockY() - 2, tempZ).isEmpty()
                        && !target.getWorld().getBlockAt(tempX, loc.getBlockY() - 4, tempZ).isEmpty()
                        && !target.getWorld().getBlockAt(tempX, loc.getBlockY() - 4, tempZ).isLiquid()) {
                    return y-3;
                }

            }
        }
        return 10;
    }


}
