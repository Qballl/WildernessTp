package io.wildernesstp.command;

import io.papermc.lib.PaperLib;
import io.wildernesstp.Language;
import io.wildernesstp.Main;
import io.wildernesstp.generator.LocationGenerator;
import io.wildernesstp.util.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

    private static final String DEFAULT_COMMAND_PERMISSION = "wildernesstp.command.wild;wildernesstp.*";

    public WildCommand(Main plugin, String name, String description, String usage, List<String> aliases, String permission, boolean onlyPlayer) {
        super(plugin, name, description, usage, aliases, (permission != null ? permission : DEFAULT_COMMAND_PERMISSION), onlyPlayer);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Player p = null;
        if(!(sender instanceof Player)) {
            if(args.length >=1){
                p = Bukkit.getPlayer(args[0]);
                if(p == null){
                    sender.sendMessage("Player is offline");
                    return;
                }
            }
        }else
            p = (Player)sender;
        final Player player = p;
        final Set<Predicate<Location>> filters = new HashSet<>();
        /*if (args.length > 0) {
            final Biome biome = Biome.valueOf(args[0].toUpperCase());

            if (!sender.hasPermission(LocationGenerator.BIOME_PERMISSION.replace("{biome}", biome.name()))) {
                sender.sendMessage("Can't teleport to biome.");
                return;
            }

           // filters.add(l -> l.getBlock().getBiome() == biome);
        }*/


        //final Future<Optional<Location>> future = super.getPlugin().getExecutorService().submit(() -> WildCommand.super.getPlugin().getGenerator().generate(player, filters));
        if(!player.hasPermission("wildernesstp.bypass.cooldown") &&
            WildCommand.super.getPlugin().getCooldownManager().hasCooldown(player)){
                player.sendMessage(WildCommand.super.getPlugin().getLanguage().general().cooldown().replace( "{wait}",
                    String.valueOf(WildCommand.super.getPlugin().
                        getCooldownManager().getCooldown(player))));
                return;
        }
        else
            WildCommand.super.getPlugin().getCooldownManager().setCooldown(player);
        TeleportManager.addToTeleport(player.getUniqueId());

        final int delay = WildCommand.super.getPlugin().getConfig().getInt("delay", 5);
        Optional<Location> location = WildCommand.super.getPlugin().getGenerator().generate(player,filters);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(super.getPlugin(), new Runnable() {
        private int i = delay;
            @Override
            public void run() {
                if(TeleportManager.checkLimit(player.getUniqueId())){
                    player.sendMessage(getPlugin().getLanguage().teleporting().noLocFound());
                }
                if(i==0 ){
                    if(TeleportManager.checkMoved(player.getUniqueId())) {
                        TeleportManager.removeAll(player.getUniqueId());
                        return;
                    }
                    if (location.isPresent()) {
                        Location l = location.get();
                        WildCommand.super.getPlugin().takeMoney(player);
                        if (TeleportManager.checkTeleport(player.getUniqueId())) {
                            player.sendMessage(getPlugin().getLanguage().teleporting().teleporting().replace("{loc}",convertLoc(l)));
                            PaperLib.teleportAsync(player, l);
                            TeleportManager.removeAll(player.getUniqueId());
                        }
                    }
                } else {
                    if(TeleportManager.checkTeleport(player.getUniqueId()))
                        player.sendMessage(getPlugin().getLanguage().teleporting().warmUp().replace("{sec}",i--+""));
                }
                if(TeleportManager.checkMoved(player.getUniqueId())) {
                    TeleportManager.removeAll(player.getUniqueId());
                    return;
                }
            }
        },0,20);
        Bukkit.getScheduler().runTaskLater(super.getPlugin(), task::cancel,(delay+1)*20);
    }

    @Override
    public List<String> suggest(CommandSender sender, Command command, String[] args) {
        /*List<Biome> biomes = new ArrayList<>(Arrays.asList(Biome.values()));
        biomes.removeAll(super.getPlugin().getBlacklistedBiomes());

        if (args.length == 0) {
            return biomes.stream().filter(b -> sender.hasPermission(LocationGenerator.BIOME_PERMISSION.replace("{biome}", b.name()))).map(Biome::name).collect(Collectors.toList());
        } else if (args.length == 1) {
            return biomes.stream().filter(b -> b.name().toLowerCase().startsWith(args[0].toLowerCase()) && sender.hasPermission(LocationGenerator.BIOME_PERMISSION.replace("{biome}", b.name()))).map(Biome::name).collect(Collectors.toList());
        }*/

        return Collections.emptyList();
    }

    private String convertLoc(Location loc){
        return loc.getBlockX() + ", " + loc.getBlockY() +", "+ loc.getBlockZ();
    }
}
