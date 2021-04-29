package com.github.irvinglink.ClashDonations.monitors;

import com.github.irvinglink.ClashDonations.loaders.ILoader;
import com.github.irvinglink.ClashDonations.loaders.PackageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LoaderMonitor {

    private final List<ILoader> loaderList = Collections.synchronizedList(new ArrayList<>());

    public LoaderMonitor() {
        register();
        load();
    }

    private void register() {
        loaderList.add(new PackageLoader());
    }

    private void load() {
        loaderList.forEach(ILoader::load);
    }

    public void update(){
        loaderList.forEach(ILoader::update);
    }

}
