package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.portal.Portal;
import io.wildernesstp.portal.PortalEditSession;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CreateCommand extends BaseCommand {

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.create";

    public CreateCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        final Player player = (Player) sender;
        Optional<PortalEditSession> session = getPlugin().getPortalManager().getSession(player);

        if (!session.isPresent()) {
            session = Optional.of(getPlugin().getPortalManager().startSession(player));
        }

        if (!session.get().isPosOneSet() || !session.get().isPosTwoSet()) {
            sender.sendMessage("Portal region is not correctly set.");
            return;
        }

        Portal portal = getPlugin().getPortalManager().createPortal(new Portal(session.get().getPosOne(), session.get().getPosTwo()));
        sender.sendMessage("Portal has been created.");

        if (Arrays.stream(args).anyMatch(s -> s.equalsIgnoreCase("--generate") || s.equalsIgnoreCase("-g"))) {
            portal.generate(player);
            sender.sendMessage("Portal-blocks has been generated as well (Note: This is a beta feature).");
    }

        getPlugin().getPortalManager().endSession(player);
    }
    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}
