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
import org.bukkit.block.Biome;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.Qball.Wild.Commands.*;
import me.Qball.Wild.GUI.MainGui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wild extends JavaPlugin implements Listener {
	public final Logger logger = Bukkit.getServer().getLogger();
	public HashMap<UUID, Long> cooldownTime;
	public static boolean Water = false;
	public static boolean loaded = false;
	public static boolean inNether = false;
	public static boolean inEnd = false;
	public static Wild plugin;
	public Plugin wild = plugin;
	public static Wild instance;
	public int cool = this.getConfig().getInt("Cooldown");
	public static int Rem;
	public int cost = this.getConfig().getInt("Cost");
	String costmsg = this.getConfig().getString("Costmsg");
	String Cost = String.valueOf(cost);
	String Costmsg = costmsg.replaceAll("\\{cost\\}", Cost);
	String Cool = String.valueOf(cool);
	String coolmsg = this.getConfig().getString("Cooldownmsg");
	String Coolmsg = coolmsg.replaceAll("\\{cool\\}",Cool);
	public static Plugin config = getInstance();
	public static Economy econ = null;
	
	public void onDisable() {
		plugin = null;

	}

	public void onEnable()
 
	{ 
		this.getCommand("wildtp").setExecutor(new CmdWildTp(this));
		plugin = this;
		instance = this;
		Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
		Bukkit.getPluginManager().registerEvents(new MainGui(), (Plugin) this);
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
				
	        	 Bukkit.getServer().getPluginManager().disablePlugin(plugin);
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

	
	public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String args[]) {
		

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
									String rem = String.valueOf(Rem);
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
							String rem = String.valueOf(Rem);
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
								sender.sendMessage(args[0] + " "+ (new StringBuilder().append(ChatColor.RED).append("is not online!!!!").toString()));
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
								if (check(player1)) {
									String rem = String.valueOf(Rem);
									Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
									player1.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
								}
								if (inNether == true) {
									player1.sendMessage(ChatColor.RED+ "Target is in the nether and thus cannot be teleported");
								} else {
									if (inEnd == true) {
										player1.sendMessage(ChatColor.RED+ "Target is in the end thus cannot be teleported");
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
								player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You lack the permission to teleport other players").toString());
							}
						}

					}

				} else {
					Player player_ = (Player) sender;
					player_.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Sorry but you dont have permsioson to do /wild :( please ask an admin why").toString());
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
							sender.sendMessage(args[0]+ " "+ (new StringBuilder()).append(ChatColor.RED).append("is not online!!").toString());
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

	public boolean check(Player p) {
		if (cooldownTime.containsKey(p.getUniqueId())) {
			long old = cooldownTime.get(p.getUniqueId()); 
			long now = System.currentTimeMillis();

			long diff = now - old;
			
			long convert = TimeUnit.MILLISECONDS.toSeconds(diff);
			Rem = (int) convert;
			if (convert >= cool) {
				cooldownTime.put(p.getUniqueId(), now);
				return true;
			}

			return false;
		} else {
			cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
			return true;
		}
	}
	public void applyPotions(Player p)
	{
		
		
		@SuppressWarnings("unchecked")
		List<String> potions = ((List<String>) this.getConfig().getList("Potions"));
		int size  = potions.size();
		for(int i = 0; i <= size-1 ; i++)
		{
			String pot = potions.get(i).toString();
			pot = pot.toUpperCase();
			PotionEffectType Potion = PotionEffectType.getByName(pot);
			p.addPotionEffect(new PotionEffect(Potion,400,100));
		}
		
		
		
	}
	public void Random(Player e) {
		final Player target = (Player) e;
		int MinX = this.getConfig().getInt("MinX");
		int MaxX = this.getConfig().getInt("MaxX");
		int MinZ = this.getConfig().getInt("MinZ");
		int MaxZ = this.getConfig().getInt("MaxZ");
		int retries = this.getConfig().getInt("Retries");
		String Message = this.getConfig().getString("No Suitable Location");
		String Teleport = this.getConfig().getString("Teleport");
		Random rand = new Random();
		int x = rand.nextInt(MaxX - MinX + 1) + MinZ;
		int z = rand.nextInt(MaxZ - MinZ + 1) + MinZ;
		
		int Y1 = Checks.getSoildBlock(x, z, target);

		if (Checks.inNether(x, z, target) == true) {
			target.sendMessage(ChatColor.RED+ "Command cannot be used in the nether");
		} else {
			if (Checks.inEnd(x, z, target) == true) {
				target.sendMessage(ChatColor.RED+ "Command cannot be used in end");
			} else {
				if (Checks.getLiquid(x, z, target) == true) {
					if (this.getConfig().getBoolean("Retry") == true) {
						for (int i = 0; i <= retries; i++) {
							x = rand.nextInt(MaxX - MinX + 1) + MinZ;
							z = rand.nextInt(MaxZ - MinZ + 1) + MinZ;
							if (!Checks.getLiquid((int) (x), (int) (z), target)) {

								applyPotions(target);
						
								Y1 = Checks.getSoildBlock(x, z, target);
								Location done = new Location(target.getWorld(),x, Y1, z, 0.0F, 0.0F);
								Checks.ChunkLoaded(done.getChunk().getX(), done.getChunk().getZ(), target);
								if(this.getConfig().getBoolean("Play")==false)
								{
								target.teleport(done);
								target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
								}
								else
								{
								target.teleport(done);
								target.playSound(done, Sounds.getSound(), 3, 10);
								target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
								}
								break;
							}
							
						}
					} else {
						target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
					}
				} else {
						applyPotions(target);
				

					Location done = new Location(target.getWorld(), x, Y1, z,0.0F, 0.0F);
					Checks.ChunkLoaded(done.getChunk().getX(), done.getChunk().getZ(), target);
					if(this.getConfig().getBoolean("Play")==false)
					{
					target.teleport(done);
					target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
					}
					else
					{
					target.teleport(done);
					target.playSound(done, Sounds.getSound(), 3, 10);
					target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
				}
				}
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent player) {
		String Message = this.getConfig().getString("No-Perm");
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		if (player.getLine(1).equalsIgnoreCase("[wild]")&& 
				player.getLine(0).equalsIgnoreCase("wildtp"))
						{
			if(player.getPlayer().hasPermission("wild.wildtp.create.sign"))
			{
			if (player.getPlayer().getWorld().getBiome(x, z) == Biome.HELL) {
				player.getPlayer().sendMessage(ChatColor.RED+ "Signs cannot be put in the nether");
				player.getBlock().breakNaturally();
				player.setCancelled(true);
			} else {
				if (player.getPlayer().getWorld().getBiome(x, z) == Biome.SKY)
					{
					player.getPlayer().sendMessage(ChatColor.RED+ "Signs cannot be put in the end");
					player.getBlock().breakNaturally();
					player.setCancelled(true);
				} else {
					player.setLine(0, "§4====================");
					player.setLine(1, "[§1Wild§0]");
					player.setLine(2, "§4====================");
					player.getPlayer().sendMessage(ChatColor.GREEN+ "Successfully made a new WildTP sign");
				}
			}
		}
			else 
		{
			player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
			player.setCancelled(true);
		}
		}
			

	}

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
					Random(Target);
				}
				else if (Target.hasPermission("!wild.wildtp.cooldown.bypass")&&Target.hasPermission("wild.wildtp.cost.bypass"))
				{
				if (check(Target)) {
					Random(Target);
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
							Random(Target);
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
					if(check(Target))
					{
					if(econ.getBalance(Target) >= cost)
					{
						
						EconomyResponse r =econ.withdrawPlayer(Target, cost);
						if(r.transactionSuccess())
						{
							Random(Target);
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
	@EventHandler
	public void BlockBreakEvent(BlockBreakEvent e)
	{
		 
		String NoPerm = this.getConfig().getString("No-Break");
		if(e.getBlock().getState() instanceof Sign)
		{
			Sign sign = (Sign) e.getBlock().getState();
			
			if(sign.getLine(0).equalsIgnoreCase("§4====================")&&
					sign.getLine(1).equalsIgnoreCase("[§1Wild§0]")&&
					sign.getLine(2).equalsIgnoreCase("§4===================="))
			{
				if(!e.getPlayer().hasPermission("wild.wildtp.break.sign"))
				{ 
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&', NoPerm));
					e.setCancelled(true);
				} 
				else 
				{
					e.getPlayer().sendMessage(ChatColor.GREEN + "You have broken a wildtp sign");
					return;
				}
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}
		 
	}
	

}
