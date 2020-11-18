package io.wildernesstp.gui;

import io.wildernesstp.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GUIHandler implements Listener {

    private final Main main;
    private final List<GUI> guis;

    public GUIHandler(Main main, GUI... guis) {
        this.main = main;
        this.guis = new ArrayList<>(Arrays.asList(guis));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(InventoryClickEvent e) {
        for (GUI gui : guis) {
            if (e.getInventory().equals(gui.createInventory()) && gui.hasClickHandler()) {
                gui.getClickHandler().accept(e);
            }
        }
    }
}
