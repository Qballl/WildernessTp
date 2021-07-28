package io.wildernesstp.command;

import io.wildernesstp.Main;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GetWorldCommand extends BaseCommand{

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.setup;wildernesstp.*";

    public GetWorldCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer){
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player)sender;
        sender.sendMessage(ChatColor.GOLD+"Current World: "+player.getLocation().getWorld().getName());
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return null;
    }
}
