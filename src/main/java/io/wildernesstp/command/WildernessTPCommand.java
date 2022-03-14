package io.wildernesstp.command;

import io.wildernesstp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
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
public final class WildernessTPCommand extends BaseCommand {

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.{sub};wildernesstp.*";
    private final Set<BaseCommand> subCommands = new LinkedHashSet<>();

    public WildernessTPCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);

        subCommands.add(new CreateCommand(plugin, "create", "Create a portal.", null, Collections.singletonList("cp"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "create"), true));
        subCommands.add(new DestroyCommand(plugin, "destroy", "Destroy a portal.", null, Collections.singletonList("dp"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "destroy"), true));
        subCommands.add(new WandCommand(plugin, "wand", "Get a Portal Wand.", null, Collections.singletonList("w"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "create"), true));
        subCommands.add(new ListCommand(plugin, "list", "List all portals.", null, Collections.singletonList("l"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "list"), false));
        subCommands.add(new GUICommand(plugin, "gui", "Opens the biome selection panel.", null, Collections.singletonList("g"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "gui"), true));
        subCommands.add(new SetupCommand(plugin, "setup", "Setup a region.", null, Collections.singletonList("s"), DEFAULT_COMMAND_PERMISSION.replace("{sub}", "setup"), true));
        subCommands.add(new ReloadCommand(plugin,"reload","Reload the config and lang file",null,Collections.singletonList("r"),DEFAULT_COMMAND_PERMISSION.replace("{sub}","reload"),false));
        subCommands.add(new BackCommand(plugin,"back","Takes you back to the last location when you used wild",null,Collections.singletonList("b"),DEFAULT_COMMAND_PERMISSION.replace("{sub}","wild"),false));
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (args.length == 0) {
            subCommands.forEach(c -> sender.sendMessage(c.getHelpMessage(sender)));
            return;
        }

        final Optional<BaseCommand> bc = subCommands.stream().filter(c -> c.getName().equalsIgnoreCase(args[0])).findFirst();

        if (!bc.isPresent()) {
            sender.sendMessage(String.format("Command '%s' not found.", args[0]));
            return;
        }

        bc.get().onCommand(sender, bc.get(), bc.get().getAliases().size() > 0 ? bc.get().getAliases().get(0) : null, Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> suggest(CommandSender sender, Command command, String[] args) {
        if (args.length == 0) {
            return subCommands.stream()
                .filter(c -> !c.isOnlyPlayer() && sender instanceof Player)
                .filter(c -> c.getPermission() != null && sender.hasPermission(c.getPermission()))
                .map(BaseCommand::getName)
                .collect(Collectors.toList());
        } else if (args.length == 1) {
            final Optional<BaseCommand> bc = this.getCommand(args[0]);

            if (bc.isPresent()) {
                return bc.get().onTabComplete(sender, bc.get(), bc.get().getAliases().size() > 0 ? bc.get().getAliases().get(0) : null, Arrays.copyOfRange(args, 1, args.length));
            } else {
                return subCommands.stream()
                    .filter(c -> !c.isOnlyPlayer() && sender instanceof Player)
                    .filter(c -> c.getPermission() != null && sender.hasPermission(c.getPermission()))
                    .map(BaseCommand::getName)
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }

    public BaseCommand[] getCommands() {
        return subCommands.toArray(new BaseCommand[subCommands.size()]);
    }

    public Optional<BaseCommand> getCommand(String name) {
        return subCommands.stream().filter(c -> c.getName().equalsIgnoreCase(name) || c.getAliases().stream().anyMatch(s -> s.equalsIgnoreCase(name))).findAny();
    }
}
