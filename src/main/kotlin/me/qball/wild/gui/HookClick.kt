package me.qball.wild.gui

import me.qball.wild.Wild
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.ArrayList

class HookClick(private val plugin: Wild) : Listener {
    private val toSet = ArrayList<String>()
    private lateinit var `val`: String

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.inventory.name.equals("Hooks", ignoreCase = true)) {
            try {
                e.isCancelled = true
                val item = e.currentItem
                val meta = item.itemMeta
                val name = meta.displayName.toLowerCase()
                when (name) {
                    "towny hook" -> {
                        toSet.add("Towny")
                        e.whoClicked.closeInventory()
                        TrueFalseGui.openTrue(e.whoClicked as Player)
                    }
                    "factions hook" -> {
                        toSet.add("Factions")
                        e.whoClicked.closeInventory()
                        TrueFalseGui.openTrue(e.whoClicked as Player)
                    }
                    "griefprevention hook" -> {
                        toSet.add("GriefPrevention")
                        e.whoClicked.closeInventory()
                        TrueFalseGui.openTrue(e.whoClicked as Player)
                    }
                    "worldguard hook" -> {
                        toSet.add("WorldGuard")
                        e.whoClicked.closeInventory()
                        TrueFalseGui.openTrue(e.whoClicked as Player)
                    }
                    "true" -> {
                        `val` = toSet[0]
                        toSet.clear()
                        plugin.getConfig().set(`val`, true)
                        plugin.saveConfig()
                        Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                        e.whoClicked.closeInventory()
                        MainGui.removeEdit(e.whoClicked as Player)
                    }
                    "false" -> {
                        `val` = toSet[0]
                        toSet.clear()
                        plugin.getConfig().set(`val`, false)
                        plugin.saveConfig()
                        Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
                        e.whoClicked.closeInventory()
                        MainGui.removeEdit(e.whoClicked as Player)
                    }
                    "kingdom hook" -> {
                        toSet.add("Kingdoms")
                        e.whoClicked.closeInventory()
                        TrueFalseGui.openTrue(e.whoClicked as Player)
                    }
                    else -> {
                        e.whoClicked.closeInventory()
                        MainGui.removeEdit(e.whoClicked as Player)
                    }
                }
            } catch (ex: NullPointerException) {
                Bukkit.getLogger().info(toSet.toString())
            } catch (ex: IndexOutOfBoundsException) {
                Bukkit.getLogger().info(toSet.toString())
            }

        }
    }
}


