package io.wildernesstp.portal;

import io.wildernesstp.Main;
import io.wildernesstp.util.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

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
public final class PortalManager extends Manager {

    /**
     * Used to speed up look-ups and minimize disk-io.
     * Though, the portals are lazily cached (= Only when accessed it will be attempted to get cached).
     */
    private static final Map<Integer, Portal> portalCache = new HashMap<>();
    private static final Set<PortalEditSession> sessionCache = new HashSet<>();

    private File  file;
    private YamlConfiguration portals;

    private final ConfigurationSection root;

    public PortalManager(Main plugin) {
        super(plugin);
        createFile();
        portals = getPortalsConfiguration();
        this.root = portals.getConfigurationSection("portals");
        fillCache();
    }

    private void createFile(){
        file = new File(this.plugin.getDataFolder() , "Portals.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
                portals = YamlConfiguration.loadConfiguration(file);
                portals.createSection("portals");
                portals.save(file);
            }catch(IOException e){
                e.printStackTrace();
            }

        }else{
            portals = YamlConfiguration.loadConfiguration(file);
        }
    }

    private YamlConfiguration getPortalsConfiguration(){
        file = new File(this.plugin.getDataFolder() , "Portals.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    private void fillCache(){
        portals = getPortalsConfiguration();
        if(portals.getConfigurationSection("portals")==null)
            return;
        for(String portalNum : portals.getConfigurationSection("portals").getKeys(false)){
            portalCache.putIfAbsent(Integer.parseInt(portalNum), loadFromFile(portalNum));
        }
    }

    public void saveCache(){
        file = new File(plugin.getDataFolder(),"Portals.yml");
        portals = YamlConfiguration.loadConfiguration(file);
        for(String s : portals.getConfigurationSection("portals").getKeys(false)) {
            for (Portal portal : portalCache.values()) {
                if(!portal.equals(loadFromFile(s))) {
                    String path = "portals." + (root.getKeys(false).size() + 1) + ".";
                    portals.set(path + "world", portal.getWorld().getName());
                    portals.set(path + "pos-one", String.format("%d, %d, %d", portal.getPositionOne().getBlockX(), portal.getPositionOne().getBlockY(), portal.getPositionOne().getBlockZ()));
                    portals.set(path + "pos-two", String.format("%d, %d, %d", portal.getPositionTwo().getBlockX(), portal.getPositionTwo().getBlockY(), portal.getPositionTwo().getBlockZ()));
                }
            }
            try {
                portals.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Portal createPortal(Portal portal) {
        if (this.getPortal(this.getPortalId(portal)).isPresent()) {
            throw new IllegalStateException("Portal already exists.");
        }

        portalCache.put(portalCache.size(),portal);
        return portal;
    }

    public void destroyPortal(int id) {
        portals = getPortalsConfiguration();

        portals.set("portals."+id,null);

        try{
            portals.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

        portalCache.remove(id);
    }

    public void destroyPortal(Portal portal) {
        //destroyPortal(getPortalId(portal));
        for(int i : portalCache.keySet()){
            if(portalCache.get(i).equals(portal));
                destroyPortal(i);
        }

    }

    public int getPortalId(Portal portal){
        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getPortal(i).isPresent() && this.getPortal(i).get().equals(portal)) {
                return i;
            }
        }

        return -1;
    }

    public Optional<Portal> getPortal(int id) {
        if (portalCache.containsKey(id)) {
            return Optional.of(portalCache.get(id));
        }

        final ConfigurationSection cs = root.getConfigurationSection(String.valueOf(id));

        if (cs == null) {
            return Optional.empty();
        }

        final World world = Bukkit.getWorld(Objects.requireNonNull(cs.getString("world")));
        final Double[] one = Arrays.stream(Objects.requireNonNull(cs.getString("pos-one")).split(", ")).map(Double::valueOf).toArray(Double[]::new);
        final Double[] two = Arrays.stream(Objects.requireNonNull(cs.getString("pos-two")).split(", ")).map(Double::valueOf).toArray(Double[]::new);
        final Portal portal = new Portal(
            new Location(world, one[0], one[1], one[2]),
            new Location(world, two[0], two[1], two[2]));

        portalCache.putIfAbsent(id, portal);
        return Optional.of(portal);
    }

    public Set<Portal> getPortals() {
        final Set<Portal> portals = new HashSet<>();

        for (int min = 0, max = root.getKeys(false).size(), i = min; i < max; i++) {
            if (this.getPortal(i).isPresent()) {
                portals.add(this.getPortal(i).get());
            }
        }

        return portals;
    }

    public Optional<Portal> getNearbyPortal(Player player) {
       return portalCache.values().stream().filter(p -> {
           if(p.getWorld().equals(player.getWorld()))
               return p.contains(player.getLocation().toVector());
           return false;
       }).findAny();
    }

    public Optional<Portal> getNearbyPortal(Player player, int radius) {
        return portalCache.values().stream().filter(p -> {
            if(p.getWorld().equals(player.getWorld()))
                return p.getPositionOne().distance(player.getLocation()) <= radius || p.getPositionTwo().distance(player.getLocation()) <= radius;
            return false;
        }).findAny();
    }


    public PortalEditSession startSession(Player player) {
        if (sessionCache.stream().anyMatch(s -> s.getPlayer().equals(player))) {
            throw new IllegalStateException("Cannot start session twice.");
        }

        final PortalEditSession session = new PortalEditSession(player);
        sessionCache.add(session);

        return session;
    }

    public void endSession(Player player) {
        sessionCache.removeIf(s -> s.getPlayer().equals(player));
    }

    public Optional<PortalEditSession> getSession(Player player) {
        return sessionCache.stream().filter(s -> s.getPlayer().equals(player)).findAny();
    }

    private Portal loadFromFile(String portalNum){
        final World world = Bukkit.getWorld(portals.getString("portals."+portalNum+".world"));
        final Double[] one = Arrays.stream(portals.getString("portals."+portalNum+".pos-one").split(", ")).map(Double::valueOf).toArray(Double[]::new);
        final Double[] two = Arrays.stream(portals.getString("portals."+portalNum+".pos-two").split(", ")).map(Double::valueOf).toArray(Double[]::new);
       return new Portal(
            new Location(world, one[0], one[1], one[2]),
            new Location(world, two[0], two[1], two[2]));
    }
}
