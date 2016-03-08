package me.Qball.Wild;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import java.util.Random;
import org.bukkit.block.Biome;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	public int cost = this.getConfig().getInt("Cost");
	public static Economy econ = null;
	public void onDisable() {
		plugin = null;

	}

	public void onEnable()

	{
		instance = this;
		Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
		plugin = this;
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		cooldownTime = new HashMap<UUID, Long>();
		if(Sounds.getSound() == Sound.valueOf("AMBIENT_CAVE")|| Sounds.getSound() == Sound.valueOf("AMBIENCE_CAVE"));
		{
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
		if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
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

	public static Wild getInstance()
	{
		return instance;
	}

	public void Reload(Player e) {
		Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
		e.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN + "WildnernessTP"+ ChatColor.BLACK + "]" + ChatColor.GREEN+ "Plugin config has successfuly been reload");

	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String args[]) {
		if (cmd.getName().equalsIgnoreCase("Wildtp")) {
			if (sender instanceof Player) {
				final Player player = (Player) sender;
				if (args.length == 0) {
					player.sendMessage(ChatColor.GOLD+ "-------------------Help-------------------------");
					player.sendMessage(ChatColor.GOLD+ "* Command:       Description:                  *");
					player.sendMessage(ChatColor.GOLD+ "* /Wild Teleports player to random location    *");
					player.sendMessage(ChatColor.GOLD+ "* /Wild [player] Teleports the specfied player *");
					player.sendMessage(ChatColor.GOLD+ "* to a radom location                          *");
					player.sendMessage(ChatColor.GOLD+ "* /WildTp reload Reloads the plugin's config   *");
					player.sendMessage(ChatColor.GOLD+ "* /WildTp Shows This help message              *");
					player.sendMessage(ChatColor.GOLD+ "************************************************");
				}

				else if (args.length == 1) {

					final String reload = args[0];

					if (reload.equalsIgnoreCase("reload")) {
						if (!player.hasPermission("wild.wildtp.reload")) {
							player.sendMessage(ChatColor.RED+ "Sorry you do not have permission to reload the plugin");
						} else {
							Reload(player);
						}

					}
				}
			} else {

				if (args.length == 0) {
					sender.sendMessage("*****************Help***************************");
					sender.sendMessage("* Command:       Description:                  *");
					sender.sendMessage("* /Wild Teleports player to random location    *");
					sender.sendMessage("* /Wild [player] Teleports the specfied player *");
					sender.sendMessage("*  to a radom location                         *");
					sender.sendMessage("* /WildTp reload Reloads the plugin's config   *");
					sender.sendMessage("* /WildTp Shows This help message              *");
					sender.sendMessage("************************************************");
				}

				else if (args.length == 1) {

					String reload = args[0];

					if (reload.equalsIgnoreCase("reload")) {

						Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
						sender.sendMessage("[ WildnernessTP] Plugin config has successfuly been reload");

					}

				}
			}

		}

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
									Random(target);

									}
							
							
						}
						else
						{
						if (check(target)) {
							if(econ.getBalance(target.getName()) >= cost)
							{
								
								EconomyResponse r =econ.withdrawPlayer(target.getName(), cost);
								if(r.transactionSuccess())
								{
									Random(target);
									target.sendMessage(ChatColor.BOLD + "" + cost + ChatColor.GREEN+" has been withdraw from your account for using the command");
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
							target.sendMessage(ChatColor.RED + "You must wait "+ cool+ " second between each use of the command");

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
										
											Random(target);
											

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
												if(econ.getBalance(target.getName()) >= cost)
												{
													
													EconomyResponse r =econ.withdrawPlayer(target.getName(), cost);
													if(r.transactionSuccess())
													{
														Random(target);
														target.sendMessage(ChatColor.BOLD + "" + cost + ChatColor.GREEN+" has been withdraw from your account for using the command");
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
										}
									}
								}
								else{
								if (check(player1)) {
									player1.sendMessage(ChatColor.RED+ "You must wait "+ cool+ " second between each use of the command");

								}
								if (inNether == true) {
									player1.sendMessage(ChatColor.RED+ "Target is in the nether and thus cannot be teleported");
								} else {
									if (inEnd == true) {
										player1.sendMessage(ChatColor.RED+ "Target is in the end thus cannot be teleported");
									} else {
										if(econ.getBalance(target.getName()) >= cost)
										{
											
											EconomyResponse r =econ.withdrawPlayer(target.getName(), cost);
											if(r.transactionSuccess())
											{
												Random(target);
												target.sendMessage(ChatColor.BOLD + "" + cost + ChatColor.GREEN+" has been withdraw from your account for using the command");
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
				if (args.length == 0) {
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

								target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400, 50));
								
										target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,
												200, 50));
						
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
					target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 50));
					
							target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 50));
				

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
		if (player.getLine(1).equalsIgnoreCase("[wild]")&& player.getLine(0).equalsIgnoreCase("wildTp")) {
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

				if (Target.hasPermission("wild.wildtp.cooldown.bypass"))
				{
					Random(Target);

				}
				else
				{
				if (check(Target)) {
					Random(Target);
				} else {
					Target.sendMessage(ChatColor.RED + "You must wait " + cool+ " second between each use of the command or sign");

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
