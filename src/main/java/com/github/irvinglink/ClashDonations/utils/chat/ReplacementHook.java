package com.github.irvinglink.ClashDonations.utils.chat;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import org.bukkit.OfflinePlayer;

public final class ReplacementHook implements IReplacementHook {

    private final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    private final Chat chat = this.plugin.getChat();

    public String replace(OfflinePlayer player, OfflinePlayer target, String str, String var) {

        switch (var) {
            case "prefix":
                return this.chat.getPrefix();

            case "str":
            case "command_syntax":
            case "message":
            case "msg":
                return str;

            case "player":
                if (player != null) return player.getName();

            case "player_displayName":
            case "player_displayname":
                if (player != null) return player.getPlayer().getDisplayName();

            case "target":
                if (target != null) return target.getName();

            default:
                return null;

        }

    }


}
