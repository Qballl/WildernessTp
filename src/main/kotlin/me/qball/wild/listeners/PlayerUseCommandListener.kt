package me.qball.wild.listeners

import me.qball.wild.Wild
import me.qball.wild.util.TeleportTar
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerUseCommandListener(plugin: Wild) : Listener {
    private val plugin = plugin

    @EventHandler
    fun onCommandUse(e: PlayerCommandPreprocessEvent) {
        val command = e.message.toLowerCase()
        val blocked = plugin.config.getStringList("BlockedCommands")
        if(TeleportTar.cmdUsed.contains(e.player.uniqueId)) {
           blocked.forEach {
               if(command.contains(it)) {
                   e.player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Block_Command_Message")))
                   e.isCancelled = true
                   return@forEach
               }
           }
        }
    }
}


