package com.github.irvinglink.ClashDonations.models;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import com.github.irvinglink.ClashDonations.utils.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Package {

    private final ClashDonationsPlugin plugin;
    private final Chat chat;

    private final String packageName;
    private final String placeholder_name;
    private final String placeholder_details;

    private final List<String> accept_commands;
    private final List<String> decline_commands;
    private final List<String> ban_commands;

    public Package(ClashDonationsPlugin plugin, String packageName, String placeholder_name, String placeholder_details, List<String> accept_commands, List<String> decline_commands, List<String> ban_commands) {
        this.plugin = plugin;
        this.chat = plugin.getChat();

        this.packageName = packageName;
        this.placeholder_name = placeholder_name;
        this.placeholder_details = placeholder_details;
        this.accept_commands = accept_commands;
        this.decline_commands = decline_commands;
        this.ban_commands = ban_commands;

    }

    public void execute(OfflinePlayer player, PackageAction packageAction) {

        List<String> executions = Collections.synchronizedList(new ArrayList<>());

        if (!executions.isEmpty()) executions.clear();

        switch (packageAction) {

            case ACCEPT:
                executions.addAll(accept_commands);
                break;
            case DECLINE:
                executions.addAll(decline_commands);
                break;
            case BAN:
                executions.addAll(ban_commands);
                break;

        }

        for (int i = executions.size() - 1; i >= 0; i--) {

            String execution = executions.get(i);
            String[] executionArgs = execution.split(" ", 2);

            String args = chat.replace(player, this, executionArgs[1], true);

            switch (executionArgs[0].toLowerCase()) {

                case "[message]":

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.isOnline())
                                player.getPlayer().sendMessage(args);

                        }
                    }.runTaskAsynchronously(plugin);

                    break;

                case "[console]":


                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), args);
                        }

                    }.runTaskAsynchronously(plugin);

                    break;

                case "[player]":
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (player.isOnline())
                                Objects.requireNonNull(player.getPlayer()).performCommand(args);
                        }

                    }.runTaskAsynchronously(plugin);

                    break;

                default:
                    break;

            }

        }

    }

    public String getPackageName() {
        return packageName;
    }

    public String getPlaceholder_name() {
        return placeholder_name;
    }

    public String getPlaceholder_details() {
        return placeholder_details;
    }

    public List<String> getAccept_commands() {
        return accept_commands;
    }

    public List<String> getDecline_commands() {
        return decline_commands;

    }

    public List<String> getBan_commands() {
        return ban_commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(packageName, aPackage.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }

    @Override
    public String toString() {
        return "Package{" +
                "packageName='" + packageName + '\'' +
                ", placeholder_name='" + placeholder_name + '\'' +
                ", placeholder_details='" + placeholder_details + '\'' +
                ", accept_commands=" + accept_commands +
                ", decline_commands=" + decline_commands +
                ", ban_commands=" + ban_commands +
                '}';
    }
}
