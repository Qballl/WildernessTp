package io.wildernesstp.command;

import io.wildernesstp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

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
public abstract class BaseCommand extends BukkitCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;
    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;
    private final String permission;
    private final boolean onlyPlayer;

    public BaseCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(name, description, usage, aliases);

        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
    }

    public Main getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    protected String getHelpMessage(CommandSender sender) {
        if (onlyPlayer && !(sender instanceof Player)) {
            return "";
        }

        final StringBuilder sb = new StringBuilder();

        sb.append(permission != null && sender.hasPermission(permission) ? ChatColor.GREEN : ChatColor.RED);
        sb.append(name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(Character.toUpperCase(name.charAt(0)))));

        if (aliases != null && aliases.size() > 0) {
            sb.append(" (");
            sb.append(String.join(", ", aliases));
            sb.append(")");
        }

        if (description != null && !description.isEmpty()) {
            sb.append(": ");
            sb.append(description);
        }

        return sb.toString();
    }

    protected abstract void execute(CommandSender sender, Command cmd, String[] args);

    protected abstract List<String> suggest(CommandSender sender, Command cmd, String[] args);

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
        throw new UnsupportedOperationException("Do we really need this?");
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command  cmd, String s, String[] args) {
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(plugin.getLanguage().command().noPermission(String.join(", ", permission.split(";"))));
            return true;
        }

        if (onlyPlayer && !(sender instanceof Player)) {
            sender.sendMessage(plugin.getLanguage().command().onlyPlayer());
            return true;
        }

        this.execute(sender, cmd, args);
        return true;
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        return this.suggest(sender, cmd, args);
    }
}
