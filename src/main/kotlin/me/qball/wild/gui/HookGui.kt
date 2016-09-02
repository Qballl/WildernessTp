package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object HookGui {
    fun openHook(p: Player) {

        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        val lore = ArrayList<String>()
        lore.add("Click to close the inventory and return to normal gameplay")
        meta.lore = lore
        Close.itemMeta = meta
        val Wildtp = Bukkit.createInventory(p, 18, "Hooks")
        p.openInventory(Wildtp)
        Wildtp.setItem(0, towny())
        Wildtp.setItem(2, factions())
        Wildtp.setItem(4, griefPreven())
        Wildtp.setItem(6, worldGuard())
        Wildtp.setItem(8, kingdoms())
        Wildtp.setItem(17, Close)
    }

    fun factions(): ItemStack {
        val factions = ItemStack(Material.TNT, 1)
        val meta = factions.itemMeta
        meta.displayName = "Factions Hook"
        val lore = ArrayList<String>()
        lore.add("Click to enable or disable factions hook")
        meta.lore = lore
        factions.itemMeta = meta
        return factions
    }

    fun griefPreven(): ItemStack {
        val griefPreven = ItemStack(Material.WOOD_SPADE, 1)
        val meta = griefPreven.itemMeta
        meta.displayName = "GriefPrevention Hook"
        val lore = ArrayList<String>()
        lore.add("Click to enable or disable Grief Prevention Hook")
        meta.lore = lore
        griefPreven.itemMeta = meta
        return griefPreven
    }

    fun towny(): ItemStack {

        val towny = ItemStack(Material.DIAMOND_PICKAXE, 1)
        val meta = towny.itemMeta
        meta.displayName = "Towny Hook"
        val lore = ArrayList<String>()
        lore.add("Click to enable or disable Towny Hook")
        meta.lore = lore
        towny.itemMeta = meta
        return towny
    }

    fun worldGuard(): ItemStack {

        val worldGuard = ItemStack(Material.WOOD_AXE, 1)
        val meta = worldGuard.itemMeta
        meta.displayName = "WorldGuard Hook"
        val lore = ArrayList<String>()
        lore.add("Click to enable or disable WorldGuard Hook")
        meta.lore = lore
        worldGuard.itemMeta = meta
        return worldGuard
    }

    fun kingdoms(): ItemStack {
        val kingdom = ItemStack(Material.WOOD_AXE, 1)
        val meta = kingdom.itemMeta
        meta.displayName = "Kingdom Hook"
        val lore = ArrayList<String>()
        lore.add("Click to enable or disable Kingdom Hook")
        meta.lore = lore
        kingdom.itemMeta = meta
        return kingdom
    }
}

