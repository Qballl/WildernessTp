package me.qball.wild.listeners

import me.qball.wild.Wild
import me.qball.wild.util.GetRandomLocation
import org.bukkit.ChatColor
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class SignClickListener(private val plugin: Wild) : Listener {
    private val random = GetRandomLocation()
    private val cooldown = plugin.config.getInt("Cooldown")
    private var cooldownMessage = plugin.config.getString("Cooldownmsg").replace("{cool}", cooldown.toString())
    private val Rem = 123
    private val cost = plugin.config.getInt("Cost")
    private val costMessage = plugin.config.getString("costMessage").replace("{cost}", cost.toString())

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {

        val target = e.player
        val sign: Sign
        if (e.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (e.clickedBlock.state is Sign) {
            sign = e.clickedBlock.state as Sign
            if (sign.getLine(1).equals("[§1Wild§0]", ignoreCase = true) && sign.getLine(0).equals("§4====================", ignoreCase = true)) {

                if (target.hasPermission("wild.wildtp.cooldown.bypass") && target.hasPermission("wild.wildtp.cost.bypass")) {
                    random.getWorldInfo(target)
                } else if (target.hasPermission("!wild.wildtp.cooldown.bypass") && target.hasPermission("wild.wildtp.cost.bypass")) {
                    if (plugin.check(target)) {
                        random.getWorldInfo(target)
                    } else {
                        val rem = Rem.toString()
                        cooldownMessage = cooldownMessage.replace("{rem}", rem)
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMessage))

                    }
                } else if (target.hasPermission("wild.wildtp.cooldown") && !target.hasPermission("wild.wildtp.cost.bypass")) {
                    if (plugin.econ.getBalance(target) >= cost) {

                        val r = plugin.econ.withdrawPlayer(target, cost.toDouble())
                        if (r.transactionSuccess()) {
                            random.getWorldInfo(target)
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', costMessage))
                        } else {
                            target.sendMessage("${ChatColor.RED}Something has gone wrong sorry but we will be unable to teleport you :( ")
                        }
                    } else {
                        target.sendMessage("${ChatColor.RED}You do not have enough money to use this command")
                    }
                } else if (!target.hasPermission("wild.wildtp.cooldown") && !target.hasPermission("wild.wildtp.cost.bypass")) {
                    if (plugin.check(target)) {
                        if (plugin.econ.getBalance(target) >= cost) {

                            val r = plugin.econ.withdrawPlayer(target, cost.toDouble())
                            if (r.transactionSuccess()) {
                                random.getWorldInfo(target)
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', costMessage))


                            } else {
                                target.sendMessage("${ChatColor.RED}Something has gone wrong sorry but we will be unable to teleport you :( ")
                            }
                        } else {
                            target.sendMessage("${ChatColor.RED}You do not have enough money to use this command")
                        }

                    } else {
                        val rem = Rem.toString()
                        cooldownMessage = cooldownMessage.replace("{rem}", rem)
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMessage))
                    }
                }
            }
        }
    }
}


