package me.Qball.Wild.Commands;

import java.util.*;

import me.Qball.Wild.Utils.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


import me.Qball.Wild.*;
import me.Qball.Wild.GUI.*;
import me.Qball.Wild.Utils.WildTpBack;
import me.Qball.Wild.Utils.WorldInfo;


public class CmdWildTp implements CommandExecutor {

    public static Wild wild = Wild.getInstance();
    public static ArrayList<UUID> dev = new ArrayList<>();
    private final Wild plugin;

    public CmdWildTp(Wild plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Wildtp")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GOLD + "-------------------Help-------------------------");
                    player.sendMessage(ChatColor.GOLD + "* Command:       Description:                  *");
                    player.sendMessage(ChatColor.GOLD + "* /Wild Teleports player to random location    *");
                    player.sendMessage(ChatColor.GOLD + "* /Wild [player] Teleports the specfied player *");
                    player.sendMessage(ChatColor.GOLD + "* to a radom location                          *");
                    player.sendMessage(ChatColor.GOLD + "* /WildTp reload Reloads the plugin's config   *");
                    player.sendMessage(ChatColor.GOLD + "* /WildTp set <minx,maxX,minz,maxz,cool,cost,  *");
                    player.sendMessage(ChatColor.GOLD + "* sound> allow you to set the min and max x    *");
                    player.sendMessage(ChatColor.GOLD + "* and z and cooldown and cost and sound for    *");
                    player.sendMessage(ChatColor.GOLD + "* using the command                            *");
                    player.sendMessage(ChatColor.GOLD + "* /Wildtp gui to open the gui to set values    *");
                    player.sendMessage(ChatColor.GOLD + "* /Wildtp back Teleports you back to your      *");
                    player.sendMessage(ChatColor.GOLD + "* location when you did /wild                  *");
                    player.sendMessage(ChatColor.GOLD + "* /Wildtp {create} {name} creates a portal     *");
                    player.sendMessage(ChatColor.GOLD + "* with that name at the world edit region      *");
                    player.sendMessage(ChatColor.GOLD + "* /Wildtp {delete} {name} delete the portal    *");
                    player.sendMessage(ChatColor.GOLD + "* /Wildtp Shows wild help message              *");
                    player.sendMessage(ChatColor.GOLD + "------------------------------------------------");

                } else if (args.length >= 1) {

                    final String str = args[0];

                    if (str.equalsIgnoreCase("reload")) {
                        if (!player.hasPermission("wild.wildtp.reload")) {
                            player.sendMessage(ChatColor.RED + "Sorry you do not have permission to reload the plugin");
                        } else {
                            plugin.reload(player);
                        }
                    }
                    if (str.equalsIgnoreCase("wand")) {
                        if (!player.hasPermission("wild.wildtp.create.portal")) {
                            player.sendMessage(ChatColor.RED + "You do not have access to /wildtp wand");
                            return true;
                        }
                        ItemStack wand = new ItemStack(Material.WOOD_AXE);
                        ItemMeta meta = wand.getItemMeta();
                        meta.setDisplayName("Wildtp Wand");
                        meta.setLore(Collections.singletonList("Right/left click on blocks to make a region"));
                        wand.setItemMeta(meta);
                        player.getInventory().addItem(wand);
                    }
                    if (str.equalsIgnoreCase("create")) {
                        if (args.length >= 2) {
                            if (!player.hasPermission("wild.wildtp.create.portal")){
                                player.sendMessage(ChatColor.RED + "You do not have access to /wildtp create");
                                return true;
                            }
                            Vector first = plugin.firstCorner.get(player.getUniqueId());
                            Vector second = plugin.secondCorner.get(player.getUniqueId());
                            if(first == null && second == null){
                                player.sendMessage(ChatColor.RED+"Please use /wtp wand to make the corners");
                                return true;
                            }
                            Region rg = new Region(first, second);
                            Vector vecMax = rg.getMaximumPoint();
                            Vector vecMin = rg.getMinimumPoint();
                            String max = vecMax.getBlockX() + "," + vecMax.getBlockY() + "," + vecMax.getBlockZ();
                            String min = vecMin.getBlockX() + "," + vecMin.getBlockY() + "," + vecMin.getBlockZ();
                            String loc;
                            if(args.length>=3){
                                try{
                                    Biome biome = Biome.valueOf(args[2]);
                                    loc = player.getWorld().getName() + ":" + max + ":" + min + ":"+biome.name();
                                }catch(IllegalArgumentException e){
                                    player.sendMessage(ChatColor.RED+"The biome was unacceptable");
                                    loc = player.getWorld().getName() + ":" + max + ":" + min;
                                }
                            }else
                                loc = player.getWorld().getName() + ":" + max + ":" + min;
                            if (plugin.portals.containsKey(args[1])) {
                                player.sendMessage(ChatColor.RED + "A portal with that name already exists");
                                return true;
                            }
                            plugin.portals.put(args[1], loc);
                            player.sendMessage(ChatColor.GREEN + "Successfully created a portal");
                            plugin.firstCorner.remove(player.getUniqueId());
                            plugin.secondCorner.remove(player.getUniqueId());
                        }
                    } else if (str.equalsIgnoreCase("remove") || str.equalsIgnoreCase("delete")) {
                        if (args.length >= 2) {
                            if (!player.hasPermission("wild.wildtp.delete.portal")) {
                                player.sendMessage(ChatColor.RED + "You do not have permission to delete portals");
                                return true;
                            }if (!plugin.portals.containsKey(args[1])) {
                                player.sendMessage(ChatColor.RED + "No portal with that name exsits");
                                return true;
                            }
                            plugin.portals.remove(args[1]);
                            player.sendMessage(ChatColor.GREEN + "Successfully deleted the portal");
                        }
                    } else if (str.equalsIgnoreCase("list")) {
                        String names = "";
                        for (String name : plugin.portals.keySet()) {
                            names += name + ",  ";
                        }
                        player.sendMessage(ChatColor.GREEN + "The names of all portals are " + names);
                    } else if (str.equalsIgnoreCase("set")) {
                        if (player.hasPermission("wild.wildtp.set")) {
                            if (args.length >= 2) {
                                String Set = args[1];
                                String set = Set.toLowerCase();
                                switch (set) {
                                    case "minx":
                                        if (args.length >= 3) {
                                            int x = Integer.parseInt(args[2]);
                                            plugin.getConfig().set("MinX", x);
                                            player.sendMessage(ChatColor.GREEN + "You have set the MinX");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");
                                        }
                                        break;
                                    case "maxx":
                                        if (args.length >= 3) {
                                            int x = Integer.parseInt(args[2]);
                                            plugin.getConfig().set("MaxX", x);
                                            player.sendMessage(ChatColor.GREEN + "You have set the MaxX");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");
                                        }
                                        break;
                                    case "minz":
                                        if (args.length >= 3) {
                                            int z = Integer.parseInt(args[2]);
                                            plugin.getConfig().set("MinZ", z);
                                            player.sendMessage(ChatColor.GREEN + "You have set the MinZ");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");
                                        }
                                        break;
                                    case "maxz":
                                        if (args.length >= 3) {
                                            int z = Integer.parseInt(args[2]);
                                            plugin.getConfig().set("MaxZ", z);
                                            player.sendMessage(ChatColor.GREEN + "You have set the MaxZ");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");

                                        }
                                        break;
                                    case "cool":
                                        if (args.length >= 3) {
                                            String x = args[2];
                                            int cool = Integer.parseInt(x);
                                            plugin.getConfig().set("Cooldown",  cool);
                                            player.sendMessage(ChatColor.GREEN + "You have set the cooldown");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");
                                        }
                                        break;
                                    case "cost":
                                        if (args.length >= 3) {
                                            String x = args[2];
                                            int cost = Integer.parseInt(x);
                                            plugin.getConfig().set("Cost",  cost);
                                            player.sendMessage(ChatColor.GREEN + "You have set the cost for using the command");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");

                                        }
                                        break;
                                    case "sound":
                                        if (args.length >= 3) {
                                            StringBuilder sb = new StringBuilder();
                                            for (int i = 3; i < 4; i++) {
                                                sb.append(" ").append(args[i]);
                                            }
                                            plugin.getConfig().set("Sound", sb.toString());
                                            player.sendMessage(ChatColor.GREEN + "You have set the Sound");
                                            plugin.saveConfig();
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            player.sendMessage(ChatColor.DARK_RED + "You must specify a value");
                                        }
                                        break;

                                    default:
                                        player.sendMessage(ChatColor.RED + "Only enter minx,minz,maxx,maxz,cool,or costor sound");
                                        break;
                                }//end switch
                            }//args length 2

                            else {
                                player.sendMessage(ChatColor.RED + " Please enter minx,minz,maxx,maxz,cool,or cost");
                            }
                        }//perm set
                        else {
                            player.sendMessage("You dont have permssion to set the x or z values");
                        }
                    }// str == set
                    else if (str.equalsIgnoreCase("add")) {
                        if (player.hasPermission("wild.wildtp.set")) {
                            if (args.length >= 2) {
                                String add = args[1].toLowerCase();
                                switch (add) {
                                    case "world":
                                        if (args.length >= 7) {
                                            WorldInfo worldInfo = new WorldInfo(plugin);
                                            worldInfo.setWorldName(args[2]);
                                            worldInfo.setMinX(args[2], Integer.parseInt(args[3]));
                                            worldInfo.setMaxX(args[2], Integer.parseInt(args[4]));
                                            worldInfo.setMinZ(args[2], Integer.parseInt(args[5]));
                                            worldInfo.setMaxZ(args[2], Integer.parseInt(args[6]));
                                            player.sendMessage(ChatColor.GREEN + "You have added " + args[2] + " to the allowed worlds");
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Please enter a world");
                                        }
                                        break;
                                    case "potion":
                                        if (args.length >= 3) {
                                            String potion = args[2];

                                            List<String> Potions = plugin.getListPots();
                                            Potions.add(potion);
                                            plugin.getConfig().set("Potions", Potions);
                                            plugin.saveConfig();
                                            sender.sendMessage("You have added " + potion + " to the list of potions");
                                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                        } else {
                                            sender.sendMessage("Please enter a potion");
                                        }
                                        break;
                                }
                            }
                        }
                    } else if (str.equalsIgnoreCase("gui")) {
                        if (player.hasPermission("wild.wildtp.set")) {
                            MainGui.OpenGUI(player);
                            MainGui.putEdit(player);
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission for that sorry :(");
                        }
                    } else if (str.equalsIgnoreCase("back")) {
                        if (player.hasPermission("wild.wildtp.back")) {
                            int confWait = plugin.getConfig().getInt("Wait");
                            String strWait = String.valueOf(confWait);
                            String delayMsg = plugin.getConfig().getString("WaitMsg");
                            String DelayMsg = delayMsg.replaceAll("\\{wait\\}", strWait);
                            WildTpBack wildtp = new WildTpBack();
                            int wait = confWait * 20;
                            if (wait > 0) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DelayMsg));
                                new BukkitRunnable() {
                                    public void run() {
                                        wildtp.back(player);
                                    }
                                }.runTaskLater(plugin, wait);
                            } else {
                                wildtp.back(player);
                            }
                        }else{
                            player.sendMessage(ChatColor.RED+"You do not have permission to use /wildtp back");
                        }
                    } else if (str.equalsIgnoreCase("dev")) {
                        dev.add(player.getUniqueId());
                    }else{
                        player.sendMessage(ChatColor.RED+"Invalid subcommand");
                    }
                }// args length 1
            }// end if playevr
            else {
                if (args.length == 0) {
                    sender.sendMessage("-------------------Help-------------------------");
                    sender.sendMessage("* Command:       Description:                  *");
                    sender.sendMessage("* /Wild Teleports player to random location    *");
                    sender.sendMessage("* /Wild [player] Teleports the specfied player *");
                    sender.sendMessage("* to a radom location                          *");
                    sender.sendMessage("* /WildTp reload Reloads the plugin's config   *");
                    sender.sendMessage("* /Wildtp set <minx,maxX,minz,maxz,cool,cost>  *");
                    sender.sendMessage("* allow you to set the min and max x and z and *");
                    sender.sendMessage("* the cooldown and cost for using the command  *");
                    sender.sendMessage("* /Wildtp Shows plugin help message              *");
                    sender.sendMessage("------------------------------------------------");
                } else if (args.length == 1) {

                    String Str = args[0];

                    if (Str.equalsIgnoreCase("reload")) {

                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                        sender.sendMessage("[WildernessTP] Plugin config has successfuly been reload");

                    }
                    if (Str.equalsIgnoreCase("set")) {

                        if (args.length >= 2) {
                            final String set = args[1];
                            switch (set) {

                                case "minx":
                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);

                                        plugin.getConfig().set("MinX", (Object) X);

                                        sender.sendMessage("You have set the MinX");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");
                                    }
                                    break;

                                case "maxx":

                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);
                                        plugin.getConfig().set("MaxX", (Object) X);
                                        sender.sendMessage("You have set the MaxX");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");
                                    }
                                    break;

                                case "minz":

                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);
                                        plugin.getConfig().set("MinZ", (Object) X);
                                        sender.sendMessage("You have set the MinZ");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");
                                    }
                                    break;


                                case "maxz":

                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);
                                        plugin.getConfig().set("MaxZ", (Object) X);
                                        sender.sendMessage("You have set the MaxZ");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");

                                    }
                                    break;
                                case "cool":

                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);
                                        plugin.getConfig().set("Cooldown", (Object) X);
                                        sender.sendMessage("You have set the cooldown");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");

                                    }
                                    break;
                                case "cost":

                                    if (args.length >= 3) {
                                        String x = args[2];
                                        int X = Integer.parseInt(x);
                                        plugin.getConfig().set("Cost", (Object) X);
                                        sender.sendMessage("You have set the cost for using the command");
                                        plugin.saveConfig();
                                        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                    } else {
                                        sender.sendMessage("You must specify a value");

                                    }
                                    break;
                                default:
                                    sender.sendMessage("Only enter minx,minz,maxx,maxz,cool,or cost");
                                    break;


                            }//end switch
                            if (Str.equalsIgnoreCase("add")) {

                                if (args.length >= 2) {
                                    String add = args[1].toLowerCase();
                                    switch (add) {
                                        case "world":
                                            if (args.length >= 3) {
                                                String world = args[2];
                                                @SuppressWarnings("unchecked")
                                                List<String> Worlds = (List<String>) plugin.getConfig().getList("Worlds");
                                                Worlds.add(world);
                                                plugin.getConfig().set("Worlds", Worlds);
                                                plugin.saveConfig();
                                                sender.sendMessage("You have added " + world + " to the allowed worlds");
                                                Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                            } else {
                                                sender.sendMessage(" Please enter a world");
                                            }
                                            break;
                                        case "potion":
                                            if (args.length >= 3) {
                                                String potion = args[2];
                                                List<String> Potions = wild.getListPots();
                                                Potions.add(potion);
                                                plugin.getConfig().set("Potions", Potions);
                                                plugin.saveConfig();
                                                sender.sendMessage("You have added " + potion + " to the list of potions");
                                                Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                                            } else {
                                                sender.sendMessage("Please enter a potion");
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}