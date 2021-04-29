package com.github.irvinglink.ClashDonations.commands.builders;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getName();

    String getDescription();

    String getSyntax();

    void perform(CommandSender sender, String[] args);

}
