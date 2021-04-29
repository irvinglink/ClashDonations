package com.github.irvinglink.ClashDonations.utils;

import org.bukkit.OfflinePlayer;

public interface IReplacementHook {

    String replace(OfflinePlayer player, OfflinePlayer target, String str,  String var);

}
