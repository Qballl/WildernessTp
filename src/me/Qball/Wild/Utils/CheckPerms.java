package me.Qball.Wild.Utils;


import me.Qball.Wild.Wild;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckPerms {
	private final Wild wild;
	public CheckPerms(Wild wild)
	{
		this.wild = wild;
	}
	public Economy econ;
	public void check(Player p)
	{
		
		WildTpBack back = new WildTpBack();
		back.saveLoc(p, p.getLocation());
		int cost = wild.getConfig().getInt("Cost");
		String costMSG = wild.getConfig().getString("Costmsg");
		String strCost = String.valueOf(cost);
		String costMsg = costMSG.replaceAll("\\{cost\\}", strCost);
		int cool = wild.getConfig().getInt("Cooldown");
		String Cool = String.valueOf(cool);
		String coolmsg = wild.getConfig().getString("Cooldownmsg");
		GetRandomLocation random = new GetRandomLocation(wild);
		if(cost>0)
			econ = wild.getEcon();
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
			if(cost > 0){
			if(econ.getBalance(p) >= cost)
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
			else{
				random.getWorldInfo(p);
			}
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
				if(cost>0){
				if(econ.getBalance(p) >= cost)
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
				else{
					random.getWorldInfo(p);
				}
			}
		}
		
	}
	public void check(Player p,Player target)
	{
		WildTpBack back = new WildTpBack();
		back.saveLoc(target, target.getLocation());
		int cost = wild.getConfig().getInt("Cost");
		String costMSG = wild.getConfig().getString("Costmsg");
		String strCost = String.valueOf(cost);
		String costMsg = costMSG.replaceAll("\\{cost\\}", strCost);
		int cool = wild.getConfig().getInt("Cooldown");
		String Cool = String.valueOf(cool);
		String coolmsg = wild.getConfig().getString("Cooldownmsg");
		GetRandomLocation random = new GetRandomLocation(wild);
		if(cost > 0)
			econ = wild.getEcon();
		if(p.hasPermission("wild.wildtp.cost.bypass")&&p.hasPermission("wild.wildtp.cooldown.bypass"))
			random.getWorldInfo(target);
		if(p.hasPermission("wild.wildtp.cost.bypass")&&!p.hasPermission("wild.wildtp.cooldown.bypass"))
		{
			if(Wild.check(p))
			{
				random.getWorldInfo(target);
				target.sendMessage(ChatColor.GREEN+ p.getDisplayName() + " threw you to radom location");
			}
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
			if(cost >0){
			if(econ.getBalance(p) >= cost)
			{
				EconomyResponse r =econ.withdrawPlayer(p, cost);
				if(r.transactionSuccess())
				{
					random.getWorldInfo(target);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
					target.sendMessage(ChatColor.GREEN+ p.getDisplayName() + " threw you to radom location");

				}
				else
				{
					p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
				}
			}
			else
				p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
			}
			else{
				random.getWorldInfo(target);
				target.sendMessage(ChatColor.GREEN+ p.getDisplayName() + " threw you to radom location");
			}
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
				if(cost > 0){
				if(econ.getBalance(p) >= cost)
				{
					EconomyResponse r =econ.withdrawPlayer(p, cost);
					if(r.transactionSuccess())
					{
						random.getWorldInfo(target);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg));
						target.sendMessage(ChatColor.GREEN+ p.getDisplayName() + " threw you to radom location");

					}
					else
					{
						p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
					}
				}
				else
					p.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
				}
				else
				{
					random.getWorldInfo(target);
					target.sendMessage(ChatColor.GREEN+ p.getDisplayName() + " threw you to radom location");
				}
			}
		}
		
	}
}
