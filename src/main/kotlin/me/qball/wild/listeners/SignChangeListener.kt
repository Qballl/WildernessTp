package me.qball.wild.listeners

import me.qball.wild.Wild
import me.qball.wild.util.Checks
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignChangeListener(plugin: Wild) : Listener {
    private val plugin = plugin

    @EventHandler
    fun onSignChange(e: SignChangeEvent) {
        val noPerm = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("No-Perm"))
        if(e.getLine(0).equals("[wild]", true) && e.getLine(1).equals("wildtp", true)) {
            if(e.player.hasPermission("wild.wildtp.create.sign")) {
                if(Checks.isInWorld(e.player)) {
                    e.setLine(0, "§4====================")
                    e.setLine(1, "[§1Wild§0]")
                    e.setLine(2, "§4====================")
                    e.player.sendMessage("${ChatColor.GREEN}Sucessfully made a new WildTP sign!")
                }else {
                    e.player.sendMessage("${ChatColor.RED}Signs cannot be placed in this world as the command isnt allowed in this world")
                    e.block.breakNaturally()
                    e.isCancelled = true
                }
            }else {
                e.player.sendMessage(noPerm)
                e.isCancelled = true
            }
        }
    }
}


