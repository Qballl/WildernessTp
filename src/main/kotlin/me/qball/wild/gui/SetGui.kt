package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object SetGui {
    fun OpenSet(p: Player) {

        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        val lore = ArrayList<String>()
        lore.add("Click to close the inventory and return to normal gameplay")
        meta.lore = lore
        Close.itemMeta = meta
        val Set = Bukkit.createInventory(p, 18, "WildTp")
        p.openInventory(Set)

        Set.setItem(0, MinX())
        Set.setItem(2, MaxX())
        Set.setItem(4, MinZ())
        Set.setItem(6, MaxZ())
        Set.setItem(8, Cool())
        Set.setItem(10, Cost())
        Set.setItem(12, delay())
        Set.setItem(14, retry())
        Set.setItem(17, Close)
    }

    fun MinX(): ItemStack {
        val MinX = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = MinX.itemMeta
        meta.displayName = "MinX"
        val lore = ArrayList<String>()
        lore.add("Click to set the minx")
        meta.lore = lore
        MinX.itemMeta = meta
        return MinX
    }

    fun MaxX(): ItemStack {
        val MaxX = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = MaxX.itemMeta
        meta.displayName = "MaxX"
        val lore = ArrayList<String>()
        lore.add("Click to set the maxx")
        meta.lore = lore
        MaxX.itemMeta = meta
        return MaxX
    }

    fun MinZ(): ItemStack {
        val MinZ = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = MinZ.itemMeta
        meta.displayName = "MinZ"
        val lore = ArrayList<String>()
        lore.add("Click to set the minz")
        meta.lore = lore
        MinZ.itemMeta = meta
        return MinZ
    }

    fun MaxZ(): ItemStack {
        val MaxZ = ItemStack(Material.BOOK_AND_QUILL, 1)
        val meta = MaxZ.itemMeta
        meta.displayName = "MaxZ"
        val lore = ArrayList<String>()
        lore.add("Click to set the maxz")
        meta.lore = lore
        MaxZ.itemMeta = meta
        return MaxZ
    }

    fun Cool(): ItemStack {
        val Cool = ItemStack(Material.WATCH, 1)
        val meta = Cool.itemMeta
        meta.displayName = "Cooldown"
        val lore = ArrayList<String>()
        lore.add("Click me to set the cooldown for the command")
        meta.lore = lore
        Cool.itemMeta = meta
        return Cool
    }

    fun Cost(): ItemStack {
        val cost = ItemStack(Material.GOLD_BLOCK, 1)
        val meta = cost.itemMeta
        meta.displayName = "Cost"
        val lore = ArrayList<String>()
        lore.add("Click me to set the cost for the command")
        meta.lore = lore
        cost.itemMeta = meta
        return cost
    }

    fun delay(): ItemStack {
        val Wait = ItemStack(Material.WATCH, 1)
        val meta = Wait.itemMeta
        meta.displayName = "Wait"
        val lore = ArrayList<String>()
        lore.add("Click to set the wait before telepoting happens")
        meta.lore = lore
        Wait.itemMeta = meta
        return Wait

    }

    fun retry(): ItemStack {
        val retry = ItemStack(Material.WATCH, 1)
        val meta = retry.itemMeta
        meta.displayName = "Retries"
        val lore = ArrayList<String>()
        lore.add("Click to set the number of retries if a location is unsuitable")
        meta.lore = lore
        retry.itemMeta = meta
        return retry

    }
}


