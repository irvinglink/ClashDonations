package com.github.irvinglink.ClashDonations.handler;

import com.github.irvinglink.ClashDonations.models.Package;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PackageHandler {

    private static final Map<String, Package> packages = Collections.synchronizedMap(new HashMap<>());

    public static void addPackage(Package pack) {
        packages.put(pack.getPackageName().toLowerCase(), pack);
    }

    public static Package getPackage(String packageName) {
        if (packages.containsKey(packageName.toLowerCase())) return packages.get(packageName.toLowerCase());
        return null;
    }

    public static void removePackage(Package pack) {
        packages.remove(pack.getPackageName().toLowerCase());
    }

    public static Map<String, Package> getPackages() {
        return packages;
    }

    public static boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }
}
