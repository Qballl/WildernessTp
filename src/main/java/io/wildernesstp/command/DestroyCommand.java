package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.portal.Portal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class DestroyCommand extends BaseCommand {

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.destroy";

    public DestroyCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        final Player player = (Player) sender;
        Optional<Portal> optionalPortal;

        if (args.length == 0) {
            optionalPortal = getPlugin().getPortalManager().getNearbyPortal(player, 5);
        } else {
            optionalPortal = getPlugin().getPortalManager().getPortal(Integer.parseInt(args[0]));
        }

        if (!optionalPortal.isPresent()) {
            sender.sendMessage("Portal not found.");
            return;
        }

        optionalPortal.get().degenerate(player);
        getPlugin().getPortalManager().destroyPortal(optionalPortal.get());
        sender.sendMessage("Portal destroyed.");
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}