package me.Qball.Wild.Commands;

import me.Qball.Wild.Utils.*;
import me.Qball.Wild.Wild;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWild implements CommandExecutor {
    private final Wild wild;

    public CmdWild(Wild wild) {
        this.wild = wild;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        if ((wild.usageMode != UsageMode.COMMAND_ONLY || wild.usageMode != UsageMode.BOTH)) {
            sender.sendMessage(wild.getConfig().getString("UsageDisabled"));
            return true;
        }

        CheckPerms check = new CheckPerms(wild);
        Checks checks = new Checks(wild);
        GetRandomLocation random = new GetRandomLocation(wild);
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UsersFile users = new UsersFile(wild);
            if(users.getUses(p.getUniqueId())>= wild.getConfig().getInt("Limit")&&
                    wild.getConfig().getInt("Limit")!=0 && !p.hasPermission("wild.wildtp.limit.bypass")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',wild.getConfig().getString("LimitMsg")));
                return true;
            }
            users.addUse(p.getUniqueId());
            if (args.length == 0) {
                if (!checks.world(p))
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("WorldMsg")));
                else {
                    if(!p.hasPermission("wild.wildtp")){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("NoPerm")));
                        return true;
                    }
                    check.check(p,p.getWorld().getName());
                }
            } else if (args.length == 1) {
                if (Bukkit.getServer().getPlayer(args[0]) != null) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (p.hasPermission("wild.wildtp.others")) {
                        if (checks.world(target)) {
                            if(!p.hasPermission("wild.wildtp")){
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("NoPerm")));
                                return true;
                            }
                            check.check(p, target,p.getWorld().getName());
                        }else
                            p.sendMessage(ChatColor.RED + "The specified player is in a world where the command cannot be used");
                    }else{
                        for(Biome biome : Biome.values()){
                            if(biome.name().equalsIgnoreCase(args[0])){
                                wild.biome.put(p.getUniqueId(), biome);
                                if(!p.hasPermission("wild.wildtp")){
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("NoPerm")));
                                    return true;
                                }
                                check.check(p,p.getWorld().getName());
                                return true;
                            }
                        }
                       p.sendMessage(ChatColor.RED+"The biome entered was not acceptable");
                    }
                } else{
                        for(Biome biome : Biome.values()){
                            if(biome.name().equalsIgnoreCase(args[0])){
                                if(p.hasPermission("wild.wildtp.biome."+biome.name().toLowerCase()))
                                wild.biome.put(p.getUniqueId(), biome);
                                if(!p.hasPermission("wild.wildtp")){
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', wild.getConfig().getString("NoPerm")));
                                    return true;
                                }
                                check.check(p,p.getWorld().getName());
                                return true;
                            }
                        }
                        for(World world : Bukkit.getWorlds()){
                            if(world.getName().toLowerCase().equals(args[0].toLowerCase())){
                                check.check(p,world.getName());
                                return true;
                            }
                        }
                }
                p.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online or biome was incorrect or the world was incorrect");
            }else if(args.length==2){
                if(p.hasPermission("wild.wildtp.others")) {
                    if (Bukkit.getServer().getPlayer(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        for (World world : Bukkit.getWorlds()) {
                            if (world.getName().toLowerCase().equals(args[1].toLowerCase())) {
                                check.check(p, target, world.getName());
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "The world was incorrect");
                    } else {
                        p.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online");
                    }
                }else{
                    p.sendMessage(ChatColor.RED+"You do not have permission to teleport other players");
                    return true;
                }
            }
            return true;

        } else {
            if (args.length == 0) {
                sender.sendMessage("Console cannot use this command except on other players");
            } else if (args.length == 1) {
                if (Bukkit.getServer().getPlayer(args[0]) != null) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (checks.world(target)) {
                        WildTpBack back = new WildTpBack();
                        back.saveLoc(target, target.getLocation());
                        random.getWorldInfo(target);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&',wild.getConfig().getString("TeleportConsole")));
                        return true;
                    } else {
                        sender.sendMessage("The specified player is in a world where the command cannot be used");
                    }

                } else {
                    sender.sendMessage("Player " + args[0] + " is not online");
                }
            }else if(args.length==2) {
                if (Bukkit.getServer().getPlayer(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    for (World world : Bukkit.getWorlds()) {
                        if (world.getName().toLowerCase().equals(args[0].toLowerCase())) {
                            random.getWorldInfo(target, world.getName());
                            return true;
                        }
                    }
                    sender.sendMessage("The world was incorrect");
                } else {
                    sender.sendMessage("Player " + args[0] + " is not online");
                }
            }
        }
        return false;
    }
}