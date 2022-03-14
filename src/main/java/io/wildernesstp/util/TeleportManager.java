package io.wildernesstp.util;

import org.bukkit.Location;

import java.util.*;

public class TeleportManager {

    private static final List<UUID> moved = new ArrayList<>();
    private static final List<UUID> needToTeleport = new ArrayList<>();
    private static final List<UUID> limitHit = new ArrayList<>();
    private static final Map<UUID, Location> backLocations= new HashMap<>();

    public static void addToTeleport(UUID uuid){
        needToTeleport.add(uuid);
    }

    public static boolean checkMoved(UUID uuid){
        return moved.contains(uuid);
    }

    public static void addRetryLimit(UUID uuid){
        limitHit.add(uuid);
    }

    public static void removeRetryLimit(UUID uuid){
        limitHit.remove(uuid);
    }

    public static boolean checkRetryLimit(UUID uuid){
        return limitHit.contains(uuid);
    }

    public static boolean checkTeleport(UUID uuid){
        return needToTeleport.contains(uuid);
    }

    public static void moved(UUID uuid){
        if(!moved.contains(uuid)){
            moved.add(uuid);
            needToTeleport.remove(uuid);
        }
    }

    public static void removeAll(UUID uuid){
        moved.remove(uuid);
        needToTeleport.remove(uuid);
    }

    public static void clearAll(){
        needToTeleport.clear();
        moved.clear();
    }

    public static void noMoney(UUID uuid){
        needToTeleport.remove(uuid);
    }

    public static void setBack(UUID uuid, Location loc){
        backLocations.put(uuid,loc);
    }

    public static Location getBack(UUID uuid){
        return backLocations.get(uuid);
    }
}
