package me.Qball.Wild.Utils;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.Qball.Wild.Wild;

public class CheckPerms {
	public Wild wild = Wild.getInstance();
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	public Economy econ = rsp.getProvider();
	public void check(Player p)
	{
		
		int cost = wild.getConfig().getInt("Cost");
		String costMSG = wild.getConfig().getString("Costmsg");
		String strCost = String.valueOf(cost);
		String costMsg = costMSG.replaceAll("\\{cost\\}", strCost);
		int cool = wild.getConfig().getInt("Cooldown");
		String Cool = String.valueOf(cool);
		String coolmsg = wild.getConfig().getString("Cooldownmsg");
		GetRandomLocation random = new GetRandomLocation();
		if(p.hasPermission("wild.wildtp.cost.bypass")&&p.hasPermission("wild.wildtp.cooldown.bypass"))
			random.getWorldInfo(p);
		if(p.hasPermission("wild.wildtp.cost.bypass")&&!p.hasPermission("wild.wildtp.cooldown.bypass"))
		{
			if(Wild.check(p))
				random.getWorldInfo(p);
			else
			{
				if(coolmsg.contains("{cool}"))
					coolmsg = coolmsg.replace("{cool}", Cool);
				else if(coolmsg.contains("{rem}"))
					coolmsg = coolmsg.replace("{rem}", String.valueOf(Wild.getRem(p)));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolmsg));
			}
		}
		if(!p.hasPermission("wild.wildtp.cost. bypass")&&p.hasPermission("wild.wildtp.cooldown.bypass"))
		{
			if(cost >= econ.getBalance(p))
			{
				EconomyResponse r =econ.withdrawPlayer(p, cost);
				if(r.transactionSuccess())
				{
					random.getWorldInfo(p);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
					

				}
				else
				{
					p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
				}
			}
			else
				p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
		}
		if(!p.hasPermission("wild.wildtp.cost.bypass")&&!p.hasPermission("wild.wildtp.cooldown.bypass"))
		{
			if(!Wild.check(p))
			{
				if(coolmsg.contains("{cool}"))
					coolmsg = coolmsg.replace("{cool}", Cool);
				else if(coolmsg.contains("{rem}"))
					coolmsg = coolmsg.replace("{rem}", String.valueOf(Wild.getRem(p)));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', coolmsg));
			}
			else
			{
				if(cost >= econ.getBalance(p))
				{
					EconomyResponse r =econ.withdrawPlayer(p, cost);
					if(r.transactionSuccess())
					{
						random.getWorldInfo(p);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
						

					}
					else
					{
						p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
					}
				}
				else
					p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
			}
		}
		
	}

}
