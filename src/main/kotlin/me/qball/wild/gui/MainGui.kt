package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList
import java.util.HashMap
import java.util.UUID

object  MainGui {
    var edit: MutableMap<UUID, String> = HashMap()

    fun OpenGUI(p: Player) {
        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        val lore = ArrayList<String>()
        lore.add("Click to close the inventory and return to normal gameplay")
        meta.lore = lore
        Close.itemMeta = meta
        val Wildtp = Bukkit.createInventory(p, 18, "WildTp")
        putEdit(p)
        p.openInventory(Wildtp)
        Wildtp.setItem(17, Close)
        Wildtp.setItem(2, message())
        Wildtp.setItem(4, set())
        Wildtp.setItem(6, add())
        Wildtp.setItem(0, sounds())
        Wildtp.setItem(8, hooks())
    }

    fun editMode(p: Player): Boolean {
        if (!edit.containsKey(p.uniqueId)) {
            return false
        } else {
            return true
        }
    }

    fun putEdit(p: Player) {
        if (!edit.containsKey(p.uniqueId)) {
            edit.put(p.uniqueId, p.customName)
        }
    }

    fun removeEdit(p: Player) {
        if (edit.containsKey(p.uniqueId)) {
            edit.remove(p.uniqueId)

        }
    }

    private fun set(): ItemStack {
        val set = ItemStack(Material.PAPER, 1)
        val Set = set.itemMeta
        Set.displayName = "Set"
        val Setlore = ArrayList<String>()
        Setlore.add("Click me to set the values for x and z ")
        Setlore.add("along with cooldown and cost")
        Set.lore = Setlore
        set.itemMeta = Set
        return set
    }

    private fun message(): ItemStack {
        val Messages = ItemStack(Material.BOOK_AND_QUILL, 1)
        val message = Messages.itemMeta
        message.displayName = "Messages"
        val MessLore = ArrayList<String>()
        MessLore.add("Click to set the messages")
        message.lore = MessLore
        Messages.itemMeta = message
        return Messages
    }

    fun add(): ItemStack {
        val add = ItemStack(Material.BOOK, 1)
        val Add = add.itemMeta
        Add.displayName = "Add a potion or world"
        val lore = ArrayList<String>()
        lore.add("Click to add a potion or world")
        Add.lore = lore
        add.itemMeta = Add
        return add

    }

    fun sounds(): ItemStack {
        val sound = ItemStack(Material.JUKEBOX, 1)
        val meta = sound.itemMeta
        meta.displayName = "Sounds"
        val lore = ArrayList<String>()
        lore.add("Click me to set the sound")
        meta.lore = lore
        sound.itemMeta = meta
        return sound
    }

    fun hooks(): ItemStack {
        val hook = ItemStack(Material.TRIPWIRE_HOOK, 1)
        val meta = hook.itemMeta
        meta.displayName = "Hooks"
        val lore = ArrayList<String>()
        lore.add("Click me to enable or disable a hook")
        meta.lore = lore
        hook.itemMeta = meta
        return hook
    }
}


