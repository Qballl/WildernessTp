package io.wildernesstp;

import io.papermc.lib.PaperLib;
import io.wildernesstp.command.GetWorldCommand;
import io.wildernesstp.command.WildCommand;
import io.wildernesstp.command.WildernessTPCommand;
import io.wildernesstp.generator.LocationGenerator;
import io.wildernesstp.gui.GUIHandler;
import io.wildernesstp.gui.WorldGUI;
import io.wildernesstp.hook.*;
import io.wildernesstp.listener.InventoryListener;
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
import org.bukkit.World;
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

    private static final int DEFAULT_CONFIG_VERSION = 403;
    private static final int DEFAULT_LANG_VERSION = 2;
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
            List<Hook> couldHook = new ArrayList<>();
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

        if (getConfig().getInt("config-version", 0) < 400)
            ConfigMigrator.migrate(this);
        this.reloadConfig();
        if (getConfig().getInt("cost") > 0) {
            if (!setupEconomy()) {
                getLogger().severe("Disabled due to no Vault dependency or economy plugin found!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }

        super.getServer().getPluginManager().registerEvents(new GUIHandler(this, new WorldGUI(this)), this);

        new Metrics(this);

    }

    public void setRegionManager(RegionManager regionManager){
        this.regionManager = regionManager;
    }

    @Override
    public final void onDisable() {
        getPortalManager().saveCache();
        Arrays.stream(hooks).filter(Hook::canHook).collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator().forEachRemaining(Hook::disable);
    }

    private void updateConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        for (String key : internalConfig.getKeys(true)) {
            if (!config.contains(key))
                externalConfig.set(key, internalConfig.get(key));
        }
        if(externalConfig.getInt("config-version")< 402)
            convertLimits();
        externalConfig.set("config-version", internalConfig.getInt("config-version"));
        try {
            externalConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLang() {
        for (String key : internalLang.getKeys(true)) {
            if (!externalLang.contains(key)) {
                externalLang.set(key, internalLang.get(key));
            }
        }

        langFile = new File(this.getDataFolder()+"/lang/"+externalConfig.getString("language", Main.DEFAULT_LANGUAGE)+".yml");
        externalLang.set("language-version", internalLang.getInt("language-version"));
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

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public List<Biome> getBlacklistedBiomes() {
        return externalConfig.getStringList("blacklisted-biomes").stream().map(String::toUpperCase).map(Biome::valueOf).collect(Collectors.toList());
    }

    public void teleport(Player player, Set<Predicate<Location>> filters) {
        teleport(player, player.getWorld(), filters);
    }

    public void teleport(Player player, World world, Set<Predicate<Location>> filters) {
        generator.generate(player, world, filters).ifPresent(l -> PaperLib.teleportAsync(player, l));
        runCommands(player);
        takeMoney(player);
    }

    public void generate(Player player, World world) {
        generator.generate(player, world, new HashSet<>()).ifPresent(l -> PaperLib.teleportAsync(player, l));
    }

    public void generate(Player player, World world,Set<Predicate<Location>> filters) {
        Optional<Location> location = getGenerator().generate(player,world,filters);
        if(location.isPresent()) {
            PaperLib.teleportAsync(player, location.get());
            runCommands(player);
        }
    }

    public void generate(Player player) {
        generate(player, player.getWorld());
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

    public FileConfiguration getInternalConfig() {
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

        this.externalLang = YamlConfiguration.loadConfiguration(languageFile);
        Language.setInstance(new Language(externalLang));
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("lang/"+language+".yml")))) {
            this.internalLang = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.language = (!usingDefaults ? new Language(YamlConfiguration.loadConfiguration(languageFile)) : new Language(internalConfig));
    }

    public void reloadTranslations() {
        final String language = externalConfig.getString("language", Main.DEFAULT_LANGUAGE);
        final File languageFile = new File(new File(super.getDataFolder(), "lang"), language + ".yml");

        boolean usingDefaults = false;



        this.externalLang = YamlConfiguration.loadConfiguration(languageFile);
        Language.reloadInstance(new Language(externalLang));
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("lang/"+language+".yml")))) {
            this.internalLang = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.language = (!usingDefaults ? new Language(YamlConfiguration.loadConfiguration(languageFile)) : new Language(internalConfig));
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
        hooks.add(new LandsHook(this));

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

        getLocation:
        {
            PluginCommand pluginCommand = super.getCommand("getWorld");
            GetWorldCommand command = new GetWorldCommand(this,pluginCommand.getName(), pluginCommand.getDescription(), pluginCommand.getUsage(),pluginCommand.getAliases(),pluginCommand.getPermission(),true);
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this),this);
    }

    public void takeMoney(Player player) {
        if (getConfig().getInt("cost") > 0) {
            double cost = getConfig().getDouble("cost");
            if (!player.hasPermission("wildernesstp.bypass.cost")) {
                if ((econ.getBalance(player) - cost) >= 0) {
                    econ.withdrawPlayer(player, cost);
                    player.sendMessage(getLanguage().economy().cost().replace("{cost}",String.valueOf(cost)));
                }else {
                    TeleportManager.noMoney(player.getUniqueId());
                    player.sendMessage(getLanguage().economy().insufficientFund());
                }
            }
        }
    }

    private void setupGenerator() {
       generator = new LocationGenerator(this);
       generator.addFilter(l -> !l.getBlock().isLiquid());
       /*generator.addFilter(l -> {
            Location temp = l;
            temp.setY(l.getY() + 2);
            return temp.getBlock().isEmpty();
        });*/
        generator.addFilter(l -> l.getBlockY() != -1);

        if (getConfig().getBoolean("use_hooks")) {
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

    public static int getServerVer() {
        String[] tmp = Bukkit.getServer().getVersion().split("MC: ");
        String ver = tmp[tmp.length - 1].substring(0, 4);
        return Integer.parseInt(ver.split("\\.")[1].replaceAll("[^0-9]", ""));
    }

    private void convertLimits(){
        HashMap<String, Integer> limits = new HashMap<>();
        externalConfig.getConfigurationSection("Limits").getKeys(false).forEach(k ->{
            limits.put(k,externalConfig.getInt("Limits."+k));
            externalConfig.set("Limits."+k,null);
           });
        externalConfig.set("Limits",null);
        limits.keySet().forEach(k -> externalConfig.set("use_limit."+k,limits.get(k)));
    }

    public void runCommands(Player p){
        List<String> commands = getConfig().getStringList("commands_on_wild");
        for(String s : commands){
            s = s.replace("%player%", p.getName());
            Bukkit.dispatchCommand(getServer().getConsoleSender(),s);
        }
    }

}
