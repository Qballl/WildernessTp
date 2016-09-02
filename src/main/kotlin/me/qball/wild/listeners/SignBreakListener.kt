package me.qball.wild.listeners

import me.qball.wild.Wild
import org.bukkit.ChatColor
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class SignBreakListener(plugin: Wild) : Listener {
    private val plugin = plugin

    @EventHandler
    fun onSignBreak(e: BlockBreakEvent) {
        val noPerm = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("No-Break"))
        val state = e.block.state
        if(state is Sign) {
            if(state.getLine(0).equals("${ChatColor.DARK_RED}====================") && state.getLine(1).equals(
                    "[${ChatColor.DARK_BLUE}Wild${ChatColor.BLACK}]", true) && state.getLine(2).equals(
                    "${ChatColor.DARK_RED}====================")) {
                if(!e.player.hasPermission("wild.wildtp.break.sign")) {
                    e.player.sendMessage(noPerm)
                    e.isCancelled = true
                }else {
                    e.player.sendMessage("${ChatColor.GREEN}You have broken a WildTP sign!")

                }
            }
        }
    }
}


