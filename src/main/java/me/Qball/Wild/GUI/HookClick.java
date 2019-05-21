package me.Qball.Wild.GUI;

import me.Qball.Wild.Wild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class HookClick implements Listener {
    public static Plugin wild = Wild.getInstance();

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getInventory() == null)
            return;
        if (e.getView().getTitle().equalsIgnoreCase("Hooks")) {

            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String name = meta.getDisplayName().toLowerCase();
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            switch (name) {
                case "close":
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    break;
                case "towny hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Towny");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "factions hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Factions");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "factionsuuid hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "FactionsUUID");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "griefprevention hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "GriefPrevention");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "worldguard hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "WorldGuard");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "kingdom hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Kingdoms");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "residence hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Residence");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player)e.getWhoClicked());
                    break;
                case "landlord hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "LandLord");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player)e.getWhoClicked());
                    break;
                case "legacyfactions hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "LegacyFactions");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "feudal hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Feudal");
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "true":
                    String val = InvClick.toSet.get(e.getWhoClicked().getUniqueId());
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    wild.getConfig().set(val, true);
                    wild.saveConfig();
                    Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
                case "false":
                    val = InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    wild.getConfig().set(val, false);
                    wild.saveConfig();
                    Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
                case "back":
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MainGui.OpenGUI((Player)e.getWhoClicked());
                    InvClick.set.remove(e.getWhoClicked().getUniqueId());
                    InvClick.add.remove(e.getWhoClicked().getUniqueId());
                    InvClick.messages.remove(e.getWhoClicked().getUniqueId());
                    InvClick.sounds.remove(e.getWhoClicked().getUniqueId());
                    break;
                default:
                    scheduler.runTask(wild, () -> e.getWhoClicked().closeInventory());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
            }

        }
    }
}
