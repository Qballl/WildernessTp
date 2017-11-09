package me.Qball.Wild.GUI;

import java.util.List;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Commands.CmdWildTp;
import me.Qball.Wild.Utils.WorldInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SetVal implements Listener {

    private Wild wild;

    public SetVal(Wild wild) {
        this.wild = wild;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();

        if (MainGui.editMode(p)) {
            final String value = e.getMessage();
            e.setCancelled(true);
            new BukkitRunnable() {

                @Override
                public void run() {

                    if (value.equalsIgnoreCase("exit") || value.equalsIgnoreCase("cancel")) {
                        MainGui.removeEdit(p);
                        if (InvClick.add.contains(p.getUniqueId())) {
                            InvClick.add.remove(p.getUniqueId());
                        } else if (InvClick.set.contains(p.getUniqueId())) {
                            InvClick.set.remove(p.getUniqueId());
                        } else if (InvClick.messages.contains(p.getUniqueId())) {
                            InvClick.messages.remove(p.getUniqueId());
                        } else if (InvClick.sounds.contains(p.getUniqueId())) {
                            InvClick.sounds.remove(p.getUniqueId());
                        }
                        InvClick.toSet.remove(p.getUniqueId());

                        p.sendMessage(ChatColor.GREEN + " You have exited edit mode. Game play will return to normal");
                    } else {
                        if (InvClick.set.contains(p.getUniqueId())) {
                            if (InvClick.worlds.contains(p.getUniqueId())) {
                                String[] info = value.split(" ");
                                String world = info[0];
                                int val = Integer.parseInt(info[1]);
                                InvClick.set.remove(p.getUniqueId());
                                InvClick.worlds.remove(p.getUniqueId());
                                WorldInfo wInfo = new WorldInfo(wild);
                                wInfo.setWorldInfo(InvClick.toSet.get(p.getUniqueId()), world, val);
                                p.sendMessage(ChatColor.GREEN + "You have set the " + InvClick.toSet.get(p.getUniqueId()) + " of world " + world + " to " + val);
                                InvClick.toSet.remove(p.getUniqueId());
                                Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                            }
                            InvClick.set.remove(p.getUniqueId());
                            String val = InvClick.toSet.get(p.getUniqueId());
                            InvClick.toSet.remove(p.getUniqueId());
                            String x = value;
                            x = x.replaceAll("[^\\d-]", "");
                            int X = Integer.parseInt(x);
                            wild.getConfig().set(val, X);
                            p.sendMessage(ChatColor.GREEN + "You have set the " + val);
                            wild.saveConfig();
                            MainGui.removeEdit(p);
                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                        } else if (InvClick.messages.contains(p.getUniqueId())) {
                            InvClick.messages.remove(p.getUniqueId());
                            String message = value;
                            String val = InvClick.toSet.get(p.getUniqueId());
                            InvClick.toSet.remove(p.getUniqueId());
                            wild.getConfig().set(val, message);
                            p.sendMessage(ChatColor.GREEN + "You have set the " + val + " message");
                            wild.saveConfig();
                            MainGui.removeEdit(p);
                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                        } else if (InvClick.add.contains(p.getUniqueId())) {

                            InvClick.add.remove(p.getUniqueId());
                            String message = value;
                            String val = InvClick.toSet.get(p.getUniqueId());
                            InvClick.toSet.remove(p.getUniqueId());
                            if(val==null)
                                return;
                            if (val.equalsIgnoreCase("potions")) {
                                List<String> Potions = wild.getListPots();
                                Potions.add(message);
                                wild.getConfig().set("Potions", Potions);
                                wild.saveConfig();
                                p.sendMessage("You have added " + message + " to the list of potions");

                            } else if (val.equalsIgnoreCase("worlds")) {
                                String[] info = message.split(" ");
                                WorldInfo world = new WorldInfo(wild);
                                world.setWorldName(info[0]);
                                world.setMinX(info[0], Integer.parseInt(info[1]));
                                world.setMaxX(info[0], Integer.parseInt(info[2]));
                                world.setMinZ(info[0], Integer.parseInt(info[3]));
                                world.setMaxZ(info[0], Integer.parseInt(info[4]));
                                p.sendMessage(ChatColor.GREEN + "You have added " + message + " to the allowed worlds");


                            }
                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                            MainGui.removeEdit(p);
                        } else if (InvClick.sounds.contains(p.getUniqueId())) {
                            InvClick.sounds.remove(p.getUniqueId());
                            String message = value;
                            String val = InvClick.toSet.get(p.getUniqueId());
                            InvClick.toSet.remove(p.getUniqueId());
                            wild.getConfig().set(val, message);
                            p.sendMessage(ChatColor.GREEN + "You have set the " + val + " as the sound that will be heard");
                            wild.saveConfig();
                            MainGui.removeEdit(p);
                            Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                        }
                    }


                }

            }.runTaskLater(wild, 1);


        }
    }
}
 