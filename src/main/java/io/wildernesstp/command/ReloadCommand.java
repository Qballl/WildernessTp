package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.region.RegionManager;
import io.wildernesstp.util.TeleportManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends BaseCommand{

    public ReloadCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, permission, onlyPlayer);
    }

    @Override
    protected void execute(CommandSender sender, Command cmd, String[] args) {
        try {
            getPlugin().reloadConfig();
            getPlugin().reloadTranslations();
            getPlugin().setRegionManager(new RegionManager(getPlugin()));
            getPlugin().getCooldownManager().clearCooldDown();
            TeleportManager.clearAll();
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "Config and lang file have been reloaded");
            }
        }catch (Exception e){
            sender.sendMessage(ChatColor.RED+"Plugin failed to reload");
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> suggest(CommandSender sender, Command cmd, String[] args) {
        return null;
    }
}
