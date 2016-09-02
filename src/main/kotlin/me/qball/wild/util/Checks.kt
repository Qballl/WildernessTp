package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.Location
import org.bukkit.block.Biome
import org.bukkit.entity.Player


object Checks {
    private val plugin = Wild.instance
    fun getLiquid(loc: Location) : Boolean {
        loc.y = loc.blockY - 2.0
        var x = loc.blockX
        var z = loc.blockZ
        return loc.world.getBlockAt(loc).isLiquid || loc.world.getBiome(x, z) == Biome.OCEAN || loc.world.getBiome(x, z) ==
                Biome.DEEP_OCEAN
    }

    fun inNether(target: Player) : Boolean {
        val x = target.location.blockX
        val z = target.location.blockZ
        return target.world.getBiome(x, z) == Biome.HELL
    }

    fun inEnd(target: Player) : Boolean {
        val x = target.location.blockX
        val z = target.location.blockZ
        return target.world.getBiome(x, z) == Biome.SKY
    }

    fun loadChunkIfNotLoaded(target: Player) {
        val x = target.location.blockX
        val z = target.location.blockZ
        if(!target.world.isChunkLoaded(x, z)) {
            target.world.loadChunk(x, z)
        }
    }

    fun getSolidBlockY(x: Int, z: Int, target: Player) : Int {

        var y = 0
        for(Y in 0..256) {
            if(!target.world.getBlockAt(x, y, z).isEmpty) {
                y = Y
                break
            }
        }
        return y
    }

    fun isInWorld(target: Player) : Boolean {
        val sec = plugin.config.getConfigurationSection("Worlds")
        return sec.contains(target.world.name)
    }

    fun blacklistBiome(loc: Location) : Boolean {
        val biomes = plugin.config.getStringList("Blacklisted_Biomes")
        if(biomes.size == 0) return false
        for(i in 0..biomes.size) {
            if(loc.block.biome == Biome.valueOf(biomes.get(i).toUpperCase())) {
                return true
            }
        }
        return false
    }
}


