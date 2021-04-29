package com.github.irvinglink.ClashDonations.commands;

import com.github.irvinglink.ClashDonations.commands.builders.CommandBuilder;
import com.github.irvinglink.ClashDonations.gui.ConfirmGui;
import com.github.irvinglink.ClashDonations.gui.manager.Menu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ConfirmCommand extends CommandBuilder {

    public ConfirmCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            Player player = (Player) sender;
            Menu.openMenu(player, new ConfirmGui());
            player.sendMessage(chat.replace(player, plugin.getLang().getString("Open_Confirm_Gui"), true));

        }

    }

}
