package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object MessageGui {
    fun openMessGui(p: Player) {
        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        val lore = ArrayList<String>()
        lore.add("Click to close the inventory and return to normal gameplay")
        meta.lore = lore
        Close.itemMeta = meta
        val Messages = Bukkit.createInventory(p, 18, "WildTp")
        p.openInventory(Messages)
        Messages.setItem(0, Teleport())
        Messages.setItem(2, NoSuit())
        Messages.setItem(4, Cost())
        Messages.setItem(6, NoBreak())
        Messages.setItem(8, NoPerm())
        Messages.setItem(10, Cool())
        Messages.setItem(12, WarmUp())
        Messages.setItem(14, UsedCmd())
        Messages.setItem(17, Close)
    }

    fun Teleport(): ItemStack {
        val Teleport = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = Teleport.itemMeta
        meta.displayName = "Teleport"
        val lore = ArrayList<String>()
        lore.add("Click to set the message to be said on teleport")
        meta.lore = lore
        Teleport.itemMeta = meta
        return Teleport
    }

    fun NoSuit(): ItemStack {
        val NoSuit = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = NoSuit.itemMeta
        meta.displayName = "NoSuit"
        val lore = ArrayList<String>()
        lore.add("Click to set the no suitable location message")
        meta.lore = lore
        NoSuit.itemMeta = meta
        return NoSuit
    }

    fun Cost(): ItemStack {
        val cost = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = cost.itemMeta
        meta.displayName = "CostMsg"
        val lore = ArrayList<String>()
        lore.add("Click to set the cost message")
        meta.lore = lore
        meta.lore = lore
        cost.itemMeta = meta
        return cost
    }

    fun NoBreak(): ItemStack {
        val NoBreak = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = NoBreak.itemMeta
        meta.displayName = "No-Break"
        val lore = ArrayList<String>()
        lore.add("Click to set the no perm for sign brak message")
        meta.lore = lore
        NoBreak.itemMeta = meta
        return NoBreak
    }

    fun NoPerm(): ItemStack {
        val NoPerm = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = NoPerm.itemMeta
        meta.displayName = "No-Perm"
        val lore = ArrayList<String>()
        lore.add("Click to set the No permission to make a sign message")
        meta.lore = lore
        NoPerm.itemMeta = meta
        return NoPerm
    }

    fun Cool(): ItemStack {
        val Cool = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = Cool.itemMeta
        meta.displayName = "Cooldown Message"
        val lore = ArrayList<String>()
        lore.add("Click to set the cool down message")
        meta.lore = lore
        Cool.itemMeta = meta
        return Cool
    }

    fun WarmUp(): ItemStack {
        val Warm = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = Warm.itemMeta
        meta.displayName = "Wait/WarmUp Message"
        val lore = ArrayList<String>()
        lore.add("Click to set the warmp/delay/wait message")
        meta.lore = lore
        Warm.itemMeta = meta
        return Warm
    }

    fun UsedCmd(): ItemStack {
        val Use = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = Use.itemMeta
        meta.displayName = "Used command Message"
        val lore = ArrayList<String>()
        lore.add("Click to set the command used message")
        meta.lore = lore
        Use.itemMeta = meta
        return Use
    }
}