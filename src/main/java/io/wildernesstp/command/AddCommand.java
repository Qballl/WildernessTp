package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class AddCommand extends BaseCommand {

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.add";

    public AddCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        Optional<Region> optionalRegion;

        if (args.length > 0) {
            if (args[0].matches("[a-zA-z][a-zA-Z0-9]+")) {
                optionalRegion = super.getPlugin().getRegionManager().getRegion(Bukkit.getWorld(args[0]));
            } else {
                optionalRegion = super.getPlugin().getRegionManager().getRegion(Integer.parseInt(args[0]));
            }

            if (optionalRegion.isPresent()) {
                // TODO: Send message 'Region already exists'.
            } else {
                // TODO: Create region with specified parameters.
            }
        } else {
            sender.sendMessage(super.getPlugin().getLanguage().command().invalidUsage(super.getUsage()));
        }
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}
