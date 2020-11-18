package io.wildernesstp.gui;

import io.wildernesstp.Main;
import io.wildernesstp.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public final class WorldGUI extends GUI {

    private final Main main;

    public WorldGUI(Main main) {
        super("Choose a world.", Size.ONE_ROW);

        this.main = main;
        super.setClickHandler(this::handleClick);
    }

    @Override
    public void show(Player p) {
        final Inventory inv = super.createInventory(); // Only creates the instance once.

        for (Region region : main.getRegionManager().getRegions()) {
            inv.addItem(this.createItem(region));
        }
    }

    private void handleClick(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getCurrentItem()  == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        // May need some changing though.
        main.getRegionManager().getRegion(Bukkit.getWorld(ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName()))).ifPresent(region -> {
            main.teleport((Player) e.getWhoClicked(), Collections.singleton((l) -> Objects.requireNonNull(l.getWorld()).getName().equalsIgnoreCase(region.getWorld().getName())));
        });
    }

    private ItemStack createItem(Region region) {
        final ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        final ItemMeta meta = Objects.requireNonNull( item.getItemMeta());
        meta.setDisplayName(ChatColor.DARK_RED  + region.getWorld().getName());
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);

        return item;
    }
}
