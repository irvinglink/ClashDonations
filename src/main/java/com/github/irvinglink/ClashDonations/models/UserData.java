package com.github.irvinglink.ClashDonations.models;

import java.util.Objects;
import java.util.UUID;

public final class UserData {

    private final UUID uuid;
    private final String playerName;
    private final Package pack;
    private final long date;
    private final boolean banned;

    public UserData(UUID uuid, String playerName, Package pack, long date, boolean banned) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.pack = pack;
        this.date = date;
        this.banned = banned;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Package getPack() {
        return pack;
    }

    public long getDate() {
        return date;
    }

    public boolean isBanned() {
        return banned;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "uuid=" + uuid +
                ", playerName='" + playerName + '\'' +
                ", pack=" + pack +
                ", date=" + date +
                ", banned=" + banned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return date == userData.date &&
                banned == userData.banned &&
                Objects.equals(uuid, userData.uuid) &&
                Objects.equals(playerName, userData.playerName) &&
                Objects.equals(pack, userData.pack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, playerName, pack, date, banned);
    }
}
