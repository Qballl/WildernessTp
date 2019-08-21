package io.wildernesstp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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

    private static final int DEFAULT_CONFIG_VERSION = 1;
    private static final String DEFAULT_LANGUAGE = "english";

    private final File configFile = new File(super.getDataFolder(), "config.yml");
    private FileConfiguration internalConfig, externalConfig;

    private Language language;

    @Override
    public void onEnable() {
        if (!configFile.exists()) {
            super.saveDefaultConfig();
        }

        this.loadConfiguration();
        this.loadTranslations();

        if (externalConfig.getInt("config-version", Main.DEFAULT_CONFIG_VERSION) < internalConfig.getInt("config-version", Main.DEFAULT_CONFIG_VERSION)) {
            super.saveResource(internalConfig.getName(), true);
            super.getLogger().info("Configuration wasn't up-to-date thus we updated it automatically.");
        }
    }

    @Override
    public void onDisable() { }

    private void loadConfiguration() {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(configFile.getName())))) {
            this.internalConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.externalConfig = super.getConfig();
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
}
