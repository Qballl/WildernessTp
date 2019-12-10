package io.wildernesstp;

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

<<<<<<< HEAD
    public final class Command {
=======
    public Economy economy(){
        return new Economy();
    }

    public static final class Command {
>>>>>>> 1badf1abff93da5e307d54587ae17c9d1e80b0d5

        public String onlyPlayer() {
            return ChatColor.translateAlternateColorCodes('&',config.getString("command.only-player"));
        }

        public String noPermission(String permission) {
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("command.no-permission"))
                .replace("{permission}", permission));
        }

        public String invalidUsage(String usage) {
            return ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(config.getString("command.invalid-usage"))
                .replace("{usage}", usage));
        }
    }

    public static final class Economy {
        public String costMessage(){
            return ChatColor.translateAlternateColorCodes('&',config.getString("economy.cost-message"));
        }

        public String noMoney(){
            return ChatColor.translateAlternateColorCodes('&',config.getString("economy.insufficent-fund"));
        }
    }

    public final class Economy {

        public String cost() {
            return Objects.requireNonNull(config.getString("economy.cost-message"));
        }
    }
}
