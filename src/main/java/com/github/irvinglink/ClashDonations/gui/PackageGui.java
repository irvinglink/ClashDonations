package com.github.irvinglink.ClashDonations.gui;

import com.github.irvinglink.ClashDonations.gui.manager.IMenu;
import com.github.irvinglink.ClashDonations.models.UserData;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public final class PackageGui implements IMenu {


    private final List<UserData> userData;

    private final int[] packageSlots = {11, 12, 13, 14, 15};


    public PackageGui(List<UserData> userData) {
        this.userData = userData;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onDrag(InventoryDragEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public boolean enableSlots() {
        return false;
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("Gui.package_gui.rows") * 9, chat.replace(plugin.getConfig().getString("Gui.package_gui.title"), true));

        for (UserData userData : userData) {
            
        }

        return inventory;
    }

    public List<UserData> getUserData() {
        return userData;
    }
}
