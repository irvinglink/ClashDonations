package com.github.irvinglink.ClashDonations.gui;

import com.github.irvinglink.ClashDonations.gui.manager.IMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public final class ConfirmGui implements IMenu {


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
        return null;
    }

}
