package me.bottleofglass.TokenShop.gui;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent evt) {
        GUI toRemove = null;
        for(GUI gui : GUI.getGuiList()) {
            if(gui.getInventory().equals(evt.getClickedInventory())) {
                evt.setCancelled(true);
                if(gui instanceof ListGUI) {
                    ListGUI listGUI = (ListGUI) gui;
                    int pageNo = Integer.parseInt(String.valueOf(evt.getInventory().getItem(49).getItemMeta().getDisplayName().charAt(evt.getInventory().getItem(49).getItemMeta().getDisplayName().length()-1)))-1;
                    if(listGUI.getButtonAt(evt.getSlot(), pageNo) != null) {
                        gui.close();
                        toRemove = gui;
                        listGUI.getButtonAt(evt.getSlot(), pageNo).action();
                        break;
                    }
                }
                if(gui.getButtonAt(evt.getSlot()) != null && gui.getButtonAt(evt.getSlot()).hasAction()) {
                    gui.close();
                    toRemove = gui;
                    gui.getButtonAt(evt.getSlot()).action();
                    break;
                }
            }
        }
        if(toRemove != null) GUI.getGuiList().remove(toRemove);
    }
    @EventHandler
    public void onClose(InventoryCloseEvent evt) {
        for(GUI gui : GUI.getGuiList()) {
            if(gui.getInventory().equals(evt.getInventory())) {
                GUI.getGuiList().remove(gui);
                return;
            }
        }
    }
}
