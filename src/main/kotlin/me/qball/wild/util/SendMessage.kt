package me.qball.wild.util

import org.bukkit.ChatColor
import org.bukkit.entity.Player

object SendMessage {
    fun send(p: Player) {
        p.sendMessage("${ChatColor.GREEN}Now just type in chat to set the desired value")
    }
}


