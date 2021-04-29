package com.github.irvinglink.ClashDonations.commands;

import com.github.irvinglink.ClashDonations.commands.builders.CommandBuilder;
import com.github.irvinglink.ClashDonations.handler.PackageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StoreAdminCommand extends CommandBuilder implements TabCompleter {

    private final List<String> subCommands = Collections.synchronizedList(new ArrayList<>());

    public StoreAdminCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
        subCommands.add("add");
        subCommands.add("ban");
        subCommands.add("resetData");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            return;
        }

        if (args.length == 1) {

            // TEST
            return;
        }

        if (args.length == 2) {

            return;
        }

        if (args.length == 3) {

            return;
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
