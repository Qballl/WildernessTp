package me.qball.wild.listeners

import me.qball.wild.Wild
import me.qball.wild.util.TeleportTar
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.ArrayList
import java.util.UUID

class PlayerMoveListener(plugin: Wild) : Listener {
    private val plugin = plugin

    val moved = ArrayList<UUID>()
    val dontTele = ArrayList<UUID>()

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        if(TeleportTar.cmdUsed.contains(e.player.uniqueId)) {
            if(e.from.x != e.to.x && e.from.y != e.to.y && e.from.z != e.to.z) {
                return
            }
            TeleportTar.cmdUsed.remove(e.player.uniqueId)
            if(!moved.contains(e.player.uniqueId)) {
                moved.add(e.player.uniqueId)
                e.player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("CancelMsg")))
                plugin.cooldownCheck.remove(e.player.uniqueId)
                plugin.cooldownTime.remove(e.player.uniqueId)
                dontTele.add(e.player.uniqueId)
            }
        }
    }
}


