package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.entity.Player

class WorldInfo(plugin: Wild) {
    private val plugin = plugin

    fun getWorldName(p: Player) : String {
        val worlds = plugin.config.getConfigurationSection("Worlds")
        worlds.getKeys(false).forEach {
            if(it.equals(p.world.name)) {
                return it
            }
        }
        return ""
    }

    fun getMinX(w: String) : Int {
        return plugin.config.getInt("Worlds.$w.MinX")
    }

    fun getMaxX(w: String) : Int {
        return plugin.config.getInt("Worlds.$w.MaxX")
    }

    fun getMinZ(w: String) : Int {
        return plugin.config.getInt("Worlds.$w.MinZ")
    }

    fun getMaxZ(w: String) : Int {
        return plugin.config.getInt("Worlds.$w.MaxZ")
    }

    fun setWorldName(world: String) {
        plugin.config.createSection("Worlds.$world")
        plugin.saveConfig()
    }

    fun setMinX(world: String, min: Int) {
        plugin.config.set("Worlds.$world.MinX", min)
        plugin.saveConfig()
    }

    fun setMaxX(world: String, max: Int) {
        plugin.config.set("Worlds.$world.MaxX", max)
        plugin.saveConfig()
    }

    fun setMinZ(world: String, min: Int) {
        plugin.config.set("Worlds.$world.MinZ", min)
        plugin.saveConfig()
    }

    fun setMaxZ(world: String, max: Int) {
        plugin.config.set("Worlds.$world.MaxZ", max)
        plugin.saveConfig()
    }
}


