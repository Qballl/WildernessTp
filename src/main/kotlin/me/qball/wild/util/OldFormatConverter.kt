package me.qball.wild.util

import me.qball.wild.Wild
import java.util.ConcurrentModificationException

object OldFormatConverter {
    private val plugin = Wild.instance

    fun convert() {
        if(!plugin.config.getBoolean("Converted")) {
            try {
                val worlds = plugin.config.getStringList("Worlds")
                worlds.forEach {
                    val world = it.split(":")
                    val worldName = world[0]
                    val minX = world[1]
                    val maxX = world[2]
                    val minZ = world[3]
                    val maxZ = world[4]
                    val info = WorldInfo(plugin)
                    info.setWorldName(worldName)
                    info.setMinX(worldName, Integer.parseInt(minX))
                    info.setMaxX(worldName, Integer.parseInt(maxX))
                    info.setMinZ(worldName, Integer.parseInt(minZ))
                    info.setMaxZ(worldName, Integer.parseInt(maxZ))
                    plugin.config.getStringList("Worlds").remove(it)
                }
                plugin.config.set("Converted", true)
                worlds.clear()
                plugin.saveConfig()
            }catch(e: Exception) {
                if(e is NullPointerException || e is ConcurrentModificationException) {
                    plugin.logger.info("Already converted")
                    plugin.config.set("Converted", true)
                    plugin.saveConfig()
                }
            }
        }
    }
}


