package com.github.irvinglink.ClashDonations.utils.chat;

import com.github.irvinglink.ClashDonations.models.Package;
import org.bukkit.OfflinePlayer;

public interface IReplacementHook {

    String replace(OfflinePlayer player, OfflinePlayer target, Package pack, String str, String var);

}
