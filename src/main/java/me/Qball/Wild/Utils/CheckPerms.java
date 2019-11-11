package me.Qball.Wild.Utils;


import me.Qball.Wild.Wild;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckPerms {
    private final Wild wild;

    public CheckPerms(Wild wild) {
        this.wild = wild;
    }

    public void check(Player p, String world) {
        WildTpBack back = new WildTpBack();
        back.saveLoc(p, p.getLocation());
        int cost = wild.getConfig().getInt("Cost");
        String strCost = String.valueOf(cost);
        String costMsg = wild.getConfig().getString("Costmsg").replaceAll("\\{cost}", strCost);
        int cool = wild.getConfig().getInt("Cooldown");
        String coolMsg = wild.getConfig().getString("Cooldownmsg");
        GetRandomLocation random = new GetRandomLocation(wild);
        Economy econ = null;
        /*if(!p.hasPermission("wild.wildtp.world")||!p.hasPermission("wild.wildtp.world."+world)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',wild.getConfig().getString("NoWorldPerm")));
            return;
        }*/
        if (cost > 0)
            econ = wild.getEcon();
        if (p.hasPermission("wild.wildtp.cost.bypass") && p.hasPermission("wild.wildtp.cooldown.bypass")) {
            random.getWorldInfo(p,world);
        }else if (p.hasPermission("wild.wildtp.cost.bypass") && !p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (Wild.check(p)) {
                random.getWorldInfo(p,world);
            }else {
                coolMsg = coolMsg.replace("{cool}", String.valueOf(cool)).replace("{rem}",Wild.getRem(p));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolMsg));
            }
        } else if (!p.hasPermission("wild.wildtp.cost.bypass") && p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (cost > 0) {
                if (econ.getBalance(p) >= cost) {
                    EconomyResponse r = econ.withdrawPlayer(p, cost);
                    if (r.transactionSuccess()) {
                        random.getWorldInfo(p,world);
                        costMsg = costMsg.replace("{bal}",String.valueOf(econ.getBalance(p)));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
                    } else {
                        p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
                    }
                } else
                    p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
            } else {
                random.getWorldInfo(p,world);
            }
        }
        if (!p.hasPermission("wild.wildtp.cost.bypass") && !p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (!Wild.check(p)) {
                coolMsg = coolMsg.replace("{cool}", String.valueOf(cool)).replace("{rem}",Wild.getRem(p));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolMsg));
            } else {
                if (cost > 0) {
                    if (econ.getBalance(p) >= cost) {
                        EconomyResponse r = econ.withdrawPlayer(p, cost);
                        if (r.transactionSuccess()) {
                            random.getWorldInfo(p,world);
                            costMsg = costMsg.replace("{bal}",String.valueOf(econ.getBalance(p)));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));


                        } else {
                            p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
                        }
                    } else
                        p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
                } else {
                    random.getWorldInfo(p,world);
                }
            }
        }
    }

    public void check(Player p, Player target, String world) {
        WildTpBack back = new WildTpBack();
        back.saveLoc(target, target.getLocation());
        int cost = wild.getConfig().getInt("Cost");
        String strCost = String.valueOf(cost);
        String costMsg = wild.getConfig().getString("Costmsg").replaceAll("\\{cost}", strCost);
        int cool = wild.getConfig().getInt("Cooldown");
        String coolMsg = wild.getConfig().getString("Cooldownmsg");
        GetRandomLocation random = new GetRandomLocation(wild);
        Economy econ = null;
        if (cost > 0)
            econ = wild.getEcon();
        if (p.hasPermission("wild.wildtp.cost.bypass") && p.hasPermission("wild.wildtp.cooldown.bypass"))
            random.getWorldInfo(target,world);
        if (p.hasPermission("wild.wildtp.cost.bypass") && !p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (Wild.check(p)) {
                random.getWorldInfo(target,world);
                target.sendMessage(ChatColor.GREEN + p.getDisplayName() + " threw you to random location");
            } else {
                coolMsg = coolMsg.replace("{cool}", String.valueOf(cool)).replace("{rem}",Wild.getRem(p));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolMsg));
            }
        }
        if (!p.hasPermission("wild.wildtp.cost.bypass") && p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (cost > 0) {
                if (econ.getBalance(p) >= cost) {
                    EconomyResponse r = econ.withdrawPlayer(p, cost);
                    if (r.transactionSuccess()) {
                        random.getWorldInfo(target,world);
                        costMsg = costMsg.replace("{bal}",String.valueOf(econ.getBalance(p)));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
                        target.sendMessage(ChatColor.GREEN + p.getDisplayName() + " threw you to random location");

                    } else {
                        p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
                    }
                } else
                    p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
            } else {
                random.getWorldInfo(target,world);
                target.sendMessage(ChatColor.GREEN + p.getDisplayName() + " threw you to random location");
            }
        }
        if (!p.hasPermission("wild.wildtp.cost.bypass") && !p.hasPermission("wild.wildtp.cooldown.bypass")) {
            if (!Wild.check(p)) {
                if (coolMsg.contains("{cool}"))
                    coolMsg = coolMsg.replace("{cool}", String.valueOf(cool));
                else if (coolMsg.contains("{rem}"))
                    coolMsg = coolMsg.replace("{rem}", Wild.getRem(p));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolMsg));
            } else {
                if (cost > 0) {
                    if (econ.getBalance(p) >= cost) {
                        EconomyResponse r = econ.withdrawPlayer(p, cost);
                        if (r.transactionSuccess()) {
                            random.getWorldInfo(target,world);
                            costMsg = costMsg.replace("{bal}",String.valueOf(econ.getBalance(p)));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
                            target.sendMessage(ChatColor.GREEN + p.getDisplayName() + " threw you to random location");

                        } else {
                            p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
                        }
                    } else
                        p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
                } else {
                    random.getWorldInfo(target,world);
                    target.sendMessage(ChatColor.GREEN + p.getDisplayName() + " threw you to random location");
                }
            }
        }

    }
}
