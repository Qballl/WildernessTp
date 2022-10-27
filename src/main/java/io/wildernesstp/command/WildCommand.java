package io.wildernesstp.command;

import io.papermc.lib.PaperLib;
import io.wildernesstp.Main;
import io.wildernesstp.generator.LocationGenerator;
import io.wildernesstp.util.LimitManager;
import io.wildernesstp.util.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

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
        LimitManager limitManager = new LimitManager(WildCommand.super.getPlugin());
        Player p = null;
        World world = null; //Make a boolean flag for this to solve a NPE
        boolean usedWorldFlag = false;
        boolean usedPlayerFlag = false;
        final Set<Predicate<Location>> filters = new HashSet<>();
        long start = System.currentTimeMillis();
        if (!(sender instanceof Player)) {
            if (args.length >  1) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].startsWith("-w")) {
                        if ((i + 1) > args.length) {
                            return;
                        }
                        world = Bukkit.getWorld(args[i + 1]);
                    } else if (args[i].startsWith("-p")) {
                        if ((i + 1) > args.length) {
                            return;
                        }
                        p = Bukkit.getPlayer(args[i + 1]);
                    }
                }
            }
        } else{
            p = (Player) sender;


        if (args.length >= 2) {
            final AtomicReference<Biome> biome = new AtomicReference<>();

            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("-b")) {
                    if ((i + 1) > args.length) {
                        return;
                    }
                    try {
                        biome.set(Biome.valueOf(args[i + 1].toUpperCase()));
                    }catch (IllegalArgumentException e){
                        p.sendMessage(ChatColor.RED+"Unacceptable biome");
                    }
                } else if (args[i].startsWith("-w")) {
                    if ((i + 1) > args.length) {
                        return;
                    }
                    usedWorldFlag = true;
                    world = Bukkit.getWorld(args[i + 1]);
                } else if (args[i].startsWith("-p")) {
                    if ((i + 1) > args.length) {
                        return;
                    }
                    usedPlayerFlag = true;
                    p = Bukkit.getPlayer(args[i + 1]);
                }
            }


            if (biome.get() != null) {
                if (!sender.hasPermission(LocationGenerator.BIOME_PERMISSION.replace("{biome}", biome.get().name()))) {
                    sender.sendMessage("Can't teleport to biome.");
                    return;
                }else{
                    filters.add(location -> location.getBlock().getBiome().equals(biome.get()));
                }
            }

            //filters.add(l -> l.getBlock().getBiome() == biome.get());
        }
            if (!usedPlayerFlag) {
                p = (Player) sender;
            }

            if (!usedWorldFlag) {
                world = p.getWorld();
            }


        }

        // Providing world optional parameter.
        final Player player = p;
        if (player == null) {
            throw new IllegalStateException("How did we get here?");
        }




        //final Future<Optional<Location>> future = super.getPlugin().getExecutorService().submit(() -> WildCommand.super.getPlugin().getGenerator().generate(player, filters));
        if (WildCommand.super.getPlugin().getConfig().getBoolean("limit_usage")) {
            if (!player.hasPermission("wildernesstp.bypass.limit")) {
                if (limitManager.getUses(player.getUniqueId()) >= limitManager.getLimit(player)) {
                    player.sendMessage(super.getPlugin().getLanguage().general().limitReached());
                    return;
                }
                limitManager.addUse(player.getUniqueId());
            }
        }
        if (!player.hasPermission("wildernesstp.bypass.cooldown") &&
            WildCommand.super.getPlugin().getCooldownManager().hasCooldown(player)) {
            player.sendMessage(WildCommand.super.getPlugin().getLanguage().general().cooldown().replace("{wait}",
                String.valueOf(WildCommand.super.getPlugin().
                    getCooldownManager().getCooldown(player))));
            //player.teleport(TeleportManager.getBack(player.getUniqueId()));
            return;
        }

        TeleportManager.addToTeleport(player.getUniqueId());


        final int delay;
        if(player.hasPermission("wildernesstp.bypass.delay"))
            delay = 0;
        else
            delay = WildCommand.super.getPlugin().getConfig().getInt("delay", 5);
        Optional<Location> location = WildCommand.super.getPlugin().getGenerator().generate(player,world,filters);
        long end = System.currentTimeMillis();
        long timePassed = end - start;
       // System.out.println(timePassed);
        if(!WildCommand.super.getPlugin().getConfig().getBoolean("doCountdown"))
            player.sendMessage(getPlugin().getLanguage().teleport().warmUp().replace("{sec}", delay+""));
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(super.getPlugin(), new Runnable() {
            private int i = delay;
            @Override
            public void run() {
                if (TeleportManager.checkRetryLimit(player.getUniqueId())) {
                    player.sendMessage(WildCommand.this.getPlugin().getLanguage().teleport().noLocFound());
                    TeleportManager.removeRetryLimit(player.getUniqueId());
                    return;
                }
                if (i == 0) {
                    if (TeleportManager.checkMoved(player.getUniqueId())) {
                        TeleportManager.removeAll(player.getUniqueId());
                        return;
                    }
                    if (location.isPresent()) {
                        Location l = location.get();
                        TeleportManager.setBack(player.getUniqueId(),l);
                        WildCommand.super.getPlugin().takeMoney(player);
                        if (TeleportManager.checkTeleport(player.getUniqueId())) {
                            player.sendMessage(getPlugin().getLanguage().teleport().teleporting().replace("{loc}", convertLoc(l)));
                            PaperLib.teleportAsync(player, l);
                            getPlugin().runCommands(player);
                            if (!player.hasPermission("wildernesstp.bypass.cooldown"))
                                WildCommand.super.getPlugin().getCooldownManager().setCooldown(player);
                            TeleportManager.removeAll(player.getUniqueId());
                        }
                    }
                } else {
                    if (TeleportManager.checkTeleport(player.getUniqueId())) {
                        if(WildCommand.super.getPlugin().getConfig().getBoolean("doCountdown"))
                            player.sendMessage(getPlugin().getLanguage().teleport().warmUp().replace("{sec}", i-- + ""));
                    }
                }
                if (TeleportManager.checkMoved(player.getUniqueId())) {
                    TeleportManager.removeAll(player.getUniqueId());
                    return;
                }
            }
        }, 0, 20);
        Bukkit.getScheduler().runTaskLater(super.getPlugin(), task::cancel, (delay + 1) * 20);
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

    private String convertLoc(Location loc) {
        return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
    }
}
