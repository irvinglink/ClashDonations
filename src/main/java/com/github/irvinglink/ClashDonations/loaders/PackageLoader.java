package com.github.irvinglink.ClashDonations.loaders;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import com.github.irvinglink.ClashDonations.handler.PackageHandler;
import com.github.irvinglink.ClashDonations.models.Package;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public final class PackageLoader implements ILoader {

    private final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    @Override
    public void load() {

        final FileConfiguration config = plugin.getConfig();
        String path = "Packages";

        if (!config.contains(path) || !config.isConfigurationSection(path)) return;

        for (String package_identifier : config.getConfigurationSection(path).getKeys(false)) {

            String placeholderName = config.getString(path + "." + package_identifier + ".placeholder_name");
            String placeholderDetails = config.getString(path + "." + package_identifier + ".placeholder_details");

            List<String> accept_commands = config.getStringList(path + "." + package_identifier + ".commands_on_accept");
            List<String> decline_commands = config.getStringList(path + "." + package_identifier + ".commands_on_decline");
            List<String> ban_commands = config.getStringList(path + "." + package_identifier + ".ban_commands");

            PackageHandler.addPackage(new Package(plugin, package_identifier, placeholderName, placeholderDetails, accept_commands, decline_commands, ban_commands));

        }

    }

    @Override
    public void update() {
        if (!PackageHandler.getPackages().isEmpty()) PackageHandler.getPackages().clear();
        load();
    }

}
