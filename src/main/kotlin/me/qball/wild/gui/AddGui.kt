package me.qball.wild.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object AddGui {
    fun openMessGui(p: Player) {
        val Close = ItemStack(Material.REDSTONE_BLOCK, 1)
        val meta = Close.itemMeta
        meta.displayName = "Close"
        Close.itemMeta = meta
        val Add = Bukkit.createInventory(p, 18, "WildTp")
        p.openInventory(Add)
        Add.setItem(1, World())
        Add.setItem(6, Potion())
        Add.setItem(17, Close)
        Add.setItem(8, Biome())
    }

    fun World(): ItemStack {
        val World = ItemStack(Material.MAP, 1)
        val meta = World.itemMeta
        meta.displayName = "World"
        val lore = ArrayList<String>()
        lore.add("Click to add a world")
        meta.lore = lore
        World.itemMeta = meta
        return World
    }

    fun Potion(): ItemStack {
        val Potion = ItemStack(Material.POTION, 1)
        val meta = Potion.itemMeta
        meta.displayName = "Potion"
        val lore = ArrayList<String>()
        lore.add("Click me to add a potion effect")
        meta.lore = lore
        Potion.itemMeta = meta
        return Potion
    }

    fun Biome(): ItemStack {
        val biome = ItemStack(Material.MAP, 1)
        val meta = biome.itemMeta
        meta.displayName = "Biome Blacklist"
        val lore = ArrayList<String>()
        lore.add("Click me to add a biome to the blacklist")
        meta.lore = lore
        biome.itemMeta = meta
        return biome
    }
}


