package io.wildernesstp.listener;

import io.wildernesstp.Main;
import io.wildernesstp.gui.SetupGUI;
import io.wildernesstp.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.UUID;

public class InventoryListener implements Listener {

    private final Main main;

    private static final HashMap<UUID, Region> regionMap = new HashMap<>();

    public InventoryListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().contains("WildernessTp setup")){
            if(e.getCurrentItem() == null)
                return;
            e.setCancelled(true);
            String[] parts = e.getView().getTitle().split(" ");
            switch (parts[2]){
                case "World":
                    Region region = new Region(Bukkit.getWorld(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())),
                        0,0,0,0,"");
                    main.getConfig().set("regions."+ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()),"");
                    main.saveConfig();
                    SetupGUI.showWorldToGui((Player)e.getWhoClicked());
                    regionMap.put(e.getWhoClicked().getUniqueId(),region);
                    break;
                case "WorldTo":
                    region = regionMap.get(e.getWhoClicked().getUniqueId());
                    String clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    if(clickedName.equalsIgnoreCase("none")||
                        clickedName.equalsIgnoreCase(region.getWorld().getName())) {
                        SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"minX");
                    }else{
                        region.setWorldTo(clickedName);
                        regionMap.replace(e.getWhoClicked().getUniqueId(),region);
                        main.getConfig().set("regions."+region.getWorld().getName()+".worldTo",clickedName);
                        main.saveConfig();
                        main.reloadConfig();
                        e.getWhoClicked().closeInventory();
                        main.getRegionManager().addRegion(region);
                    }
                    break;
                case "minX":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    region = regionMap.get(e.getWhoClicked().getUniqueId());
                    region.setMinX(Integer.parseInt(clickedName));
                    e.getWhoClicked().sendMessage(ChatColor.GRAY+region.toString());
                    e.getWhoClicked().sendMessage(" ");
                    regionMap.replace(e.getWhoClicked().getUniqueId(),region);
                    main.getConfig().set("regions."+region.getWorld().getName()+".minX",Integer.parseInt(clickedName));
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"maxX");
                    break;
                case "maxX":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    region = regionMap.get(e.getWhoClicked().getUniqueId());
                    region.setMinX(Integer.parseInt(clickedName));
                    e.getWhoClicked().sendMessage(ChatColor.GRAY+region.toString());
                    e.getWhoClicked().sendMessage(" ");
                    regionMap.replace(e.getWhoClicked().getUniqueId(),region);
                    main.getConfig().set("regions."+region.getWorld().getName()+".maxX",Integer.parseInt(clickedName));
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"minZ");
                    break;
                case "minZ":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    region = regionMap.get(e.getWhoClicked().getUniqueId());
                    e.getWhoClicked().sendMessage(ChatColor.GRAY+region.toString());
                    e.getWhoClicked().sendMessage(" ");
                    region.setMinX(Integer.parseInt(clickedName));
                    regionMap.replace(e.getWhoClicked().getUniqueId(),region);
                    main.getConfig().set("regions."+region.getWorld().getName()+".minZ",Integer.parseInt(clickedName));
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"maxZ");
                    break;
                case "maxZ":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    region = regionMap.get(e.getWhoClicked().getUniqueId());
                    region.setMinX(Integer.parseInt(clickedName));
                    e.getWhoClicked().sendMessage(ChatColor.GRAY+region.toString());
                    e.getWhoClicked().sendMessage(" ");
                    main.getConfig().set("regions."+region.getWorld().getName()+".maxZ",Integer.parseInt(clickedName));
                    main.saveConfig();
                    main.reloadConfig();
                    e.getWhoClicked().closeInventory();
                    main.getRegionManager().addRegion(region);
                    regionMap.remove(e.getWhoClicked().getUniqueId());
                    break;

            }
        }
    }

}
