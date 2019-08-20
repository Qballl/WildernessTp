package io.wildernesstp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private final File configFile = new File(super.getDataFolder(), "config.yml");
    private FileConfiguration internalConfig, externalConfig;

    @Override
    public void onEnable() {
        if (!configFile.exists()) {
            super.saveDefaultConfig();
        }

        this.loadConfiguration();

        if (externalConfig.getInt("config-version") < internalConfig.getInt("config-version")) {
            super.saveResource(internalConfig.getName(), true);
            super.getLogger().info("Configuration wasn't up-to-date thus we updated it automatically.");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadConfiguration() {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(configFile.getName())))) {
            this.internalConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.externalConfig = super.getConfig();
    }
}
