package me.bottleofglass.TokenShop.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashMap;

public class CustomizedGUI extends GUI {
    private Inventory inventory;
    private int rows;
    public CustomizedGUI(int rows) {
        this.rows = rows;
        inventory = Bukkit.createInventory(null, rows*9);
    }
    public CustomizedGUI(Player p, int rows) {
        super(p);
        owner = p;
        this.rows = rows;
        inventory = Bukkit.createInventory(p, rows*9);
    }
    public CustomizedGUI(Player p, int rows, String title) {
        super(p);
        inventory = Bukkit.createInventory(p, rows*9, title);
    }

    public boolean addButton(int i, GUIButton button) {
        if(i < inventory.getSize() && !buttons.containsKey(i)) {
            buttons.put(i,button);
            inventory.setItem(i, button.getStack());
            return true;
        }
        return false;
    }
    @Override
    public void open() {
        if(inventory == null) {
            inventory = Bukkit.createInventory(owner, rows*9);
        }
        owner.openInventory(inventory);
        guiList.add(this);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
