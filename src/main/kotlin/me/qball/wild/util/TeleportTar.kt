package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.ArrayList
import java.util.UUID

object TeleportTar {
    private val plugin = Wild.instance
    val confWait = plugin.config.getInt("Wait")
    val cmdUsed = ArrayList<UUID>()
    val random = GetRandomLocation()

    fun tp(loc: Location, player: Player) {
        if(cmdUsed.contains(player.uniqueId)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("UsedCmd")))
            return
        }
        cmdUsed.add(player.uniqueId)
        val waitMsg = plugin.config.getString("WaitMsg").replace("{wait}", confWait.toString())
        val tele = plugin.config.getString("teleport")
        if(plugin.config.getBoolean("Play")) {
            if(confWait > 0) {
                object : BukkitRunnable() {
                    override fun run() {
                        if(!plugin.moveListener.moved.contains(player.uniqueId) && !plugin.moveListener.dontTele.contains(player.uniqueId)) {
                            if(!Checks.blacklistBiome(loc)) {
                                cmdUsed.remove(player.uniqueId)
                                // TODO: plugin.applyPotions(player)
                                player.teleport(loc)
                                player.sendMessage(String.format("%s${ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Teleport"))}", ChatColor.GREEN))
                                // TODO: player.playSound(Sounds.sound, 3, 10)
                                if(plugin.moveListener.moved.contains(player.uniqueId)) {
                                    plugin.moveListener.moved.remove(player.uniqueId)
                                }

                            }else {
                                if(plugin.retries != 0) {
                                    val info = random.worldToString(player)
                                    random.recallTeleport(random.getRandomLoc(info, player), player)
                                }else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("No_Suitable_Location")))
                                }
                            }
                        }else {
                            plugin.moveListener.moved.remove(player.uniqueId)
                            plugin.moveListener.dontTele.remove(player.uniqueId)
                        }
                    }
                }.runTaskLater(plugin, (confWait * 20).toLong())
            }else {
                if(!Checks.blacklistBiome(loc)) {
                    // TODO plugin.applyPotion(player)
                    player.teleport(loc)
                    player.sendMessage(String.format("%s${ChatColor.translateAlternateColorCodes('&', tele)}", ChatColor.GREEN))
                    // TODO player.playSound(Sounds.getSound(), 3, 10)
                }else {
                    if(plugin.retries != 0) {
                        val toString = random.worldToString(player)
                        random.recallTeleport(random.getRandomLoc(toString, player), player)
                    }else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("No_Suitable_Location")))
                    }
                }
            }
        }else {
            if(confWait > 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', waitMsg))
                object : BukkitRunnable() {
                    override fun run() {
                        if(!plugin.moveListener.moved.contains(player.uniqueId) && plugin.moveListener.dontTele.contains(player.uniqueId)) {
                            if(!Checks.blacklistBiome(loc)) {
                                cmdUsed.remove(player.uniqueId)
                                // TODO plugin.applyPotions(player)
                                player.teleport(loc)
                                player.sendMessage(String.format("%s${ChatColor.translateAlternateColorCodes('&', tele)}", ChatColor.GREEN))
                                // TODO player.playSound(Sounds.getSound(), 3, 10)
                                plugin.moveListener.moved.remove(player.uniqueId)
                            }else {
                                if(plugin.retries != 0) {
                                    val info = random.worldToString(player)
                                    random.recallTeleport(random.getRandomLoc(info, player), player)
                                }else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("No_Suitable_Location")))
                                }
                            }
                        }else {
                            plugin.moveListener.moved.remove(player.uniqueId)
                            plugin.moveListener.dontTele.remove(player.uniqueId)
                        }
                    }
                }.runTaskLater(plugin, (confWait* 20).toLong())
            }else {
                // TODO plugin.applyPotions(player)
                player.teleport(loc)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', tele))
            }
        }
        plugin.moveListener.moved.remove(player.uniqueId)
        plugin.moveListener.dontTele.remove(player.uniqueId)
    }
}


