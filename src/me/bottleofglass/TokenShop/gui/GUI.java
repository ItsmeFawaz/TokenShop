package me.bottleofglass.TokenShop.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class GUI implements Cloneable {
    protected static List<GUI> guiList = new ArrayList<>();
    protected HashMap<Integer, GUIButton> buttons = new HashMap<>();
    protected String guiTitle = "";
    protected Player owner;
    public GUI() {}
    public GUI(Player p) {
        owner = p;
    }
    public GUI(String title) {guiTitle = title;}
    public GUI(Player p,String title) {
        guiTitle = title;
        owner = p;
    }
    public abstract void open();
    public GUIButton getButtonAt(int i) {
        return buttons.get(i);
    }
    public static List<GUI> getGuiList() {
        return guiList;
    }
    public abstract Inventory getInventory();
    public void close() {
        owner.closeInventory();
        guiList.remove(this);
    }
    public HashMap<Integer, GUIButton> getButtons() {return buttons;}
    public Player getOwner() {return owner;}
    public void setOwner(Player p) { owner = p;}
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
