package io.wildernesstp.command;

import io.wildernesstp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public final class ListCommand extends BaseCommand {

    public ListCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, permission, onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        sender.sendMessage("Not yet implemented.");
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}