package me.qball.wild.util

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.HashMap
import java.util.UUID

object WildTpBack {
    private val locations = HashMap<UUID, Location>()

    fun saveLoc(p: Player) {
        locations.put(p.uniqueId, p.location)
    }

    fun back(p: Player) {
        p.teleport(locations[p.uniqueId])
        locations.remove(p.uniqueId)
    }
}


