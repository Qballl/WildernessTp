package io.wildernesstp.listener;

import io.wildernesstp.Main;
import io.wildernesstp.gui.SetupGUI;
import io.wildernesstp.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                    setup(clickedName,"minX",e.getWhoClicked().getUniqueId());
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"maxX");
                    break;
                case "maxX":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    setup(clickedName,"maxX",e.getWhoClicked().getUniqueId());
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"minZ");
                    break;
                case "minZ":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    setup(clickedName,"minZ",e.getWhoClicked().getUniqueId());
                    main.saveConfig();
                    SetupGUI.showMinMaxGUI((Player)e.getWhoClicked(),"maxZ");
                    break;
                case "maxZ":
                    clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    setup(clickedName,"maxZ",e.getWhoClicked().getUniqueId());
                    main.reloadConfig();
                    e.getWhoClicked().closeInventory();
                    main.getRegionManager().addRegion(regionMap.get(e.getWhoClicked().getUniqueId()));
                    regionMap.remove(e.getWhoClicked().getUniqueId());
                    break;

            }
        }
    }

    private void setup(String name, String type,UUID id){
        Region region = regionMap.get(id);
        region.set(type,Integer.parseInt(name));
        regionMap.replace(id,region);
        main.getConfig().set("regions."+region.getWorld().getName()+"."+type,Integer.parseInt(name));
        main.saveConfig();
    }

}
