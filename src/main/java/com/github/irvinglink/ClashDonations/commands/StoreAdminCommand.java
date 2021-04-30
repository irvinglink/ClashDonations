package com.github.irvinglink.ClashDonations.commands;

import com.github.irvinglink.ClashDonations.commands.builders.CommandBuilder;
import com.github.irvinglink.ClashDonations.database.DatabaseManager;
import com.github.irvinglink.ClashDonations.handler.PackageHandler;
import com.github.irvinglink.ClashDonations.models.PackageAction;
import com.github.irvinglink.ClashDonations.models.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public final class StoreAdminCommand extends CommandBuilder implements TabCompleter {

    private final DatabaseManager databaseManager = plugin.getDatabaseManager();

    private final Map<String, String> subCommands = Collections.synchronizedMap(new HashMap<>());

    public StoreAdminCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
        subCommands.put("add", "/storeadmin add <player> <package>");
        subCommands.put("ban", "/storeadmin ban <player> <package>");
        subCommands.put("resetData", "/storeadmin resetData <package>");
        subCommands.put("reload", "/storeadmin reload");
    }

    /*
    /storeadmin ban %player% %package% (clashstore.ban) - bans a player if they accepted the package that the command was executed for
    /storeadmin add %player% %package% (clashstore.add) - gives the player a package to claim
    /storeadmin resetdata %package% (clashstore.reset) - resets all the accepted data for the packages players have claimed.
     */

    @Override
    protected void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            return;
        }

        switch (args[0].toLowerCase()) {

            case "add":

                if (!sender.hasPermission("clashstore.add")) {
                    sender.sendMessage(chat.replace(plugin.getLang().getString("No_Permission"), true));
                    return;
                }

                if (args.length == 3) {

                    String targetName = args[1];
                    OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetName);

                    UUID targetUUID = targetPlayer.getUniqueId();

                    String packageName = args[2];

                    if (!PackageHandler.containsPackage(packageName)) {
                        sender.sendMessage(chat.replace(plugin.getLang().getString("Package_No_Exists"), true));
                        return;
                    }

                    databaseManager.registerPlayer(targetUUID, Objects.requireNonNull(PackageHandler.getPackage(packageName)), System.currentTimeMillis());
                    sender.sendMessage(chat.replace(null, targetPlayer, plugin.getLang().getString("Added_Player"), true));
                    return;

                } else
                    sender.sendMessage(chat.replace(subCommands.get("add"), plugin.getLang().getString("Wrong_Usage"), true));

                return;

            case "ban":

                if (!sender.hasPermission("clashstore.ban")) {
                    sender.sendMessage(chat.replace(plugin.getLang().getString("No_Permission"), true));
                    return;
                }

                if (args.length == 3) {

                    String targetName = args[1];
                    OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetName);

                    UUID targetUUID = targetPlayer.getUniqueId();

                    String packageName = args[2];

                    if (!PackageHandler.containsPackage(packageName)) {
                        sender.sendMessage(chat.replace(plugin.getLang().getString("Package_No_Exists"), true));
                        return;
                    }

                    if (!databaseManager.isRegistered(targetUUID)) {
                        sender.sendMessage(chat.replace(null, targetPlayer, plugin.getLang().getString("Player_No_Registered"), true));
                        return;
                    }

                    for (UserData userData : databaseManager.getPlayer(targetUUID))
                        if (userData.getPackage().getPackageName().equalsIgnoreCase(packageName)) {
                            userData.getPackage().execute(targetPlayer, PackageAction.BAN);
                            sender.sendMessage(chat.replace(null, targetPlayer, plugin.getLang().getString("Banned_Player"), true));
                            return;
                        }


                }

                return;

            case "resetdata":

                if (!sender.hasPermission("clashstore.reset")) {
                    sender.sendMessage(chat.replace(plugin.getLang().getString("No_Permission"), true));
                    return;
                }

                if (args.length == 2) {

                    String packageName = args[1];

                    if (!PackageHandler.containsPackage(packageName)) {
                        sender.sendMessage(chat.replace(plugin.getLang().getString("Package_No_Exists"), true));
                        return;
                    }

                    databaseManager.deleteFromTable("PACKAGE", packageName);
                    sender.sendMessage(chat.replace(PackageHandler.getPackage(packageName), plugin.getLang().getString("Reset_Data"), true));

                    return;
                }

                return;

            case "reload":

                if (!sender.hasPermission("clashstore.reload")) {
                    sender.sendMessage(chat.replace(plugin.getLang().getString("No_Permission"), true));
                    return;
                }

                if (args.length == 1) {

                    plugin.reloadConfig();

                    // UPDATE PACKAGES
                    plugin.getLoader().update();

                    sender.sendMessage(chat.replace(plugin.getLang().getString("Reload_Files"), true));
                    return;

                } else
                    sender.sendMessage(chat.replace(subCommands.get("reload"), plugin.getLang().getString("Wrong_Usage"), true));

                return;

            default:
                break;
        }


    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> output = Collections.synchronizedList(new ArrayList<>());

        if (args.length == 1) {

            List<String> subCommandStrList = new ArrayList<>(subCommands.keySet());

            if (!(args[0].isEmpty())) {

                subCommandStrList.forEach(x -> {
                    if (x.toLowerCase().startsWith((args[0].toLowerCase()))) output.add(x);
                });

            } else return subCommandStrList;

        }

        if ((args.length == 2 && args[0].equalsIgnoreCase("resetData")) ||
                (args.length == 3 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("ban"))))

            return new ArrayList<>(PackageHandler.getPackages().keySet());

        else return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());


    }
}
