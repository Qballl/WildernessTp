package me.Qball.Wild.Commands;

import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pi on 4/9/17.
 */
public class WildTpTab implements TabCompleter {
    private ArrayList<String> biomes = new ArrayList<>();
    public WildTpTab(){
        for(Biome biome : Biome.values())
            biomes.add(biome.name());
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("wildtp")) {
            if (args.length <=2) {
                if (args[0].equalsIgnoreCase("create"))
                    return biomes;
            }else if (args.length >=3){
                if(args[0].equalsIgnoreCase("create")) {
                    for (String biome : biomes) {
                        if (biome.toLowerCase().startsWith(args[2].toLowerCase()))
                            completions.add(biome);
                    }
                    return completions;
                }
            }
        }
        return null;
    }
}
