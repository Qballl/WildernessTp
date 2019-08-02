package me.Qball.Wild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


import io.papermc.lib.PaperLib;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.inventivetalent.update.spiget.SpigetUpdate;
import org.inventivetalent.update.spiget.UpdateCallback;
import org.inventivetalent.update.spiget.comparator.VersionComparator;


public class Wild extends JavaPlugin{
    public static HashMap<UUID, Long> cooldownTime;
    public static Wild instance;
    public static HashMap<UUID, Integer> cooldownCheck = new HashMap<>();
    public static Economy econ = null;
    public static ArrayList<UUID> CmdUsed = new ArrayList<UUID>();
    public static ArrayList<UUID> cancel = new ArrayList<UUID>();
    public ArrayList<UUID> portalUsed = new ArrayList<>();
    public HashMap<UUID, Vector> firstCorner = new HashMap<>();
    public HashMap<UUID, Vector> secondCorner = new HashMap<>();
    public HashMap<String, String> portals = new HashMap<>();
    public int retries = this.getConfig().getInt("Retries");
    public HashMap<UUID,Biome> biome = new HashMap<>();
    public ArrayList<UUID> village = new ArrayList<>();
    public boolean thirteen = false;
    public UsageMode usageMode;
    public static Wild getInstance() {
        return instance;
    }
    private final String prefix = "(WildTP) ";

    public void onEnable() {
        Console.send("&7-----------------------------------------------");
        String[] tmp = Bukkit.getVersion().split("MC: ");
        String version = tmp[tmp.length - 1].substring(0, 4);
        int ver = parseMcVer(version);

        thirteen = ver>=13;
        instance = this;
        this.getCommand("wildtp").setExecutor(new CmdWildTp(this));
        this.getCommand("wild").setExecutor(new CmdWild(this));
        this.getCommand("wild").setTabCompleter(new WildTab());
        this.getCommand("wildtp").setTabCompleter(new WildTpTab());
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.saveResource("PotionsEffects.txt", true);
        this.saveResource("Biomes.txt", true);
        this.saveResource("Sounds.txt", true);
        this.saveResource("Particles.txt", true);
        Bukkit.getPluginManager().registerEvents(new InvClick(this), this);
        Bukkit.getPluginManager().registerEvents(new SetVal(this), this);
        Bukkit.getPluginManager().registerEvents(new SignChange(this), this);
        Bukkit.getPluginManager().registerEvents(new SignBreak(this), this);
        Bukkit.getPluginManager().registerEvents(new SignClick(this), this);
        Bukkit.getPluginManager().registerEvents(new HookClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayMoveEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandUseEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockClickEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PortalEvent(this),this);
        LoadDependencies.loadAll();
        Initializer initialize = new Initializer(this);
        initialize.initializeAll();
        SavePortals save = new SavePortals(this);
        save.createFile();
        LocationsFile locationsFile = new LocationsFile(this);
        locationsFile.createFile();
        cooldownTime = new HashMap<>();
        Sounds.init();
        CheckConfig check = new CheckConfig();
        if (this.getConfig().getBoolean("Metrics"))
            new Metrics(this);
        if (!check.isCorrectPots()) {
            Console.send(prefix + "Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
            Console.send(prefix +"&cPlugin will now disable");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if(!check.checkParticle()){
            Console.send(prefix +"Particle type is invalid disabling particles to stop errors");
            this.getConfig().set("DoParticles",false);
        }
        if (this.getConfig().getInt("Cost") > 0) {
            if (!setupEconomy()) {
                this.getLogger().severe("[%s] - Disabled due to no Vault dependency found!");
                Bukkit.getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        if(PaperLib.isPaper()){
            Console.send(prefix + "&bPaper was found ! Async Teleport &aenabled&b.");
        }
        OldFormatConverter.convert();
        checkUpdate();
        getUpdates();
        Console.send("&7-----------------------------------------------");
    }

    private void getUpdates(){
        Console.send(prefix +"Changes from version 3.11.0 to 3.12.0-FIX include: +\n" +
                "* Fixed some backend issues +\n" +
                "* Async teleport for Paper users +\n" +
                "* Reworked gui +\n" +
                "* Fixed NPE from refundPlayer");
    }

    private void checkUpdate(){
        SpigetUpdate updater = new SpigetUpdate(this,18431);
        updater.setVersionComparator(VersionComparator.SEM_VER);
        updater.checkForUpdate(new UpdateCallback() {
            @Override
            public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
                if(instance.getConfig().getBoolean("AutoUpdate")) {
                    if (hasDirectDownload) {
                        if (updater.downloadUpdate()) {
                            getLogger().info("New version of the plugin downloaded and will be loaded on restart");
                        } else {
                            getLogger().warning("Update download failed, reason is " + updater.getFailReason());
                        }
                    }
                }else{
                    getLogger().info("There is an update available please go download it");
                }
            }

            @Override
            public void upToDate() {
                getLogger().info("You are using the latest version thanks");
            }
        });
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

    public List<Biome> getBlacklistedBiomes() {
        // Temporary fix. -- Should be enough though.
        return super.getConfig().getStringList("Blacklisted_Biomes").stream()
                .map(String::toUpperCase)
                .map(Biome::valueOf)
                .collect(Collectors.toList());
    }

    public static boolean check(Player p) {
        int cool = instance.getConfig().getInt("Cooldown");
        if (cooldownTime.containsKey(p.getUniqueId())) {
            long old = cooldownTime.get(p.getUniqueId());
            long now = System.currentTimeMillis();
            long diff = now - old;
            long convert = TimeUnit.MILLISECONDS.toSeconds(diff);
            int rem = cool - (int) convert;
            if (convert >= cool) {
                cooldownTime.put(p.getUniqueId(), now);
                cooldownCheck.remove(p.getUniqueId());
                return true;
            }
            cooldownCheck.put(p.getUniqueId(), rem);
            return false;
        } else {
            cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
            cooldownCheck.remove(p.getUniqueId());
            return true;
        }
    }

    public static String getRem(Player p) {
        final StringBuilder sb = new StringBuilder();
        final int rem = cooldownCheck.getOrDefault(p.getUniqueId(), 0);

        if (rem > 0) {
            final String[] info = instance.timeFormat(rem).split(":");
            final int days = Integer.parseInt(info[0]);
            final int hours = Integer.parseInt(info[1]);
            final int minutes = Integer.parseInt(info[2]);
            final int seconds = Integer.parseInt(info[3]);

            if (days > 0) {
                sb.append(days).append(" ").append((days > 1 ? "days" : "day")).append(", ");
            } else if (hours > 0) {
                sb.append(hours).append(" ").append((hours > 1 ? "hours" : "hour")).append(", ");
            } else if (minutes > 0) {
                sb.append(minutes).append(" ").append((minutes > 1 ? "minutes" : "minute")).append(", ");
            } else if (seconds > 0) {
                sb.append(seconds).append(" ").append((seconds > 1 ? "seconds" : "second")).append(", ");
            }
        }

        String result = sb.toString();
        result = result.trim();
        result = result.substring(0, result.lastIndexOf(',') - 1);

        return result;

    }

    public static void applyPotions(Player p) {
        List<String> potions = instance.getConfig().getStringList("Potions");
        int size = potions.size();
        if (size != 0) {
            for (int i = 0; i <= size - 1; i++) {
                String potDur = potions.get(i);
                String[] potionDuration = potDur.split(":");
                String pot = potionDuration[0];
                String dur = potionDuration[1];
                int duration = Integer.parseInt(dur) * 20;
                pot = pot.toUpperCase();
                PotionEffectType potion = PotionEffectType.getByName(pot);
                p.addPotionEffect(new PotionEffect(potion, duration, 100));
            }
        }
    }

    public void onDisable() {
        SavePortals save = new SavePortals(this);
        save.saveMap();
        HandlerList.unregisterAll((Plugin) this);
        econ = null;
    }


    public Economy getEcon() {
        return econ;
    }

    public void reload(Player p) {
        CheckConfig check = new CheckConfig();
        Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
        if (!check.isCorrectPots()) {
            this.getLogger()
                    .info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly");
            this.getLogger().info("Plugin will now disable");
            Bukkit.getPluginManager().disablePlugin(this);
        } else if(!check.checkParticle()){
            this.getLogger().info("Particle type is invalid disabling particles to stop errors");
            this.getConfig().set("DoParticles",false);
        }else {
            p.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN
                    + "WildernessTP" + ChatColor.BLACK + "]" + ChatColor.GREEN
                    + "Plugin config has successfully been reload");
        }
    }

    public List<String> getListPots() {
        return instance.getConfig().getStringList("Potions");
    }

    private void refundPlayer(Player p) {
        if ((!p.hasPermission("wild.wildtp.cost.bypass"))&&this.getConfig().getInt("Cost")>0) {
            econ.depositPlayer(p, this.getConfig().getInt("Cost"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("RefundMsg").replace("{cost}", String.valueOf(this.getConfig().getInt("Cost")))));
        }
    }

    public void random(Player target, Location location) {
        String noSuitableLocation = this.getConfig().getString("No Suitable Location");
        if(location.getY()<=10 && location.getY()>=250){
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', noSuitableLocation));
            refundPlayer(target);
            cooldownCheck.remove(target.getUniqueId());
            cooldownTime.remove(target.getUniqueId());
            return;
        }
        GetRandomLocation random = new GetRandomLocation(this);
        int x = location.getBlockX();
        int z = location.getBlockZ();
        TeleportTarget tele = new TeleportTarget(this);
        Checks check = new Checks(this);
        if (check.inNether(location, target)) {
            double y = check.getSolidBlock(x,z,target);
            if(y > 10) {
                tele.teleport(location, target);
            }
        } else{
            ClaimChecks claims = new ClaimChecks();
            //target.sendMessage("0 From Wild.random the value of y is "+location.getY());
            if (check.getLiquid(location) || !check.checkBiome(location,target)
                    || claims.checkForClaims(location) || !check.isVillage(location,target)
                    || check.checkLocation(location,target)) {
                if (this.getConfig().getBoolean("Retry")) {
                    for (int i = retries; i >= 0; i--) {
                        String info = random.getWorldInformation(location);
                        location = random.getRandomLoc(info, target);
                        if (!check.getLiquid(location) &&
                                check.checkBiome(location,target)
                                && !claims.checkForClaims(location)
                                && location.getBlockY() >5 &&
                                check.isVillage(location,target) && !check.checkLocation(location,target)) {
                            biome.remove(target.getUniqueId());
                            tele.teleport(location, target);
                            return;
                        }
                    }
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', noSuitableLocation));
                    cooldownTime.remove(target.getUniqueId());
                    cooldownCheck.remove(target.getUniqueId());
                    refundPlayer(target);
                    biome.remove(target.getUniqueId());
                    village.remove(target.getUniqueId());
                } else {
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', noSuitableLocation));
                    cooldownTime.remove(target.getUniqueId());
                    cooldownCheck.remove(target.getUniqueId());
                    refundPlayer(target);
                    biome.remove(target.getUniqueId());
                    village.remove(target.getUniqueId());
                }
            } else {
                //target.sendMessage("0.5 From Wild.random the value of y is "+location.getY());
                check.isLoaded(location.getChunk().getX(), location.getChunk().getZ(), target);
                biome.remove(target.getUniqueId());
                village.remove(target.getUniqueId());
                //target.sendMessage("3 From Wild.random the value of y is "+location.getY());
                tele.teleport(location, target);
            }
        }
    }

    private String timeFormat(int rem){
        int days = rem / 86400;
        int hours = (rem % 86400 ) / 3600 ;
        int minutes = ((rem % 86400 ) % 3600 ) / 60;
        int seconds = ((rem % 86400 ) % 3600 ) % 60  ;
        return days+":"+hours+":"+minutes+":"+seconds;
    }

    public static int parseMcVer(String ver){
        return Integer.parseInt(ver.split("\\.")[1]);
    }

}
