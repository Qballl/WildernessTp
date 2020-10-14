package io.wildernesstp;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;

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

    private final Command COMMAND = new Command();
    private final Economy ECONOMY = new Economy();
    private final General GENERAL = new General();
    private final Teleporting teleporting = new Teleporting();

    private Configuration config;

    protected Language(Configuration config) {
        this.config = config;
    }

    protected Language() {
        this(new MemoryConfiguration());
    }

    public Command command() {
        return COMMAND;
    }

    public Economy economy() {
        return ECONOMY;
    }

    public General general() {
        return  GENERAL;
    }

    public Teleporting teleporting(){
        return  teleporting;
    }

    public final class Command {
        public String onlyPlayer() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("command.only-player")));
        }

        public String noPermission(String permission) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("command.no-permission"))
                .replace("{permission}", permission));
        }

        public String invalidUsage(String usage) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("command.invalid-usage"))
                .replace("{usage}", usage));
        }

    }

    public final class Economy {

        public String cost() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("economy.cost")));
        }

        public String insufficientFund() {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("economy.insufficient-fund")));
        }

        public String limitReached(){
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("general.limit_reached")));
        }

    }

    public final class General{

        public String moved(){
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("general.movedMsg")));
        }

        public String cooldown(){
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("general.cooldown")));
        }

        public String limitReached(){
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("general.limit_reached")));
        }


    }

    public final class Teleporting{

        public String noWorld(){
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("teleporting.noWorld")));
        }

        public String noLocFound(){
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("teleporting.noLocFound")));
        }

        public String teleporting(){
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("teleporting.teleporting")));
        }

        public String warmUp(){
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("teleporting.warmUp")));
        }
    }
}
