package me.Qball.Wild.Commands;

import me.Qball.Wild.Utils.Checks;
import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.CheckPerms;
import me.Qball.Wild.Utils.GetRandomLocation;
import me.Qball.Wild.Utils.WildTpBack;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWild implements CommandExecutor{
	private final Wild wild;
	public CmdWild(Wild wild)
	{
		this.wild = wild; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		CheckPerms check = new CheckPerms(wild);
		Checks checks = new Checks(wild);
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(args.length==0){
				if(!checks.world(p))
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',wild.getConfig().getString("WorldMsg")));
				else
					check.check(p);
			}
			else if(args.length ==1)
			{
				if(Bukkit.getServer().getPlayer(args[0])!= null)
				{
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(p.hasPermission("wild.wildtp.others"))
					{
						if(checks.world(target))
							check.check(p,target);
						else
							p.sendMessage(ChatColor.RED+"The specified player is in a world where the command cannot be used");
					}
				}
				else
					p.sendMessage(ChatColor.RED+ "Player " + args[0] + " is not online");
			}
			return true;
			
		}
		else
		{
			if(args.length == 0){
				sender.sendMessage("Console cannot use this command except on other players");
			}
			else if(args.length == 1)
			{
				if(Bukkit.getServer().getPlayer(args[0]) != null)
				{
					Player target = Bukkit.getServer().getPlayer(args[0]);
					WildTpBack back = new WildTpBack();
					back.saveLoc(target, target.getLocation());
					GetRandomLocation random = new GetRandomLocation(wild);
					random.getWorldInfo(Bukkit.getPlayer(args[0]));
					target.sendMessage(ChatColor.GREEN+"Thrown to a random location by the console");
					return true;
				}
				else
				{
					sender.sendMessage("Player " + args[0] + " is not online");
				}
			}
		}
		return false;
	}
	
}
