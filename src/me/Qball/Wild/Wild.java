package me.Qball.Wild;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import java.util.Random;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.Qball.Wild.Commands.*;
import me.Qball.Wild.GUI.InvClick;
import me.Qball.Wild.GUI.SetVal;
import me.Qball.Wild.Listeners.SignBreak;
import me.Qball.Wild.Listeners.SignChange;
import me.Qball.Wild.Listeners.SignClick;
import me.Qball.Wild.Utils.Checks;
import me.Qball.Wild.Utils.GetHighestNether;
import me.Qball.Wild.Utils.Sounds;
import me.Qball.Wild.Utils.TeleportTar;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wild extends JavaPlugin implements Listener {
	public final Logger logger = Bukkit.getServer().getLogger();
	public static HashMap<UUID, Long> cooldownTime;
	public static boolean Water = false;
	public static boolean loaded = false;
	public static boolean inNether = false;
	public static boolean inEnd = false;
	public static Wild plugin;
	public Plugin wild = plugin;
	public static Wild instance;
	public static HashMap<UUID,Integer>cooldownCheck = new HashMap<UUID,Integer>();
	public static int Rem;
	public int cost = this.getConfig().getInt("Cost");
	String costmsg = this.getConfig().getString("Costmsg");
	String Cost = String.valueOf(cost);
	String Costmsg = costmsg.replaceAll("\\{cost\\}", Cost);	
	public static Plugin config = getInstance();
	public static Economy econ = null;
	
	public void onDisable() {
		plugin = null;
		HandlerList.unregisterAll((Plugin)this);


	}

	public void onEnable()
  
	{ 
		
		this.getCommand("wildtp").setExecutor(new CmdWildTp(this));
		plugin = this; 
		instance = this;
		
		Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
		Bukkit.getPluginManager().registerEvents(new InvClick(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents(new SetVal(), (Plugin)this);
		Bukkit.getPluginManager().registerEvents(new SignChange(),(Plugin)this);
		Bukkit.getPluginManager().registerEvents(new SignBreak(), (Plugin)this);
		Bukkit.getPluginManager().registerEvents(new SignClick(), (Plugin)this);		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig(); 
		this.saveResource("PotionsEffects.txt", true);
		cooldownTime = new HashMap<UUID, Long>(); 
		Sounds.init();
		if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
          
        }
		if(this.getConfig().getBoolean("Play")==true)
		{
		if(Sounds.Match()==false)
           {
			String Sound = this.getConfig().getString("Sound");
			logger.info("Error specifed sound cannot be found please check config");
			logger.info(Sound);
			logger.info("Disabling plugin");
			
        	 Bukkit.getServer().getPluginManager().disablePlugin(this);
        	   return;
           }
		if (this.getConfig().getBoolean("Towny"))
		{
			 if (getServer().getPluginManager().getPlugin("Towny") == null) {
		            getServer().getPluginManager().disablePlugin(this);
		        }
			 else
			 {
				 Bukkit.getLogger().info("Towny hook enabled");
			 }
		}
	      }
			if (this.getConfig().getBoolean("Factions"))
			{
				 if (getServer().getPluginManager().getPlugin("Factions") == null) {
			            getServer().getPluginManager().disablePlugin(this);
			        }
				 else
				 {
					 Bukkit.getLogger().info("Factions hook enabled");
				 }
			}
		
	}
	  private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }
	  public static void Reload(Player e) {
			Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
			if(Sounds.Match()==false)
			{
				String Sound = config.getConfig().getString("Sound");
				e.sendMessage(ChatColor.RED+"Error specifed sound cannot be found please check config");
				e.sendMessage(ChatColor.RED + Sound + "Is not a vaild sound");
				e.sendMessage(ChatColor.RED+"Disabling plugin");
				
	        	
	        	 return;
			}
			else
			{
			e.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN + "WildnernessTP"+ ChatColor.BLACK + "]" + ChatColor.GREEN+ "Plugin config has successfuly been reload");
			}
		}
	public static Wild getInstance()
	{
		return instance;
	}
	public static List<String> getListPots()
	{ 
		@SuppressWarnings("unchecked")
		List<String> potions = ((List<String>) instance.getConfig().getList("Potions"));
		return potions;
	}
	public static List<String> getWorlds()
	{
		@SuppressWarnings("unchecked")
		List<String> Worlds = ((List<String>) instance.getConfig().getList("Worlds"));
		return Worlds;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String args[]) {
	  int cool = plugin.getConfig().getInt("Cooldown");
		String Cool = String.valueOf(cool);
		String coolmsg = this.getConfig().getString("Cooldownmsg");
		String Coolmsg = coolmsg.replaceAll("\\{cool\\}",Cool);

		if (cmd.getName().equalsIgnoreCase("Wild")) {

			if (sender instanceof Player)

			{
				final Player player1 = (Player) sender;
				final Player player = (Player) sender;

				if (player1.hasPermission("Wild.wildtp")) {

					if (args.length == 0) {
						final Player target = (Player) sender;
						if (target.hasPermission("wild.wildtp.cooldown.bypass"))
						{	
							if (target.hasPermission("wild.wildtp.cost.bypass"))
									{
									if (Checks.World(target)==true)
									{
									Random(target);
									}
									else
									{
										target.sendMessage(ChatColor.RED+"Command cannot be used in this world");
									}
									}
							else{
								if (Checks.World(target)==true)
								{
								if(econ.getBalance(target) >= cost)
								{
									
									EconomyResponse r =econ.withdrawPlayer(target, cost);
									if(r.transactionSuccess())
									{
										Random(target);
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
									}
									else
									{
										target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
									}
								}
								else
								{
									target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
							}
								}
								else
								{
									int Remand = getRem(target);
									String rem = String.valueOf(Remand);
									Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
						 			target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
								}
							
							
						}
						}
						else
						{
							if (Checks.World(target)==true)
							{
						if (check(target)) {
						
						
							if(econ.getBalance(target) >= cost)
							{
								
								EconomyResponse r =econ.withdrawPlayer(target, cost);
								if(r.transactionSuccess())
								{
									Random(target);
									target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
								}
								else
								{
									target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
								}
							}
							else
							{
								target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
							}
							

						} else {
							int Remand = getRem(target);
							String rem = String.valueOf(Remand);
							
							Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
						}
							}
						
						else
						{
							target.sendMessage(ChatColor.RED+"Command cannot be used in this world");
						}

					}
					}

					else if (args.length == 1) {

						if (args[0] != null) {
							final Player target = Bukkit.getServer().getPlayer(
									args[0]);
 
							if (target == null) {
								sender.sendMessage(args[0] + " "+ ChatColor.RED + "is not online!!!!");
								return true;
							}
							if (player1.hasPermission("Wild.wildtp.others")) {
								if(player1.hasPermission("wild.wildtp.cooldown.bypass"))
								{
									if(player1.hasPermission("wild.wildtp.cost.bypass"))
									{
									if (inNether == true) {
										player1.sendMessage(ChatColor.RED+ "Target is in the nether and thus cannot be teleported");
									} else {
										if (inEnd == true) {
											player1.sendMessage(ChatColor.RED+ "Target is in the end thus cannot be teleported");
										} else {
											if (Checks.World(target)==true)
											{
											Random(target);
											}
											else
											{
												player1.sendMessage(ChatColor.RED+"Target is in a world where the command cannot be used"); 
											}

										}
									}
									}
									else
									{
										if(inNether==true)
										{
											player1.sendMessage(ChatColor.RED +"Target is in the nether and thus cannot be used");
											
										}
										else
										{
											if (inEnd==true)
											{
												player1.sendMessage(ChatColor.RED+ "Target in is the End and thus cannot be teleported");
											}
											else
											{
												if (Checks.World(target)==true)
												{
												if(econ.getBalance(player1) >= cost)
												{
													
													EconomyResponse r =econ.withdrawPlayer(player1, cost);
													if(r.transactionSuccess())
													{
														Random(target);
														player1.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
														player1.sendMessage(ChatColor.GREEN +" You have thrown"+ target.getCustomName());
													}
													
												}
												else
												{
													player1.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
												}
												
											}
											
											else
											{
												player1.sendMessage(ChatColor.RED+"Target is in a world where the command cannot be used");
											}
										}
									}
								}
								}
								else{
									if (Checks.World(target)==true)
									{
								if (!check(player1)) {
									int Remand = getRem(player1);
									String rem = String.valueOf(Remand);
									Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
									player1.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
								}
								else
								{
								if (inNether == true) {
									player1.sendMessage(ChatColor.RED+ "Target is in the nether and thus cannot be teleported");
								} else {
									
										if(econ.getBalance(target) >= cost)
										{
											
											EconomyResponse r =econ.withdrawPlayer(player1, cost);
											if(r.transactionSuccess())
											{
												
												Random(target);
												player1.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
												player1.sendMessage(ChatColor.GREEN +" You have thrown"+ target.getCustomName());

											}
											else
											{
												player1.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
											}
										}
										else
										{
											player1.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
										}
										
									}
							 		}
								
								}
								
								else
								{
									player1.sendMessage(ChatColor.RED+"Target is in a world where the command cannot be used");
								}
							}
							}
							else {
								player.sendMessage(ChatColor.RED + "You lack the permission to teleport other players");
							}
						}
						
					}

				} else {
					Player player_ = (Player) sender;
					player_.sendMessage(ChatColor.RED+"Sorry but you dont have permsioson to do /wild :( please ask an admin why");
				}

			} else {
				if (args.length >= 1) {
					sender.sendMessage("You must be a player!");
					return false;
				} else if (args.length == 1) {
					if (args[0] != null) {
						final Player target = Bukkit.getServer().getPlayer(
								args[0]);
						Random(target);
						if (target == null) {
							sender.sendMessage(args[0]+ " "+ ChatColor.RED+"is not online!!");
							return true;
						}
					}

				}

				{

				}

			}

		}
			
		return false;
	}

	public static boolean check(Player p) {
		  int cool = plugin.getConfig().getInt("Cooldown");
		
		if (cooldownTime.containsKey(p.getUniqueId())) {
			long old = cooldownTime.get(p.getUniqueId()); 
			long now = System.currentTimeMillis();

			long diff = now - old;
			
			long convert = TimeUnit.MILLISECONDS.toSeconds(diff);
			int Rem =  cool + (int)convert ; 
			if (convert >= cool) {
				cooldownTime.put(p.getUniqueId(), now);
				try
				{
					cooldownCheck.remove(p.getUniqueId());
				}
				catch(NullPointerException e)
				{}
				return true;
			}
			cooldownCheck.put(p.getUniqueId(), Rem);
			return false;
		} else {
			cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
			try
			{
				cooldownCheck.remove(p.getUniqueId());
			}
			catch(NullPointerException e)
			{}
			return true;
		}
	}
	public static int getRem(Player p )
	{
		int rem = 0;
		if(cooldownCheck.containsKey(p.getUniqueId()))
		{
			rem = cooldownCheck.get(p.getUniqueId());
		}
		return rem;
	}

	public static void applyPotions(Player p)
	{
		
		
		@SuppressWarnings("unchecked")
		List<String> potions = ((List<String>) plugin.getConfig().getList("Potions"));
		int size  = potions.size();
		for(int i = 0; i <= size-1 ; i++)
		{
			String potDur = potions.get(i).toString();
			String[] PotDur = potDur.split(":");
			String pot = PotDur[0];
			String dur = PotDur[1];
			int Dur = Integer.parseInt(dur)*20;
			pot = pot.toUpperCase();
			PotionEffectType Potion = PotionEffectType.getByName(pot);
			p.addPotionEffect(new PotionEffect(Potion,Dur,100));
		}
		
		
		
	}
	public static void Random(Player e) {
		final Player target = (Player) e;
		int MinX = plugin.getConfig().getInt("MinX");
		int MaxX = plugin.getConfig().getInt("MaxX");
		int MinZ = plugin.getConfig().getInt("MinZ");
		int MaxZ = plugin.getConfig().getInt("MaxZ");
		int retries = plugin.getConfig().getInt("Retries");
		String Message = plugin.getConfig().getString("No Suitable Location");
		Random rand = new Random();
		int x = rand.nextInt(MaxX - MinX + 1) + MinZ;
		int z = rand.nextInt(MaxZ - MinZ + 1) + MinZ;
		
		int Y1 = Checks.getSoildBlock(x, z, target);

		if (Checks.inNether(x, z, target) == true) {
			int y = GetHighestNether.getSoildBlock(x,z,target);
			if (y==0)
			{
				Random(target);
			}
			else
			{
				
					Location done = new Location(target.getWorld(),x,y,z,0.0F,0.0F);
					TeleportTar.TP(done, target);
				
			}
		
		} else {
			
				if (Checks.getLiquid(x, z, target) == true) {
					if (plugin.getConfig().getBoolean("Retry") == true) {
						for (int i = retries; i <= 0; i--) {
							x = rand.nextInt(MaxX - MinX + 1) + MinZ;
							z = rand.nextInt(MaxZ - MinZ + 1) + MinZ;
							if (!Checks.getLiquid((int) (x), (int) (z), target)) {

								
						
								Y1 = Checks.getSoildBlock(x, z, target);
								Location done = new Location(target.getWorld(),x, Y1, z, 0.0F, 0.0F);
								Checks.ChunkLoaded(done.getChunk().getX(), done.getChunk().getZ(), target);
								if(plugin.getConfig().getBoolean("Play")==false)
								{
								TeleportTar.TP(done, target);
								
								}
								else
								{
								TeleportTar.TP(done, target); 
								
								}
								break;
							}
							
						}
					} else {
						target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
					}
				} else {
				

					Location done = new Location(target.getWorld(), x, Y1, z,0.0F, 0.0F);
					Checks.ChunkLoaded(done.getChunk().getX(), done.getChunk().getZ(), target);
					
					TeleportTar.TP(done,target);
				
				}
			}
		}
	

}
