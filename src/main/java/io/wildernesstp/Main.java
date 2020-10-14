package io.wildernesstp;

import io.papermc.lib.PaperLib;
import io.wildernesstp.command.WildCommand;
import io.wildernesstp.command.WildernessTPCommand;
import io.wildernesstp.generator.LocationGenerator;
import io.wildernesstp.hook.*;
import io.wildernesstp.listener.PlayerListener;
import io.wildernesstp.portal.PortalManager;
import io.wildernesstp.region.RegionManager;
import io.wildernesstp.util.ConfigMigrator;
import io.wildernesstp.util.CooldownManager;
import io.wildernesstp.util.Metrics;
import io.wildernesstp.util.TeleportManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * MIT License
 * <p>
 * Copyright (c) 2019 Quintin VanBooven
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public final class Main extends JavaPlugin {

    private static final int DEFAULT_CONFIG_VERSION = 401 ;
    private static final int DEFAULT_LANG_VERSION = 2 ;
    private static final String DEFAULT_LANGUAGE = "english";

    private final File configFile = new File(super.getDataFolder(), "config.yml");
    private FileConfiguration internalConfig, externalConfig;
    private File langFile;
    private FileConfiguration internalLang, externalLang;

    private Economy econ;
    private Hook[] hooks;

    private Language language;
    private LocationGenerator generator;

    private PortalManager portalManager;
    private RegionManager regionManager;
    private CooldownManager cooldownManager;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Override
    public final void onEnable() {
        if (!configFile.exists()) {
            super.saveDefaultConfig();
        }
        cooldownManager = new CooldownManager(this);

        this.loadConfiguration();
        this.loadTranslations();

        if (externalConfig.getInt("config-version", Main.DEFAULT_CONFIG_VERSION) < internalConfig.getInt("config-version", Main.DEFAULT_CONFIG_VERSION)) {
            updateConfig();
            super.getLogger().info("Configuration wasn't up-to-date thus we updated it automatically.");
        }

        langFile = new File(super.getDataFolder()+File.separator+"lang"+File.separator,getConfig().getString("language","english")+".yml");
        this.loadLang();

        if (externalLang.getInt("lang_ver", Main.DEFAULT_LANG_VERSION) < internalLang.getInt("lang_ver", Main.DEFAULT_LANG_VERSION)) {
            updateLang();
            super.getLogger().info("Language file wasn't up-to-date thus we updated it automatically.");
        }

        this.registerHooks();
        this.registerCommands();
        this.registerListeners();


        this.portalManager = new PortalManager(this);
        this.regionManager = new RegionManager(this);

        PaperLib.suggestPaper(this);

        if (getConfig().getBoolean("use_hooks")) {
            List<Hook> couldHook  = new ArrayList<>();
            Arrays.stream(hooks).filter(Hook::canHook).forEach(h -> {
                h.enable();
                couldHook.add(h);

                message:
                {
                    final StringBuilder sb = new StringBuilder("Hooked into " + h.getName() + " ");

                    if (!h.getVersion().isEmpty()) {
                        sb.append("v").append(h.getVersion());
                    }

                    if (h.getAuthors().length > 0) {
                        sb.append("by").append(" ").append(String.join(", ", h.getAuthors()));
                    }

                    Bukkit.getLogger().info(sb.toString().trim());
                }
            });
            hooks = couldHook.toArray(new Hook[couldHook.size()]);
        }

        this.setupGenerator();

        if(getConfig().getInt("config-version",0)<400)
            ConfigMigrator.migrate(this);
        this.reloadConfig();
        if(getConfig().getInt("cost")>0) {
            if (!setupEconomy()) {
                getLogger().severe("Disabled due to no Vault dependency or economy plugin found!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }


        new Metrics(this);
    }

    @Override
    public final void onDisable() {
        getPortalManager().saveCache();
        Arrays.stream(hooks).filter(Hook::canHook).collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator().forEachRemaining(Hook::disable);
    }

    private void updateConfig(){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        for(String key : internalConfig.getConfigurationSection("").getKeys(true)){
            if(!config.contains(key))
                externalConfig.set(key,internalConfig.get(key));
        }
        try{
            externalConfig.save(configFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        externalConfig.set("config-version",internalConfig.getInt("config-version"));
        try {
            externalConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLang(){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(langFile);
        for(String key : internalLang.getConfigurationSection("").getKeys(true)){
            if(!config.contains(key))
                externalLang.set(key,internalLang.get(key));
        }
        try{
            externalLang.save(langFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        externalLang.set("ver",internalLang.getInt("ver"));
        try {
            externalLang.save(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Language getLanguage() {
        return language;
    }

    public LocationGenerator getGenerator() {
        return generator;
    }

    public PortalManager getPortalManager() {
        return portalManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public Economy getEcon() {
        return econ;
    }

    public CooldownManager getCooldownManager(){
        return cooldownManager;
    }

    public List<Biome> getBlacklistedBiomes() {
        return externalConfig.getStringList("blacklisted-biomes").stream().map(String::toUpperCase).map(Biome::valueOf).collect(Collectors.toList());
    }

    public void teleport(Player player, Set<Predicate<Location>> filters) {
        generator.generate(player, filters).ifPresent(l -> PaperLib.teleportAsync(player, l));
        takeMoney(player);
    }

    public void generate(Player player){
        generator.generate(player, Collections.emptySet()).ifPresent(l -> PaperLib.teleportAsync(player, l));
    }

    /*public Optional<Location> generate(Player player){
        return generator.generate(player);
    }*/

    public void teleport(Player player) {
        Set<Predicate<Location>> filters = new HashSet<>();
        teleport(player, filters);
    }

    private void loadConfiguration() {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(configFile.getName())))) {
            this.internalConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.externalConfig = super.getConfig();
    }

    private void loadLang() {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(langFile.getPath())))) {
            this.internalLang = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.externalLang = YamlConfiguration.loadConfiguration(langFile);
    }

    public FileConfiguration getInternalConfig(){
        return internalConfig;
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

    private void loadTranslations() {
        final String language = externalConfig.getString("language", Main.DEFAULT_LANGUAGE);
        final File languageFile = new File(new File(super.getDataFolder(), "lang"), language + ".yml");
        boolean usingDefaults = false;

        if (!languageFile.exists()) {
            try {
                super.saveResource("lang/" + language + ".yml", false);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("Could not find language file. Falling back on default translations.");
                usingDefaults = true;
            }
        }

        this.language = (!usingDefaults ? new Language(YamlConfiguration.loadConfiguration(languageFile)) : new Language());

    }

    private void registerHooks() {
        final List<Hook> hooks = new ArrayList<>();
        hooks.add(new TownyHook(this));
        hooks.add(new FabledKingdomsHook(this));
        hooks.add(new MassiveFactionsHook(this));
        hooks.add(new FeudalHook(this));
        hooks.add(new ResidenceHook(this));
        hooks.add(new FactionsUUIDHook(this));
        hooks.add(new GriefPreventionHook(this));
        hooks.add(new WorldGuardHook(this));

        this.hooks = hooks.toArray(new Hook[hooks.size()]);
    }

    private void registerCommands() {
        wildernesstp:
        {
            PluginCommand pluginCommand = Objects.requireNonNull(super.getCommand("wildernesstp"));
            WildernessTPCommand command = new WildernessTPCommand(this, pluginCommand.getName(), pluginCommand.getDescription(), pluginCommand.getUsage(), pluginCommand.getAliases(), pluginCommand.getPermission(), true);
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);

        }

        wild:
        {
            PluginCommand pluginCommand = super.getCommand("wild");
            WildCommand command = new WildCommand(this, pluginCommand.getName(), pluginCommand.getDescription(), pluginCommand.getUsage(), pluginCommand.getAliases(), pluginCommand.getPermission(), false);
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public void takeMoney(Player player) {
        if (getConfig().getInt("cost") > 0) {
            if (!player.hasPermission("wildernesstp.bypass.cost")) {
                if ((econ.getBalance(player) - getConfig().getInt("cost")) >= 0) {
                    econ.withdrawPlayer(player, getConfig().getInt("cost"));
                }
                TeleportManager.noMoney(player.getUniqueId());
                player.sendMessage(getLanguage().economy().insufficientFund());
            }
        }
    }

    private void setupGenerator() {
        generator = new LocationGenerator(this);
        generator.addFilter(l -> !l.getBlock().isLiquid());
        generator.addFilter(l -> {
            Location temp = l;
            temp.setY(l.getY()+2);
            return temp.getBlock().isEmpty();
        });
        generator.addFilter(l -> !l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()-2,l.getBlockZ()).isLiquid());
        generator.addFilter(l -> l.getBlockY() != -1);

        if(getConfig().getBoolean("use_hooks")) {
            for (Hook h : hooks) {

                if (h.canHook()) {
                    getLogger().info("Generator makes use of hook: " + h.getName());
                    getLogger().info("WE HOOKED WITH " + h.getName());
                    generator.addFilter(l -> !h.isClaim(l));
                }
            }
        }
        getBlacklistedBiomes().forEach(b -> generator.addFilter(l -> l.getBlock().getBiome() != b));
    }

    public static int parseMcVer(String ver) {
        return Integer.parseInt(ver.split("\\.")[1].replaceAll("[^0-9]",""));
    }

}
