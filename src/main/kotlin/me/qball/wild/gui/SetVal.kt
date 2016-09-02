package me.qball.wild.gui

import me.qball.wild.Wild
import me.qball.wild.commands.CmdWildTp
import me.qball.wild.util.WorldInfo
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.scheduler.BukkitRunnable

class SetVal(private val plugin: Wild) : Listener {

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val p = e.player

        if (MainGui.editMode(p)) {
            val value = e.message
            e.isCancelled = true
            object : BukkitRunnable() {

                override fun run() {

                    if (value.equals("exit", ignoreCase = true) || value.equals("cancel", ignoreCase = true)) {
                        MainGui.removeEdit(p)
                        if (InvClick.Add.contains(p.uniqueId)) {
                            InvClick.Add.remove(p.uniqueId)
                        } else if (InvClick.Set.contains(p.uniqueId)) {
                            InvClick.Set.remove(p.uniqueId)
                        } else if (InvClick.Messages.contains(p.uniqueId)) {
                            InvClick.Messages.remove(p.uniqueId)
                        } else if (InvClick.Sounds.contains(p.uniqueId)) {
                            InvClick.Sounds.remove(p.uniqueId)
                        }

                        p.sendMessage("${ChatColor.GREEN} You have exited edit mode. Game play will return to normal")
                    } else {
                        if (InvClick.Set.contains(p.uniqueId)) {
                            InvClick.Set.remove(p.uniqueId)
                            val `val` = InvClick.toSet.get(0)
                            InvClick.toSet.clear()
                            var x = value
                            x = x.replace("[^\\d-]".toRegex(), "")
                            val X = Integer.parseInt(x)
                            plugin.config.set(`val`, X as Any)
                            p.sendMessage("${ChatColor.GREEN}You have set the " + `val`)
                            plugin.saveConfig()
                            MainGui.removeEdit(p)
                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                        } else if (InvClick.Messages.contains(p.uniqueId)) {
                            InvClick.Messages.remove(p.uniqueId)
                            val message = value
                            val `val` = InvClick.toSet.get(0)
                            InvClick.toSet.clear()
                            plugin.getConfig().set(`val`, message)
                            p.sendMessage("${ChatColor.GREEN}You have set the " + `val` + " message")
                            plugin.saveConfig()
                            MainGui.removeEdit(p)
                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                        } else if (InvClick.Add.contains(p.uniqueId)) {

                            InvClick.Add.remove(p.uniqueId)
                            val message = value
                            val `val` = InvClick.toSet.get(0)
                            InvClick.toSet.clear()
                            if (`val`.equals("potions", ignoreCase = true)) {

                                val Potions = plugin.getListPots()
                                Potions.add(message)
                                plugin.getConfig().set("Potions", Potions)
                                plugin.saveConfig()
                                p.sendMessage("You have added $message to the list of potions")

                            } else if (`val`.equals("worlds", ignoreCase = true) && !CmdWildTp.dev.contains(p.uniqueId)) {
                                val info = message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                val world = WorldInfo(plugin)
                                world.setWorldName(info[0])
                                world.setMinX(info[0], Integer.parseInt(info[1]))
                                world.setMaxX(info[0], Integer.parseInt(info[2]))
                                world.setMinZ(info[0], Integer.parseInt(info[3]))
                                world.setMaxZ(info[0], Integer.parseInt(info[4]))
                                p.sendMessage("${ChatColor.GREEN}You have added " + message + " to the allowed worlds")


                            }
                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                            MainGui.removeEdit(p)
                        } else if (InvClick.Sounds.contains(p.uniqueId)) {
                            InvClick.Sounds.remove(p.uniqueId)
                            val message = value
                            val `val` = InvClick.toSet.get(0)
                            InvClick.toSet.clear()
                            plugin.getConfig().set(`val`, message)
                            p.sendMessage("${ChatColor.GREEN}You have set the " + `val` + " as the sound that will be heard")
                            plugin.saveConfig()
                            MainGui.removeEdit(p)
                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                        }
                    }


                }

            }.runTaskLater(plugin, 1)
        }
    }
}


