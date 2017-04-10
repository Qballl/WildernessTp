package me.Qball.Wild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


import net.milkbowl.vault.economy.Economy;


import me.Qball.Wild.Listeners.*;
import me.Qball.Wild.Utils.*;
import me.Qball.Wild.Commands.*;
import me.Qball.Wild.GUI.HookClick;
import me.Qball.Wild.GUI.InvClick;
import me.Qball.Wild.GUI.SetVal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


public class Wild extends JavaPlugin implements Listener {
    public static HashMap<UUID, Long> cooldownTime;
    public static boolean water = false;
    public static boolean loaded = false;
    public static boolean inNether = false;
    public static boolean inEnd = false;
    public static Plugin plugin;
    public static Wild instance;
    public static HashMap<UUID, Integer> cooldownCheck = new HashMap<UUID, Integer>();
    public static int Rem;
    public static Economy econ = null;
    public static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
    public static ArrayList<UUID> cancel = new ArrayList<UUID>();
    public final Logger logger = Bukkit.getServer().getLogger();
    public ArrayList<UUID> portalUsed = new ArrayList<>();
    public HashMap<UUID, Vector> firstCorner = new HashMap<>();
    public HashMap<UUID, Vector> secondCorner = new HashMap<>();
    public HashMap<String, String> portals = new HashMap<>();
    public int cost = this.getConfig().getInt("Cost");
    public String costMSG = this.getConfig().getString("Costmsg");
    public String strCost = String.valueOf(cost);
    public String costMsg = costMSG.replaceAll("\\{cost\\}", strCost);
    public int retries = this.getConfig().getInt("Retries");
    public HashMap<UUID,Biome> biome = new HashMap<>();
    public static Wild getInstance() {
        return instance;
    }

    public static boolean check(Player p) {
        int cool = plugin.getConfig().getInt("Cooldown");
        if (cooldownTime.containsKey(p.getUniqueId())) {
            long old = cooldownTime.get(p.getUniqueId());
            long now = System.currentTimeMillis();
            long diff = now - old;
            long convert = TimeUnit.MILLISECONDS.toSeconds(diff);
            int Rem = cool - (int) convert;
            if (convert >= cool) {
                cooldownTime.put(p.getUniqueId(), now);
                if (cooldownCheck.containsKey(p.getUniqueId()))
                    cooldownCheck.remove(p.getUniqueId());
                return true;
            }
            cooldownCheck.put(p.getUniqueId(), Rem);
            return false;
        } else {
            cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
            if (cooldownCheck.containsKey(p.getUniqueId()))
                cooldownCheck.remove(p.getUniqueId());
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
        List<String> potions = plugin.getConfig().getStringList("Potions");
        int size = potions.size();
        if (size != 0) {
            for (int i = 0; i <= size - 1; i++) {
                String potDur = potions.get(i);
                String[] potionDuration = potDur.split(":");
                String pot = potionDuration[0];
                String dur = potionDuration[1];
                int duration = Integer.parseInt(dur) * 20;
                pot = pot.toUpperCase();
                PotionEffectType Potion = PotionEffectType.getByName(pot);
                p.addPotionEffect(new PotionEffect(Potion, duration, 100));
            }
        }
    }

    public void onDisable() {
        SavePortals save = new SavePortals(this);
        save.saveMap();
        HandlerList.unregisterAll((Plugin) this);
        econ = null;
        plugin = null;
        unRegisterPortalPermissions();
    }

    public void onEnable() {
        this.getCommand("wildtp").setExecutor(new CmdWildTp(this));
        this.getCommand("wild").setExecutor(new CmdWild(this));
        this.getCommand("wild").setTabCompleter(new WildTab());
        this.getCommand("wildtp").setTabCompleter(new WildTpTab());
        plugin = this;
        instance = this;
        registerPortalPerms();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.saveResource("PotionsEffects.txt", true);
        this.saveResource("Biomes.txt", true);
        this.saveResource("Sounds.txt", true);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new InvClick(), this);
        Bukkit.getPluginManager().registerEvents(new SetVal(this), this);
        Bukkit.getPluginManager().registerEvents(new SignChange(this), this);
        Bukkit.getPluginManager().registerEvents(new SignBreak(), this);
        Bukkit.getPluginManager().registerEvents(new SignClick(this), this);
        Bukkit.getPluginManager().registerEvents(new HookClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayMoveEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandUseEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockClickEvent(this), this);
        LoadDependencies.loadAll();
        Initializer intialize = new Initializer(this);
        intialize.initializeAll();
        SavePortals save = new SavePortals(this);
        save.createFile();
        cooldownTime = new HashMap<>();
        Sounds.init();
        CheckConfig check = new CheckConfig();
        Metrics metrics = new Metrics(this);
        if (!check.isCorrectPots()) {
            logger.info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
            logger.info("Plugin will now disable");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
        if (cost > 0) {
            if (!setupEconomy()) {
                Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
                Bukkit.getServer().getPluginManager().disablePlugin(this);
                return;
            }
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

    public Economy getEcon() {
        return econ;
    }

    public void reload(Player p) {
        CheckConfig check = new CheckConfig();
        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
        if (!check.isCorrectPots()) {
            Bukkit.getLogger()
                    .info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
            Bukkit.getLogger().info("Plugin will now disable");
            Bukkit.getPluginManager().disablePlugin(plugin);
        } else {
            p.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN
                    + "WildernessTP" + ChatColor.BLACK + "]" + ChatColor.GREEN
                    + "Plugin config has successfully been reload");
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public List<String> getListPots() {

        return instance.getConfig().getStringList("Potions");
    }

    public void refundPlayer(Player p) {
        if (!p.hasPermission("wild.wildtp.cost.bypass")) {
            econ.depositPlayer(p, cost);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("RefundMsg").replace("{cost}", String.valueOf(cost))));
        }
    }

    public void random(Player target, Location location) {
        GetRandomLocation random = new GetRandomLocation(this);
        String Message = plugin.getConfig().getString("No Suitable Location");
        int x = location.getBlockX();
        int z = location.getBlockZ();
        TeleportTarget tele = new TeleportTarget(this);
        Checks check = new Checks(this);
        if (check.inNether(location, target)) {
            GetHighestNether nether = new GetHighestNether();
            int y = nether.getSolidBlock(x, z, target);

            Location done = new Location(location.getWorld(), x + .5, y, z + .5,
                    0.0F, 0.0F);
            tele.teleport(done, target);
        } else {
            ClaimChecks claims = new ClaimChecks();
            Location loc = new Location(location.getWorld(),
                    location.getBlockX() + .5, location.getBlockY(),
                    location.getBlockZ() + .5, 0.0F, 0.0F);
            if (check.getLiquid(loc) || !check.checkBiome(target,loc.getBlockX(),loc.getBlockZ())|| claims.townyClaim(loc)
                    || claims.factionsClaim(loc) || claims.greifPrevnClaim(loc)
                    || claims.worldGuardClaim(loc) || claims.factionsUUIDClaim(loc)
                    || check.blacklistBiome(loc) || claims.residenceClaimCheck(loc)
                    || claims.landLordClaimCheck(loc)) {

                if (plugin.getConfig().getBoolean("Retry")) {
                    for (int i = retries; i >= 0; i--) {
                        String info = random.getWorldInfomation(target);
                        Location temp = random.getRandomLoc(info, target);
                        Location test = new Location(temp.getWorld(),
                                temp.getBlockX() + .5, temp.getBlockY(),
                                temp.getBlockZ() + .5, 0.0F, 0.0F);
                        if (!check.getLiquid(test) &&
                                check.checkBiome(target,test.getBlockX(),test.getBlockZ())
                                && !claims.townyClaim(test)
                                && !claims.factionsClaim(test)
                                && !claims.greifPrevnClaim(test)
                                && !claims.worldGuardClaim(test)
                                && !claims.kingdomClaimCheck(test)
                                && !claims.factionsUUIDClaim(test)
                                && !check.blacklistBiome(test)
                                && !claims.residenceClaimCheck(test)
                                && !claims.landLordClaimCheck(test)) {
                            biome.remove(target.getUniqueId());
                            tele.teleport(test, target);
                            break;
                        }
                        if (i == 0) {
                            target.sendMessage("1a");
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', Message));
                            cooldownTime.remove(target.getUniqueId());
                            cooldownCheck.remove(target.getUniqueId());
                            refundPlayer(target);
                            target.  sendMessage("2a");
                            return;
                        }
                    }
                } else {
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Message));
                    cooldownTime.remove(target.getUniqueId());
                    cooldownCheck.remove(target.getUniqueId());
                    refundPlayer(target);
                }

            } else {
                target.sendMessage("6a");
                check.isLoaded(location.getChunk().getX(), location.getChunk().getZ(), target);
                Location loco = new Location(location.getWorld(), location.getBlockX() + .5, location.getBlockY(), location.getBlockZ() + .5, 0.0F, 0.0F);
                biome.remove(target.getUniqueId());
                tele.teleport(loco, target);

            }
        }
    }
    private void registerPortalPerms(){
        for(Biome biome : Biome.values())
            Bukkit.getPluginManager().addPermission(new Permission("wild.wildtp.biome."+biome.name().toLowerCase()));
    }
    private void unRegisterPortalPermissions(){
        for(Biome biome : Biome.values())
            Bukkit.getPluginManager().removePermission(new Permission("wild.wildtp.biome."+biome.name().toLowerCase()));

    }

}
