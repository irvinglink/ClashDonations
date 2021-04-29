package com.github.irvinglink.ClashDonations.utils.chat;

import org.bukkit.OfflinePlayer;

public interface IReplacementHook {

    String replace(OfflinePlayer player, OfflinePlayer target, String str,  String var);

}
