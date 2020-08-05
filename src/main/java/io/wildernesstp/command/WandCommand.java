package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.util.WTPConstants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collections;
import java.util.List;

public final class WandCommand extends BaseCommand {

    public WandCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, permission, onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        final Player player = (Player) sender;
        final PlayerInventory inv = player.getInventory();

        if (inv.firstEmpty() == -1) {
            sender.sendMessage("Inventory is full.");
            return;
        }

        inv.addItem(WTPConstants.WAND);
        sender.sendMessage("You have gotten a Portal Wand.");
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return Collections.emptyList();
    }
}