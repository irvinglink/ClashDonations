package com.github.irvinglink.ClashDonations.gui;

import com.github.irvinglink.ClashDonations.gui.manager.IMenu;
import com.github.irvinglink.ClashDonations.utils.items.CustomItemsUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public final class ConfirmGui implements IMenu {

    private int[] confirmSlots = {0, 1, 2, 9, 10, 11, 18, 19, 20};
    private int[] declineSlots = {6, 7, 8, 15, 16, 17, 24, 25, 26};

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
        Inventory inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("Gui.confirm_gui.rows") * 9, chat.replace(plugin.getConfig().getString("Gui.confirm_gui.title"), true));

        for (int i = 0; i < confirmSlots.length; i++)
            inventory.setItem(confirmSlots[i], CustomItemsUtils.CustomItem.CONFIRM.getItemStack());

        for (int i = 0; i < declineSlots.length; i++)
            inventory.setItem(declineSlots[i], CustomItemsUtils.CustomItem.DECLINE.getItemStack());

        return inventory;
    }

}
