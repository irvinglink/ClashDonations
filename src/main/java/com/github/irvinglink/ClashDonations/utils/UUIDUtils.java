package com.github.irvinglink.ClashDonations.utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDUtils {

    public static UUID getOfflineUUID(String name) {
        return UUID.nameUUIDFromBytes(String.format("OfflinePlayer:%s", new Object[]{name}).getBytes(StandardCharsets.UTF_8));
    }

}
