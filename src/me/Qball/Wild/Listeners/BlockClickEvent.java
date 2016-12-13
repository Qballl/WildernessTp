package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.Region;
import me.Qball.Wild.Wild;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class BlockClickEvent implements Listener {
    private Wild wild;

    public BlockClickEvent(Wild wild) {
        this.wild = wild;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getItem().getItemMeta().hasLore()) {
            if (e.getItem().getItemMeta().getLore().equals(Collections.singletonList("Right/left click on blocks to make a region"))) {
                e.setCancelled(true);
                wild.firstCorner.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation().toVector());
                e.getPlayer().sendMessage(ChatColor.GREEN + "First corner set");
            }
        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack stack = e.getPlayer().getItemInHand();
            if (stack.getItemMeta().hasLore()) {
                if (stack.getItemMeta().getLore().equals(Collections.singletonList("Right/left click on blocks to make a region"))) {
                    e.setCancelled(true);
                    wild.secondCorner.put(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation().toVector());
                    e.getPlayer().sendMessage(ChatColor.GREEN + "Second corner set");
                }
            }
        }
    }

}
