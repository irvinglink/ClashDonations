package com.github.irvinglink.ClashDonations.gui;

import com.github.irvinglink.ClashDonations.gui.manager.IMenu;
import com.github.irvinglink.ClashDonations.gui.manager.Menu;
import com.github.irvinglink.ClashDonations.models.UserData;
import com.github.irvinglink.ClashDonations.utils.items.CustomItemsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PackageGui implements IMenu {


    private final List<UserData> userData;

    private final int[] packageSlots = {11, 12, 13, 14, 15};

    private final Map<Integer, UserData> packageSlot = Collections.synchronizedMap(new HashMap<>());

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

        int slot = event.getRawSlot();

        Player player = (Player) event.getWhoClicked();

        for (int i = 0; i < packageSlots.length; i++) {

            if ((slot == packageSlots[i]) && (packageSlot.containsKey(slot)))
                Menu.openMenu(player, new ConfirmGui(packageSlot.get(slot)));

        }

    }

    @Override
    public boolean enableSlots() {
        return false;
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("Gui.package_gui.rows") * 9, chat.replace(plugin.getConfig().getString("Gui.package_gui.title"), true));

        inventory.setItem(4, CustomItemsUtils.CustomItem.PACKAGES_SIGN.getItemStack());

        for (int i = 0; i < packageSlots.length; i++) {

            if (i < userData.size()) {

                inventory.setItem(packageSlots[i], CustomItemsUtils.CustomItem.AVAILABLE_PACKAGE.getPackageItem(userData.get(i).getPackage()));
                packageSlot.put(packageSlots[i], userData.get(i));

            } else inventory.setItem(packageSlots[i], CustomItemsUtils.CustomItem.UNAVAILABLE_PACKAGE.getItemStack());

        }

        return inventory;
    }

    public List<UserData> getUserData() {
        return userData;
    }
}
