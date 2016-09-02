package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object TrueFalseGui {
    fun openTrue(p: Player) {
        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        val lore = ArrayList<String>()
        lore.add("Click to close the inventory and return to normal gameplay")
        meta.lore = lore
        Close.itemMeta = meta
        val Wildtp = Bukkit.createInventory(p, 9, "Hooks")
        p.openInventory(Wildtp)
        Wildtp.setItem(2, True())
        Wildtp.setItem(5, False())
        Wildtp.setItem(8, Close)
    }

    fun True(): ItemStack {
        val True = ItemStack(Material.WOOL, 1, 5.toByte().toShort())
        val meta = True.itemMeta
        meta.displayName = "True"
        val lore = ArrayList<String>()
        lore.add("Click to to enable the hook")
        meta.lore = lore
        True.itemMeta = meta
        return True
    }

    fun False(): ItemStack {
        val wool = ItemStack(Material.WOOL, 1, 14.toByte().toShort())
        val meta = wool.itemMeta
        meta.displayName = "False"
        val lore = ArrayList<String>()
        lore.add("Click to to disable the hook")
        meta.lore = lore
        wool.itemMeta = meta
        return wool
    }
}


