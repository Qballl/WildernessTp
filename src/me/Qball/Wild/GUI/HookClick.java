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

public class HookClick implements Listener {
    public static Plugin wild = Wild.getInstance();

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getInventory() == null)
            return;
        if (e.getInventory().getName().equalsIgnoreCase("Hooks")) {

            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String name = meta.getDisplayName().toLowerCase();
            switch (name) {
                case "close":
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    e.getWhoClicked().closeInventory();
                    break;
                case "towny hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Towny");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "factions hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Factions");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "factionsuuid hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "FactionsUUID");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "griefprevention hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "GriefPrevention");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "worldguard hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "WorldGuard");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "kingdom hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Kingdoms");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player) e.getWhoClicked());
                    break;
                case "residence hook":
                    InvClick.toSet.put(e.getWhoClicked().getUniqueId(), "Residence");
                    e.getWhoClicked().closeInventory();
                    TrueFalseGui.openTrue((Player)e.getWhoClicked());
                    break;
                case "true":
                    String val = InvClick.toSet.get(e.getWhoClicked().getUniqueId());
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    wild.getConfig().set(val, true);
                    wild.saveConfig();
                    Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                    e.getWhoClicked().closeInventory();
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
                case "false":
                    val = InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    InvClick.toSet.remove(e.getWhoClicked().getUniqueId());
                    wild.getConfig().set(val, false);
                    wild.saveConfig();
                    Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
                    e.getWhoClicked().closeInventory();
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
                case "back":
                    e.getWhoClicked().closeInventory();
                    MainGui.OpenGUI((Player)e.getWhoClicked());
                    if (InvClick.set.contains(e.getWhoClicked().getUniqueId())) {
                        InvClick.set.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (InvClick.add.contains(e.getWhoClicked().getUniqueId())) {
                        InvClick.add.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (InvClick.messages.contains(e.getWhoClicked().getUniqueId())) {
                        InvClick.messages.remove(e.getWhoClicked().getUniqueId());
                    }
                    if (InvClick.sounds.contains(e.getWhoClicked().getUniqueId())) {
                        InvClick.sounds.remove(e.getWhoClicked().getUniqueId());
                    }
                    break;
                default:
                    e.getWhoClicked().closeInventory();
                    MainGui.removeEdit((Player) e.getWhoClicked());
                    break;
            }

        }
    }
}
