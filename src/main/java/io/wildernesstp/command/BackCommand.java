package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.util.TeleportManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BackCommand extends BaseCommand{

    public BackCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, permission, onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        p.teleport(TeleportManager.getBack(p.getUniqueId()));
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return null;
    }
}
