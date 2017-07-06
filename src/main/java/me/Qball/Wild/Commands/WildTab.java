package me.Qball.Wild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class WildTab implements TabCompleter {
    private ArrayList<String> biomes = new ArrayList<>();
    public WildTab(){
        for(Biome biome : Biome.values())
            biomes.add(biome.name());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("wild")){
            if(!sender.hasPermission("wild.wildtp.others")) {
                if (args.length == 0)
                    return biomes;
                else {
                    for (String biome : biomes) {
                        if (biome.toLowerCase().startsWith(args[0].toLowerCase()))
                            completions.add(biome);
                    }
                    return completions;
                }
            }else{
                if (args.length == 0){
                    for(Player p : Bukkit.getServer().getOnlinePlayers())
                        biomes.add(p.getDisplayName());
                    return biomes;
                }
                else {
                    for (String biome : biomes) {
                        if (biome.toLowerCase().startsWith(args[0].toLowerCase()))
                            completions.add(biome);
                    }
                    for(Player p : Bukkit.getServer().getOnlinePlayers()){
                        if(p.getDisplayName().toLowerCase().startsWith(args[0].toLowerCase()))
                            completions.add(p.getDisplayName());
                    }
                    return completions;
                }
            }
        }
        return null;
    }
}
