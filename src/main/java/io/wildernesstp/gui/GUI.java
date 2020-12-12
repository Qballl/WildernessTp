package io.wildernesstp.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public abstract class GUI {

    private final String name;
    private final int size;
    private Inventory inventory;
    private Consumer<InventoryClickEvent> clickHandler;

    public GUI(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public final String getName() {
        return name;
    }

    public final int getSize() {
        return size;
    }

    public void setClickHandler(Consumer<InventoryClickEvent> handler) {
        this.clickHandler = handler;
    }

    public Consumer<InventoryClickEvent> getClickHandler() {
        return clickHandler;
    }

    public boolean hasClickHandler() {
        return clickHandler != null;
    }

    public abstract void show(Player p);

    protected final Inventory createInventory() {
        return (inventory != null ? inventory : (inventory = Bukkit.createInventory(null, size, name)));
    }

    public static class Size {

        public static final int ONE_ROW = 9;
        public static final int TWO_ROWS = 18;
        public static final int THREE_ROWS = 27;
        public static final int FOUR_ROWS = 36;
        public static final int FIVE_ROWS = 45;
        public static final int SIX_ROWS = 54;
    }
}
