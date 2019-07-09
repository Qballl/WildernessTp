package me.Qball.Wild.Listeners;

import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Wild;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignClick implements Listener {
    private final Wild wild;

    public SignClick(Wild plugin) {
        wild = plugin;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getClickedBlock().getState() instanceof Sign) {
            CheckPerms perms = new CheckPerms(wild);
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase("[§1Wild§0]") && sign.getLine(0).equalsIgnoreCase("§4====================")) {
                if (!Wild.cancel.contains(e.getPlayer().getUniqueId())) {
                    if(!sign.getLine(3).equals("")){
                        try{
                            for(World world : Bukkit.getWorlds()){
                                if(world.getName().toLowerCase().equals(sign.getLine(3).toLowerCase())){
                                    perms.check(e.getPlayer(),world.getName());
                                    return;
                                }
                            }
                            Biome biome = Biome.valueOf(sign.getLine(3).toUpperCase());
                            wild.biome.put(e.getPlayer().getUniqueId(),biome);
                        }catch(IllegalArgumentException ex){
                            Location loc = e.getClickedBlock().getLocation();
                            Bukkit.getLogger().severe("Biome wild sign at " +loc.getWorld().getName()+","+loc.getBlockX()
                                    +","+loc.getBlockY()+ "," + loc.getBlockZ() +" has a biome or a world that is incorrect please fix");
                        }
                    }else
                        perms.check(e.getPlayer(),e.getPlayer().getWorld().getName());
                }
            }
        }
    }


}
