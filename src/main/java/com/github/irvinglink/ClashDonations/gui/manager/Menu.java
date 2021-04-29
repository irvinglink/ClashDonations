package com.github.irvinglink.ClashDonations.gui.manager;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class Menu {

    private static final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    public static void openMenu(Player player, IMenu menu) {

        UUID uuid = player.getUniqueId();

        player.closeInventory();

        if (!plugin.getPlayerMenus().containsKey(player.getUniqueId())) plugin.getPlayerMenus().put(uuid, menu);
        else plugin.getPlayerMenus().replace(uuid, menu);

        player.openInventory(menu.getInventory());

    }

    public static void closeMenu(Player player) {

        player.closeInventory();
        plugin.getPlayerMenus().remove(player.getUniqueId());

    }

}
