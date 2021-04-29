package com.github.irvinglink.ClashDonations.commands;

import com.github.irvinglink.ClashDonations.commands.builders.CommandBuilder;
import com.github.irvinglink.ClashDonations.handler.PackageHandler;
import com.github.irvinglink.ClashDonations.models.UserData;
import com.github.irvinglink.ClashDonations.utils.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public final class StoreAdminCommand extends CommandBuilder implements TabCompleter {

    private final List<String> subCommands = Collections.synchronizedList(new ArrayList<>());

    public StoreAdminCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
        subCommands.add("add");
        subCommands.add("ban");
        subCommands.add("resetData");
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

                if (args.length == 3) {

                    String targetName = args[1];
                    UUID targetUUID = UUIDUtils.getOfflineUUID(targetName);

                    String packageName = args[2];

                    if (!PackageHandler.containsPackage(packageName)) {
                        sender.sendMessage("Package no exists");
                        return;
                    }

                    plugin.getDatabaseManager().registerPlayer(targetUUID, Objects.requireNonNull(PackageHandler.getPackage(packageName)), System.currentTimeMillis());
                    return;

                } else
                    sender.sendMessage("Wrong Syntax");

                return;

            case "ban":

                return;
            case "resetdata":

                return;
            default:
                break;
        }


    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> output = Collections.synchronizedList(new ArrayList<>());

        if (args.length == 1) {

            List<String> subCommandStrList = subCommands;

            if (!(args[0].isEmpty())) {

                subCommandStrList.forEach(x -> {
                    if (x.toLowerCase().startsWith((args[0].toLowerCase()))) output.add(x);
                });

            } else return subCommandStrList;

        }

        if ((args.length == 2 && args[0].equalsIgnoreCase("resetData")) ||
                (args.length == 3 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("ban"))))
            return new ArrayList<>(PackageHandler.getPackages().keySet());


        return output;

    }
}
