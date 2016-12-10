package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class BlockClickEvent implements Listener {
    private Wild wild;
    public BlockClickEvent(Wild wild){
        this.wild = wild;
    }
    @EventHandler
    public void onBlockClick(PlayerInteractEvent e){
        Vector first = null;
        Vector second = null;
        if(e.getItem().getItemMeta().hasLore()&&e.getItem().getItemMeta().getLore().equals(Arrays.asList("Right/left click on blocks to make a region"))&&
                e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            e.setCancelled(true);
            first = e.getClickedBlock().getLocation().toVector();
        }
        if(e.getItem().getItemMeta().hasLore()&&e.getItem().getItemMeta().getLore().equals(Arrays.asList("Right/left click on blocks to make a region"))&&
                e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            e.setCancelled(true);
            second = e.getClickedBlock().getLocation().toVector();
        }
        if(first != null && second !=null){
            Region region = new Region(first,second);
            wild.regions.put(e.getPlayer().getUniqueId(),region);
        }
    }


}
