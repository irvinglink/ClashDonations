package com.github.irvinglink.ClashDonations.loaders;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;

public interface ILoader {

    ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    void load();

    void update();

}
