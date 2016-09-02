package me.qball.wild.commands

import me.qball.wild.Wild
import me.qball.wild.util.WorldInfo
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList
import java.util.UUID


class CmdWildTp(private val plugin: Wild) : CommandExecutor {

    companion object {
        val dev = ArrayList<UUID>()
    }

    override fun onCommand(cs: CommandSender, cmd: Command, label: String, args: Array<String>) : Boolean {
        if(cmd.name.equals("wildtp", true)) {
            if(cs is Player) {
                when(args.size) {
                    0 -> {
                        cs.sendMessage("${ChatColor.GOLD}-------------------Help-------------------------")
                        cs.sendMessage("${ChatColor.GOLD}* Command:       Description:                  *")
                        cs.sendMessage("${ChatColor.GOLD}* /Wild Teleports player to random location    *")
                        cs.sendMessage("${ChatColor.GOLD}* /Wild [player] Teleports the specfied player *")
                        cs.sendMessage("${ChatColor.GOLD}* to a radom location                          *")
                        cs.sendMessage("${ChatColor.GOLD}* /WildTp reload Reloads the plugin's config   *")
                        cs.sendMessage("${ChatColor.GOLD}* /WildTp set <minx,maxX,minz,maxz,cool,cost,  *")
                        cs.sendMessage("${ChatColor.GOLD}* sound> allow you to set the min and max x    *")
                        cs.sendMessage("${ChatColor.GOLD}* and z and cooldown and cost and sound for    *")
                        cs.sendMessage("${ChatColor.GOLD}* using the command                            *")
                        cs.sendMessage("${ChatColor.GOLD}* /Wildtp gui to open the gui to set values    *")
                        cs.sendMessage("${ChatColor.GOLD}* /WildTp Shows wild help message              *")
                        cs.sendMessage("${ChatColor.GOLD}------------------------------------------------")
                    }
                    else -> {
                        if(args[0].equals("reload", true)) {
                            if (!cs.hasPermission("wild.wildtp.reload")) {
                                cs.sendMessage("${ChatColor.RED}Sorry you do not have permission to reload the plugin")
                            } else {
                                plugin.reload(cs)
                            }
                        }
                        if(args[0].equals("set", true)) {
                            if (cs.hasPermission("wild.wildtp.set")) {
                                if (args.size >= 2) {
                                    val Set = args[1]
                                    val set = Set.toLowerCase()

                                    when (set) {

                                        "minx" -> if (args.size >= 3) {
                                            val x = args[2]
                                            val X = Integer.parseInt(x)
                                            plugin.config.set("MinX", X as Any)
                                            cs.sendMessage("${ChatColor.GREEN}You have set the MinX")
                                            plugin.saveConfig()
                                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                        } else {
                                            cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")
                                        }

                                        "maxx" ->

                                            if (args.size >= 3) {
                                                val x = args[2]
                                                val X = Integer.parseInt(x)
                                                plugin.config.set("MaxX", X as Any)
                                                cs.sendMessage("${ChatColor.GREEN}You have set the MaxX")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")
                                            }

                                        "minz" ->

                                            if (args.size >= 3) {
                                                val x = args[2]
                                                val X = Integer.parseInt(x)
                                                plugin.config.set("MinZ", X as Any)
                                                cs.sendMessage("${ChatColor.GREEN}You have set the MinZ")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")
                                            }


                                        "maxz" ->

                                            if (args.size >= 3) {
                                                val x = args[2]
                                                val X = Integer.parseInt(x)
                                                plugin.config.set("MaxZ", X as Any)
                                                cs.sendMessage("${ChatColor.GREEN}You have set the MaxZ")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")

                                            }
                                        "cool" ->

                                            if (args.size >= 3) {
                                                val x = args[2]
                                                val X = Integer.parseInt(x)
                                                plugin.config.set("Cooldown", X as Any)
                                                cs.sendMessage("${ChatColor.GREEN}You have set the cooldown")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")

                                            }
                                        "cost" ->

                                            if (args.size >= 3) {
                                                val x = args[2]
                                                val X = Integer.parseInt(x)
                                                plugin.config.set("Cost", X as Any)
                                                cs.sendMessage("${ChatColor.GREEN}You have set the cost for using the command")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")

                                            }
                                        "sound" ->


                                            if (args.size >= 3) {
                                                val sb = StringBuilder()

                                                for (i in 3..3) {
                                                    sb.append(" ").append(args[i])
                                                }
                                                plugin.config.set("MaxX", sb.toString())
                                                cs.sendMessage("${ChatColor.GREEN}You have set the Sound")
                                                plugin.saveConfig()
                                                Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                            } else {
                                                cs.sendMessage("${ChatColor.DARK_RED}You must specify a value")
                                            }

                                        else -> cs.sendMessage("${ChatColor.RED}Only enter minx,minz,maxx,maxz,cool,or costor sound")
                                    }//end switch


                                }//args length 2
                                else {
                                    cs.sendMessage("${ChatColor.RED} Please enter minx,minz,maxx,maxz,cool,or cost")
                                }

                            }//perm set
                            else {
                                cs.sendMessage("You dont have permssion to set the x or z values")

                            }
                        }
                        if(args[0].equals("add", true)) {
                            if (cs.hasPermission("wild.wildtp.set")) {
                                if (args.size >= 2) {
                                    val add = args[1].toLowerCase()
                                    when (add) {
                                        "world" -> if (args.size >= 7) {

                                            val worldInfo = WorldInfo(plugin)
                                            worldInfo.setWorldName(args[2])
                                            worldInfo.setMinX(args[2], Integer.parseInt(args[3]))
                                            worldInfo.setMaxX(args[2], Integer.parseInt(args[4]))
                                            worldInfo.setMinZ(args[2], Integer.parseInt(args[5]))
                                            worldInfo.setMaxZ(args[2], Integer.parseInt(args[6]))
                                            cs.sendMessage("${ChatColor.GREEN}You have added ${args[2]} to the allowed worlds")
                                        } else {
                                            cs.sendMessage("${ChatColor.RED}Please enter a world")
                                        }
                                        "potion" -> if (args.size >= 3) {
                                            val potion = args[2]

                                            val potions = plugin.getListPots()
                                            potions.add(potion)
                                            plugin.config.set("Potions", potions)
                                            plugin.saveConfig()
                                            cs.sendMessage("You have added $potion to the list of potions")
                                            Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                                        } else {
                                            cs.sendMessage("Please enter a potion")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}


