package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.entity.Player
import java.util.Random

class GetRandomLocation {
    private val plugin = Wild.instance
    val worldInfo = WorldInfo(plugin)

    fun getWorldInfo(p: Player) {
        val name = worldInfo.getWorldName(p)

        val minX = worldInfo.getMinX(name)
        val maxX = worldInfo.getMaxX(name)
        val minZ = worldInfo.getMinZ(name)
        val maxZ = worldInfo.getMaxZ(name)

        getRandomLoc(p, Bukkit.getWorld(name), maxX, minX, maxZ, minZ)
    }

    fun getRandomLoc(p: Player, world: World, maxX: Int, minX: Int, maxZ: Int, minZ: Int) {
        val random = Random()

        val x = random.nextInt(maxX - minX + 1) + minX
        val z = random.nextInt(maxZ - minZ + 1) + minZ
        var y = 0

        if(p.world.getBiome(x, z) != Biome.HELL) {
            y = Checks.getSolidBlockY(x, z, p)
        }else {
            // TODO: y = GetHighestNether.getSolidBlock(x, z, p)
        }
        val loc = Location(world, x.toDouble(), y.toDouble(), z.toDouble(), 0F, 0F)
        //TODO: plugin.random(p, loc)
    }

    fun worldToString(player: Player) : String {
        val world = worldInfo.getWorldName(player)
        val minX = worldInfo.getMinX(world)
        val maxX = worldInfo.getMaxX(world)
        val minZ = worldInfo.getMinZ(world)
        val maxZ = worldInfo.getMaxZ(world)
        return "$world:$minX:$maxX:$minZ:$maxZ"
    }

    fun recallTeleport(loc: Location, player: Player) {
        TeleportTar.tp(loc, player)
    }

    fun getRandomLoc(info: String, p: Player) : Location {
        val rand = Random()
        val split = info.split(":")
        val world = Bukkit.getWorld(split[0])
        val minX = Integer.parseInt(split[1])
        val maxX = Integer.parseInt(split[2])
        val minZ = Integer.parseInt(split[3])
        val maxZ = Integer.parseInt(split[4])
        val x = rand.nextInt(maxX - minX + 1) + minX
        val z = rand.nextInt(maxZ - minZ + 1) + minZ
        val y = if(p.world.getBiome(x, z) == Biome.HELL) GetHighestNether.getSolidBlock(x, z, p) else Checks.getSolidBlockY(x, z, p)
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble(), 0F, 0F)
    }
}


