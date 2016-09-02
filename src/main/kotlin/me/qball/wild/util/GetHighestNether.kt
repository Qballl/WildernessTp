package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.entity.Player

object GetHighestNether {
    private val plugin = Wild.instance
    private val randomLoc = GetRandomLocation()

    fun getSolidBlock(x: Int, z: Int, target: Player) : Int {
        var y = 0
        for(Y in 0..128) {
            y = Y
            if(target.world.getBlockAt(x, y, z).isEmpty && target.world.getBlockAt(x, y, z).isLiquid) {
                y += 2
                break
            }
        }
        return y
    }
}


