package me.Qball.Wild;

import java.util.ArrayList;
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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.Qball.Wild.Commands.*;
import me.Qball.Wild.Utils.ClaimChecks;
import me.Qball.Wild.GUI.HookClick;
import me.Qball.Wild.GUI.InvClick;
import me.Qball.Wild.GUI.SetVal;
import me.Qball.Wild.Listeners.PlayMoveEvent;
import me.Qball.Wild.Listeners.SignBreak;
import me.Qball.Wild.Listeners.SignChange;
import me.Qball.Wild.Listeners.SignClick;
import me.Qball.Wild.Utils.CheckConfig;
import me.Qball.Wild.Utils.Checks;
import me.Qball.Wild.Utils.GetHighestNether;
import me.Qball.Wild.Utils.GetRandomLocation;
import me.Qball.Wild.Utils.LoadDependencies;
import me.Qball.Wild.Utils.OldFormatConverter;
import me.Qball.Wild.Utils.Sounds;
import me.Qball.Wild.Utils.TeleportTar;
import me.Qball.Wild.Utils.WildTpBack;

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
	public static boolean water = false;
	public static boolean loaded = false;
	public static boolean inNether = false;
	public static boolean inEnd = false;
	public static Wild plugin;
	public static Wild instance;
	public static HashMap<UUID, Integer> cooldownCheck = new HashMap<UUID, Integer>();
	public static int Rem;
	public int cost = this.getConfig().getInt("Cost");
	public String costMSG = this.getConfig().getString("Costmsg");
	public String strCost = String.valueOf(cost);
	public String costMsg = costMSG.replaceAll("\\{cost\\}", strCost);
	public int retries = this.getConfig().getInt("Retries");
	public static Economy econ = null;
	public static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();

	public void onDisable() {
		plugin = null;
		HandlerList.unregisterAll((Plugin) this);
		econ = null;
	}

	public void onEnable()
	{
		this.getCommand("wildtp").setExecutor(new CmdWildTp(this));
		plugin = this;
		instance = this; 
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.saveResource("PotionsEffects.txt", true);
		this.saveResource("Biomes.txt", true);
		this.saveResource("Sounds.txt", true);
		Bukkit.getPluginManager().registerEvents((Listener) this, this);
		Bukkit.getPluginManager().registerEvents(new InvClick(), this);
		Bukkit.getPluginManager().registerEvents(new SetVal(), this);
		Bukkit.getPluginManager().registerEvents(new SignChange(), this);
		Bukkit.getPluginManager().registerEvents(new SignBreak(), this);
		Bukkit.getPluginManager().registerEvents(new SignClick(), this);
		Bukkit.getPluginManager().registerEvents(new HookClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayMoveEvent(), this);
		LoadDependencies.loadAll(); 
		cooldownTime = new HashMap<UUID, Long>();
		Sounds.init();
		CheckConfig check = new CheckConfig();
		if (!check.isCorrectPots()) {
			logger.info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
			logger.info("Plugin will now disable");
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
		if (!setupEconomy()) {
			Bukkit.getLogger().severe(String.format(
					"[%s] - Disabled due to no Vault dependency found!",
					getDescription().getName()));
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		OldFormatConverter.convert();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static void reload(Player p) {
		CheckConfig check = new CheckConfig();
		Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
		if (!check.isCorrectPots()) {
			Bukkit.getLogger()
					.info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
			Bukkit.getLogger().info("Plugin will now disable");
			Bukkit.getPluginManager().disablePlugin(plugin);
		} else {
			p.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN
					+ "WildnernessTP" + ChatColor.BLACK + "]" + ChatColor.GREEN
					+ "Plugin config has successfuly been reload");
		}
	}

	public static Wild getInstance() {
		return instance;
	}

	public static List<String> getListPots() {
		@SuppressWarnings("unchecked")
		List<String> potions = ((List<String>) instance.getConfig().getList(
				"Potions"));
		return potions;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String args[]) {
		int cool = this.getConfig().getInt("Cooldown");
		String Cool = String.valueOf(cool);
		String coolmsg = this.getConfig().getString("Cooldownmsg");
		String Coolmsg = coolmsg.replaceAll("\\{cool\\}", Cool);
		GetRandomLocation random = new GetRandomLocation();
		if (cmd.getName().equalsIgnoreCase("wild")) {

			if (sender instanceof Player)

			{
				final Player player1 = (Player) sender;
				final Player player = (Player) sender;
				if (player1.hasPermission("Wild.wildtp")) {
					WildTpBack wildtp = new WildTpBack();
					wildtp.saveLoc(player, player.getLocation());
					if (args.length == 0) {
						final Player target = (Player) sender;
						if (target.hasPermission("wild.wildtp.cooldown.bypass")) {
							if (target.hasPermission("wild.wildtp.cost.bypass")) {
								if (Checks.World(target) == true) {

									random.getWorldInfo(target);
								} else {
									target.sendMessage(ChatColor.RED
											+ "Command cannot be used in this world");
								}
							} else {
								if (Checks.World(target) == true) {
									if (econ.getBalance(target) >= cost) {

										EconomyResponse r = econ
												.withdrawPlayer(target, cost);
										if (r.transactionSuccess()) {
											random.getWorldInfo(target);
											target.sendMessage(ChatColor
													.translateAlternateColorCodes(
															'&', costMsg));
										} else {
											target.sendMessage(ChatColor.RED
													+ "Something has gone wrong sorry but we will be unable to teleport you :( ");
										}
									} else {
										target.sendMessage(ChatColor.RED
												+ "You do not have enough money to use this command");
									}
								} else {
									int Remand = getRem(target);
									String rem = String.valueOf(Remand);
									Coolmsg = Coolmsg.replaceAll("\\{rem\\}",
											rem);
									target.sendMessage(ChatColor
											.translateAlternateColorCodes('&',
													Coolmsg));
								}

							}
						} else {
							if (Checks.World(target) == true) {
								if (check(target)) {

									if (econ.getBalance(target) >= cost) {
										EconomyResponse r = econ
												.withdrawPlayer(target, cost);
										if (r.transactionSuccess()) {
											random.getWorldInfo(target);
											if (plugin.getConfig().getBoolean(
													"DoCostMsg")) {
												target.sendMessage(ChatColor
														.translateAlternateColorCodes(
																'&', costMsg));
											}
										} else {
											target.sendMessage(ChatColor.RED
													+ "Something has gone wrong sorry but we will be unable to teleport you :( ");
										}
									} else {
										target.sendMessage(ChatColor.RED
												+ "You do not have enough money to use this command");
									}

								} else {
									int Remand = getRem(target);
									String rem = String.valueOf(Remand);

									Coolmsg = Coolmsg.replaceAll("\\{rem\\}",
											rem);
									target.sendMessage(ChatColor
											.translateAlternateColorCodes('&',
													Coolmsg));
								}
							}

							else {
								target.sendMessage(ChatColor.RED
										+ "Command cannot be used in this world");
							}

						}
					}

					else if (args.length == 1) {

						if (args[0] != null) {
							final Player target = Bukkit.getServer().getPlayer(
									args[0]);

							if (target == null) {
								sender.sendMessage(args[0] + " "
										+ ChatColor.RED + "is not online!!!!");
								return true;
							}
							if (player1.hasPermission("Wild.wildtp.others")) {
								if (player1
										.hasPermission("wild.wildtp.cooldown.bypass")) {
									if (player1
											.hasPermission("wild.wildtp.cost.bypass")) {
										if (inNether) {
											player1.sendMessage(ChatColor.RED
													+ "Target is in the nether and thus cannot be teleported");
										} else {
											if (inEnd) {
												player1.sendMessage(ChatColor.RED
														+ "Target is in the end thus cannot be teleported");
											} else {
												if (Checks.World(target) == true) {
													random.getWorldInfo(target);
												} else {
													player1.sendMessage(ChatColor.RED
															+ "Target is in a world where the command cannot be used");
												}

											}
										}
									} else {
										if (inNether) {
											player1.sendMessage(ChatColor.RED
													+ "Target is in the nether and thus cannot be used");

										} else {
											if (inEnd) {
												player1.sendMessage(ChatColor.RED
														+ "Target in is the End and thus cannot be teleported");
											} else {
												if (Checks.World(target) == true) {
													if (econ.getBalance(player1) >= cost) {

														EconomyResponse r = econ
																.withdrawPlayer(
																		player1,
																		cost);
														if (r.transactionSuccess()) {
															random.getWorldInfo(target);
															if (plugin
																	.getConfig()
																	.getBoolean(
																			"DoCostMsg")) {
																player1.sendMessage(ChatColor
																		.translateAlternateColorCodes(
																				'&',
																				costMsg));
															}
															player1.sendMessage(ChatColor.GREEN
																	+ " You have thrown"
																	+ target.getCustomName());
														}

													} else {
														player1.sendMessage(ChatColor.RED
																+ "You do not have enough money to use this command");
													}

												}

												else {
													player1.sendMessage(ChatColor.RED
															+ "Target is in a world where the command cannot be used");
												}
											}
										}
									}
								} else {
									if (Checks.World(target) == true) {
										if (!check(player1)) {
											int Remand = getRem(player1);
											String rem = String.valueOf(Remand);
											Coolmsg = Coolmsg.replaceAll(
													"\\{rem\\}", rem);
											player1.sendMessage(ChatColor
													.translateAlternateColorCodes(
															'&', Coolmsg));
										} else {

											if (econ.getBalance(target) >= cost) {

												EconomyResponse r = econ
														.withdrawPlayer(
																player1, cost);
												if (r.transactionSuccess()) {

													random.getWorldInfo(target);
													player1.sendMessage(ChatColor
															.translateAlternateColorCodes(
																	'&',
																	costMsg));
													player1.sendMessage(ChatColor.GREEN
															+ " You have thrown"
															+ target.getCustomName());

												} else {
													player1.sendMessage(ChatColor.RED
															+ "Something has gone wrong sorry but we will be unable to teleport you :( ");
												}
											} else {
												player1.sendMessage(ChatColor.RED
														+ "You do not have enough money to use this command");
											}

										}
									}

									else {
										player1.sendMessage(ChatColor.RED
												+ "Target is in a world where the command cannot be used");
									}
								}
							} else {
								player.sendMessage(ChatColor.RED
										+ "You lack the permission to teleport other players");
							}
						}

					}

				} else {
					Player player_ = (Player) sender;
					player_.sendMessage(ChatColor.RED
							+ "Sorry but you dont have permsioson to do /wild :( please ask an admin why");
				}

			} else {
				if (args.length >= 1) {
					sender.sendMessage("You must be a player!");
					return false;
				} else if (args.length == 1) {
					if (args[0] != null) {
						final Player target = Bukkit.getServer().getPlayer(
								args[0]);
						if (target == null) {
							sender.sendMessage(args[0] + " " + ChatColor.RED
									+ "is not online!!");
							return true;
						}
					}

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
			int Rem = cool + (int) convert;
			if (convert >= cool) {
				cooldownTime.put(p.getUniqueId(), now);
				try {
					cooldownCheck.remove(p.getUniqueId());
				} catch (NullPointerException e) {
				}
				return true;
			}
			cooldownCheck.put(p.getUniqueId(), Rem);
			return false;
		} else {
			cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
			try {
				cooldownCheck.remove(p.getUniqueId());
			} catch (NullPointerException e) {
			}
			return true;
		}
	}

	public static int getRem(Player p) {
		int rem = 0;
		if (cooldownCheck.containsKey(p.getUniqueId())) {
			rem = cooldownCheck.get(p.getUniqueId());
		}
		return rem;
	}

	public static void applyPotions(Player p) {

		@SuppressWarnings("unchecked")
		List<String> potions = ((List<String>) plugin.getConfig().getList(
				"Potions"));
		int size = potions.size();
		if (size != 0) {
			for (int i = 0; i <= size - 1; i++) {
				String potDur = potions.get(i).toString();
				String[] PotDur = potDur.split(":");
				String pot = PotDur[0];
				String dur = PotDur[1];
				int Dur = Integer.parseInt(dur) * 20;
				pot = pot.toUpperCase();
				PotionEffectType Potion = PotionEffectType.getByName(pot);
				p.addPotionEffect(new PotionEffect(Potion, Dur, 100));

			}
		}
	}

	public void random(Player e, Location location) {
		final Player target = e;
		GetRandomLocation random = new GetRandomLocation();
		String Message = plugin.getConfig().getString("No Suitable Location");
		int x = location.getBlockX();
		int z = location.getBlockZ();
		TeleportTar tele = new TeleportTar();

		if (Checks.inNether(x, z, target) == true) {
			int y = GetHighestNether.getSoildBlock(x, z, target);

			Location done = new Location(target.getWorld(), x +.5, y, z+.5, 0.0F, 0.0F);

			tele.TP(done, target);
		} else {
			ClaimChecks claims = new ClaimChecks();
			Location loc = new Location(location.getWorld(),location.getBlockX()+.5,location.getBlockY(),location.getBlockZ()+.5,0.0F,0.0F);
			if (Checks.getLiquid(loc) 
					|| claims.townyClaim(loc)
					|| claims.factionsClaim(loc)
					|| claims.greifPrevnClaim(loc)
					|| claims.worldGuardClaim(loc)) {

				if (plugin.getConfig().getBoolean("Retry")) {
					for (int i = retries; i >= 0; i--) {
						String info = random.getWorldInfomation(target);
						Location temp = random.getRandomLoc(info, target);
						Location test = new Location(temp.getWorld(),temp.getBlockX()+.5,temp.getBlockY(),temp.getBlockZ()+.5,0.0F,0.0F);
						if (!Checks.getLiquid(test)
								&& !claims.townyClaim(test)
								&& !claims.factionsClaim(test)
								&& !claims.greifPrevnClaim(test)
								&& !claims.worldGuardClaim(test)
								&& !claims.kingdomClaimCheck(test)) {
							if (plugin.getConfig().getBoolean("Play") == false) {
								tele.TP(test, target);
							} else {
								tele.TP(test, target);

							}
							break;
						} 
						if (i == 0) {
							target.sendMessage(ChatColor
									.translateAlternateColorCodes((char) '&',
											Message));
							cooldownTime.remove(e.getPlayer().getUniqueId());
							cooldownCheck.remove(e.getPlayer().getUniqueId());
						}
					}
				} else {
					target.sendMessage(ChatColor.translateAlternateColorCodes(
							(char) '&', Message));
					cooldownTime.remove(e.getPlayer().getUniqueId());
					cooldownCheck.remove(e.getPlayer().getUniqueId());
				}

			} else {

			
				Checks.ChunkLoaded(location.getChunk().getX(),
						location.getChunk().getZ(), target);
				Location loco = new Location(location.getWorld(),location.getBlockX()+.5,location.getBlockY(),location.getBlockZ()+.5,0.0F,0.0F);
				tele.TP(loco, target);

			}
		}
	}

}
