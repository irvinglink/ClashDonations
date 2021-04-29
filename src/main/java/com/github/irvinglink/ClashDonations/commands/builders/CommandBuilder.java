package com.github.irvinglink.ClashDonations.commands.builders;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import com.github.irvinglink.ClashDonations.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class CommandBuilder implements CommandExecutor {

    protected final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);
    protected final Chat chat = this.plugin.getChat();

    private final String cmdName;
    private final String permission;

    private final boolean console;

    private final String prefix = this.chat.getPrefix();

    public CommandBuilder(String cmdName, String permission, boolean console) {
        this.cmdName = cmdName;
        this.permission = permission;
        this.console = console;

        Objects.requireNonNull(this.plugin.getCommand(cmdName)).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!cmd.getLabel().equalsIgnoreCase(this.cmdName))
            return true;

        if (!sender.hasPermission(this.permission)  && !permission.equalsIgnoreCase("")) {
            sender.sendMessage(this.chat.replace((Player)sender, this.plugin.getLang().getString("No_Permission"), true));
            return true;
        }

        StringBuilder x = new StringBuilder();
        x.append(this.prefix);
        x.append(" ");
        if (!this.console && !(sender instanceof Player)) {
            x.append("&aOnly players can use this command");
            sender.sendMessage(this.chat.str(x.toString()));
            return true;
        }

        execute(sender, args);
        return false;
    }

    public Chat getChat() {
        return this.chat;
    }

    protected abstract void execute(CommandSender sender, String[] args);

}