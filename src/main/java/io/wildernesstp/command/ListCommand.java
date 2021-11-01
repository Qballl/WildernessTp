package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.portal.Portal;
import io.wildernesstp.portal.PortalManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ListCommand extends BaseCommand {

    public ListCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, permission, onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        getPlugin().getPortalManager().getCache().keySet().forEach(k -> sender.sendMessage(ChatColor.BLUE+""+getPlugin().getPortalManager().getCache().get(k)));
        getPlugin().getPortalManager().getPortals().forEach(p -> sender.sendMessage(ChatColor.GOLD+""+p));
        sender.sendMessage("Not yet implemented.");
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}