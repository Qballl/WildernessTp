package io.wildernesstp.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * MIT License
 * <p>
 * Copyright (c) 2019 Quintin VanBooven
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public final class WTPConstants {

    // Items.
    public static ItemStack WAND;

    // Inventories.
    public static Inventory BIOME_SELECTOR;

    static {
        wand:
        {
            WTPConstants.WAND = new ItemStack(Material.GOLDEN_AXE);
            ItemMeta WAND_META = Objects.requireNonNull(WAND.getItemMeta());
            WAND_META.setDisplayName("WildernessTP Portal Wand");
            WAND_META.setLore(Arrays.asList("§9Left-click to select point one.", "§9Right-click to select point two."));
            WAND_META.addItemFlags(ItemFlag.values());
            WAND.setItemMeta(WAND_META);
        }

        biome_selector:
        {
            final Biome[] biomes = Biome.values();
            WTPConstants.BIOME_SELECTOR = Bukkit.createInventory(null, InventoryUtils.calculateRows(biomes.length));

            for (Biome biome : biomes) {
                ItemStack i = new ItemStack(Material.GRASS_BLOCK);
                ItemMeta m = Objects.requireNonNull(i.getItemMeta());
                m.setDisplayName(biome.name().toLowerCase().replaceFirst(String.valueOf(biome.name().charAt(0)), String.valueOf(Character.toUpperCase(biome.name().charAt(0)))));
                m.setLore(Collections.singletonList("§9Click to teleport to this biome."));
                m.addItemFlags(ItemFlag.values());
                i.setItemMeta(m);

                BIOME_SELECTOR.addItem();
            }
        }
    }

    private WTPConstants() {
    }
}
