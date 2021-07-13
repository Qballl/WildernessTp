package io.wildernesstp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

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
public final class Language {

    // Constants
    private static final Command COMMAND = new Command();
    private static final Economy ECONOMY = new Economy();
    private static final General GENERAL = new General();
    private static final Teleport TELEPORT = new Teleport();

    private static Language instance;
    private Configuration config;

    protected Language(Configuration config) {
        this.config = config;
    }

    public static Language getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Language is not initialized.");
        }

        return instance;
    }

    protected static void setInstance(Language instance) {
        if (Language.instance != null) {
            throw new IllegalStateException("Language is already initialized.");
        }

        Language.instance = instance;
    }


    public Configuration getConfig(){
        return instance.config;
    }

    protected static void reloadInstance(Language instance){
        Language.instance = instance;
    }

    public Command command() {
        return COMMAND;
    }

    public Economy economy() {
        return ECONOMY;
    }

    public General general() {
        return GENERAL;
    }

    public Teleport teleport() {
        return TELEPORT;
    }

    public static final class Command {

        public String onlyPlayer() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("command.only-player")));
        }

        public String noPermission(String permission) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("command.no-permission"))
                .replace("{permission}", permission));
        }

        public String invalidUsage(String usage) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("command.invalid-usage"))
                .replace("{usage}", usage));
        }
    }

    public static final class Economy {

        public String cost() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("economy.cost")));
        }

        public String insufficientFund() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("economy.insufficient-fund")));
        }

        public String limitReached() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("general.limit_reached")));
        }

    }

    public static final class General {

        public String moved() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("general.movedMsg")));
        }

        public String cooldown() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("general.cooldown")));
        }

        public String limitReached() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("general.limit_reached")));
        }
    }

    public static final class Teleport {

        public String noWorld() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("teleporting.noWorld")));
        }

        public String noLocFound() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("teleporting.noLocFound")));
        }

        public String teleporting() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("teleporting.teleporting")));
        }

        public String warmUp() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(instance.config.getString("teleporting.warmUp","This is default")));
        }
    }
}
