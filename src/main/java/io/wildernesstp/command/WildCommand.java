package io.wildernesstp.command;

import io.wildernesstp.Main;
import io.wildernesstp.generator.LocationGenerator;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public final class WildCommand extends BaseCommand {

    private static final String COMMAND_PERMISSION = "wildernesstp.command.wild";
    private static final String BIOME_PERMISSION = "wildernesstp.biome.%s";

    public WildCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(COMMAND_PERMISSION)) {
            sender.sendMessage("No permission.");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Not a player.");
            return true;
        }

        final Player player = (Player) sender;
        final LocationGenerator generator = new LocationGenerator(player.getWorld())
            .filter(l -> !l.getBlock().isLiquid());
        Location loc;

        if (args.length > 0) {
            final Biome biome = Biome.valueOf(args[0].toUpperCase());
            loc = generator.filter(l -> l.getBlock().getBiome() == biome).generate();
        } else {
            loc = generator.generate();
        }

        sender.sendMessage("Teleporting...");
        player.teleport(loc, TeleportCause.PLUGIN);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return Arrays.stream(Biome.values()).filter(b -> sender.hasPermission(String.format(BIOME_PERMISSION, b.name().toLowerCase()))).map(Biome::name).collect(Collectors.toList());
        } else if (args.length == 1) {
            return Arrays.stream(Biome.values()).filter(b -> b.name().toLowerCase().startsWith(args[0].toLowerCase()) && sender.hasPermission(String.format(BIOME_PERMISSION, b.name().toLowerCase()))).map(Biome::name).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
