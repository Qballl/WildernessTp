package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class SignClick implements Listener {
	public static Economy econ = Wild.econ;
	public static Plugin wild = Wild.getInstance();
	public static int Rem;
	public static int cool = wild.getConfig().getInt("Cooldown");
	public int cost = wild.getConfig().getInt("Cost");
	String costmsg = wild.getConfig().getString("Costmsg");
	String Cost = String.valueOf(cost);
	String Costmsg = costmsg.replaceAll("\\{cost\\}", Cost);
	String Cool = String.valueOf(cool);
	String coolmsg = wild.getConfig().getString("Cooldownmsg");
	String Coolmsg = coolmsg.replaceAll("\\{cool\\}",Cool);
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent target) {
		
		Player Target = target.getPlayer();
		Sign sign;
		if (target.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (target.getClickedBlock().getState() instanceof Sign) {
			sign = (Sign) target.getClickedBlock().getState();
			if (sign.getLine(1).equalsIgnoreCase("[§1Wild§0]")&& sign.getLine(0).equalsIgnoreCase("§4====================")) {

				if (Target.hasPermission("wild.wildtp.cooldown.bypass")&&Target.hasPermission("wild.wildtp.cost.bypass"))
				{
					Wild.Random(Target);
				}
				else if (Target.hasPermission("!wild.wildtp.cooldown.bypass")&&Target.hasPermission("wild.wildtp.cost.bypass"))
				{
				if (Wild.check(Target)) {
					Wild.Random(Target);
				} else {
					String rem = String.valueOf(Rem);
					Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
					Target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));

				}
				}
				else if (Target.hasPermission("wild.wildtp.cooldown")&&!Target.hasPermission("wild.wildtp.cost.bypass"))
				{
					if(econ.getBalance(Target) >= cost)
					{
						
						EconomyResponse r =econ.withdrawPlayer(Target, cost);
						if(r.transactionSuccess())
						{
							Wild.Random(Target);
							Target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
							

						}
						else
						{
							Target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
						}
					}
					else
					{
						Target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
					}

				}
				else if (!Target.hasPermission("wild.wildtp.cooldown")&&!Target.hasPermission("wild.wildtp.cost.bypass"))
				{
					if(Wild.check(Target))
					{
					if(econ.getBalance(Target) >= cost)
					{
						
						EconomyResponse r =econ.withdrawPlayer(Target, cost);
						if(r.transactionSuccess())
						{
							Wild.Random(Target);
							Target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
							

						}
						else
						{
							Target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
						}
					}
					else
					{
						Target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
					}

				}
					else
				{
						String rem = String.valueOf(Rem);
						Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
						Target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
				}
				}
				
			
			}
		}
	}

	
}
