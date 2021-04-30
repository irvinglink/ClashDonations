package com.github.irvinglink.ClashDonations.utils.items;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import com.github.irvinglink.ClashDonations.utils.chat.Chat;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class CustomItemsUtils {

    private static final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);
    private static final Chat chat = plugin.getChat();

    private static final Map<CustomItem, ItemStack> customItems = Collections.synchronizedMap(new HashMap<>());


    public static void addItem(CustomItem type, ItemStack itemStack) {
        customItems.put(type, itemStack);
    }

    public static void removeItem(CustomItem type) {
        customItems.remove(type);
    }

    public static Map<CustomItem, ItemStack> getCustomItems() {
        return customItems;
    }

    public static Material getMaterial(String gui, String identifier, String key) {

        String path = "Gui." + gui + ".items.";

        final FileConfiguration config = plugin.getConfig();

        if (!config.contains(path) || !config.isConfigurationSection(path)) return null;

        return Material.valueOf(config.getString(path + identifier + "." + key).toUpperCase());
    }

    public static String getString(String gui, String identifier, String key) {

        String path = "Gui." + gui + ".items.";

        final FileConfiguration config = plugin.getConfig();

        if (!config.contains(path) || !config.isConfigurationSection(path)) return null;

        return config.getString(path + identifier + "." + key);

    }

    public static byte getByte(String gui, String identifier, String key) {

        String path = "Gui." + gui + ".items.";

        final FileConfiguration config = plugin.getConfig();

        if (!config.contains(path) || !config.isConfigurationSection(path)) return (byte) 0;

        return (byte) config.getInt(path + identifier + "." + key);

    }

    public static List<String> getStringList(String gui, String identifier, String key) {

        String path = "Gui." + gui + ".items.";

        final FileConfiguration config = plugin.getConfig();

        if (!config.contains(path) || !config.isConfigurationSection(path)) return null;

        return config.getStringList(path + identifier + "." + key);

    }


    public enum CustomItem {


        AVAILABLE_PACKAGE(getMaterial("package_gui", "available_package", "item"), getString("package_gui", "available_package", "name"), getByte("package_gui", "available_package", "data"), getStringList("package_gui", "available_package", "lore")),
        UNAVAILABLE_PACKAGE(getMaterial("package_gui", "unvailable-package", "item"), getString("package_gui", "unvailable-package", "name"), getByte("package_gui", "unvailable-package", "data"), getStringList("package_gui", "unvailable-package", "lore")),
        CONFIRM(getMaterial("confirm_gui", "confirm_item", "item"), getString("confirm_gui", "confirm_item", "name"), getByte("confirm_gui", "confirm_item", "data"), getStringList("confirm_gui", "confirm_item", "lore")),
        DECLINE(getMaterial("confirm_gui", "decline_item", "item"), getString("confirm_gui", "decline_item", "name"), getByte("confirm_gui", "decline_item", "data"), getStringList("confirm_gui", "decline_item", "lore"));


        private final Material material;
        private final String name;
        private final byte data;
        private final List<String> lore;

        CustomItem(Material material, String name, byte data, List<String> lore) {
            this.material = material;
            this.name = name;
            this.data = data;
            this.lore = lore;
        }

//        UNAVAILABLE_PACKAGE,CONFIRM,DECLINE

        public ItemStack getItemStack() {

            ItemStack itemStack = new ItemStack(material, 1, data);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(chat.replace(name, true));
            itemMeta.setLore(lore.stream().map(x -> chat.replace(x, true)).collect(Collectors.toList()));

            itemStack.setItemMeta(itemMeta);

            return itemStack;

        }

    }

}
