package me.qball.wild.gui

import me.qball.wild.Wild
import me.qball.wild.util.SendMessage
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.ArrayList
import java.util.UUID

class InvClick(private val plugin: Wild) : Listener {

    companion object {
        var toSet = ArrayList<String>()
        var Set = ArrayList<UUID>()
        var Add = ArrayList<UUID>()
        var Messages = ArrayList<UUID>()
        var Sounds = ArrayList<UUID>()
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {


        if (e.inventory.name.equals("wildtp", ignoreCase = true)) {
            try {
                e.isCancelled = true
                val item = e.currentItem
                val meta = item.itemMeta
                val name = meta.displayName.toLowerCase()
                when (name) {
                    "close" -> {
                        e.whoClicked.closeInventory()
                        MainGui.removeEdit(e.whoClicked as Player)
                        if (Set.contains(e.whoClicked.uniqueId)) {
                            Set.remove(e.whoClicked.uniqueId)
                        }
                        if (Add.contains(e.whoClicked.uniqueId)) {
                            Add.remove(e.whoClicked.uniqueId)
                        }
                        if (Messages.contains(e.whoClicked.uniqueId)) {
                            Messages.remove(e.whoClicked.uniqueId)
                        }
                        if (Sounds.contains(e.whoClicked.uniqueId)) {
                            Sounds.remove(e.whoClicked.uniqueId)
                        }
                    }
                    "messages" -> {
                        e.whoClicked.closeInventory()
                        MessageGui.openMessGui(e.whoClicked as Player)
                        Messages.add(e.whoClicked.uniqueId)
                    }
                    "set" -> {
                        e.whoClicked.closeInventory()
                        SetGui.OpenSet(e.whoClicked as Player)
                        Set.add(e.whoClicked.uniqueId)
                    }
                    "add a potion or world" -> {
                        e.whoClicked.closeInventory()
                        AddGui.openMessGui(e.whoClicked as Player)
                        Add.add(e.whoClicked.uniqueId)
                    }
                    "minx" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("MinX")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "maxx" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("MaxX")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "minz" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("MinZ")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "maxz" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("MaxZ")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "cool" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Cooldown")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "cost" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Cost")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "retries" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Retries")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "teleport" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Teleport")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "nosuit" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("No Suitable Location")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "costmsg" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Costmsg")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "no-break" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("No-Break")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "no-perm" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("No-Perm")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "cooldown message" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Cooldownmsg")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "potion" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Potions")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "world" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Worlds")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "sounds" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Sound")
                        Sounds.add(e.whoClicked.uniqueId)
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "wait" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Wait")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "wait/warmUp message" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("WaitMsg")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "used command message" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("UsedCmd")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "bome blacklist" -> {
                        e.whoClicked.closeInventory()
                        toSet.add("Blacklisted_Biomes")
                        SendMessage.send(e.whoClicked as Player)
                    }
                    "hooks" -> {
                        e.whoClicked.closeInventory()
                        HookGui.openHook(e.whoClicked as Player)
                    }
                    else -> {
                        e.whoClicked.closeInventory()
                        MainGui.removeEdit(e.whoClicked as Player)
                    }
                }
            } catch (ex: NullPointerException) {

            }

        }
    }
}
