package com.github.irvinglink.ClashDonations.database;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import com.github.irvinglink.ClashDonations.handler.PackageHandler;
import com.github.irvinglink.ClashDonations.models.Package;
import com.github.irvinglink.ClashDonations.models.UserData;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class DatabaseManager {

    protected final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    protected Connection conn;

    public abstract void setup(String host, String database, String username, String password);

    public Connection getConnection() {
        return this.conn;
    }

    public synchronized void createTables() {

        try {

            PreparedStatement stmt2 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerData(UUID VARCHAR(100), NAME VARCHAR(40), PACKAGE VARCHAR(100), DATE LONG, BANNED BOOLEAN)");
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized List<UserData> getPlayer() {

        List<UserData> output = Collections.synchronizedList(new ArrayList<>());

        try {

            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO playerData(UUID, NAME, PACKAGE, DATE, BANNED) VALUES(?,?,?,?,?)");

            ResultSet resultSet = stmt.executeQuery();

            do {

                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                String playerName = resultSet.getString("NAME");

                Package pack = PackageHandler.getPackage(resultSet.getString("PACKAGE"));

                long date = resultSet.getLong("DATE");
                boolean banned = resultSet.getBoolean("BANNED");


                output.add(new UserData(uuid, playerName, pack, date, banned));

            } while (resultSet.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    public synchronized void registerPlayer(UUID uuid, Package pack, Long millis) {

        try {

            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO playerData(UUID, NAME, PACKAGE, DATE, BANNED) VALUES(?,?,?,?,?)");

            stmt.setString(1, uuid.toString());
            stmt.setString(2, Bukkit.getOfflinePlayer(uuid).getName());
            stmt.setString(3, pack.getPackageName());
            stmt.setLong(4, millis);
            stmt.setBoolean(5, Boolean.FALSE);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void removePlayer(UUID uuid) {

        try {

            PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM playerData WHERE UUID=?");

            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized boolean isRegistered(UUID uuid) {

        try {
            PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID=?");
            stmt.setString(1, uuid.toString());

            ResultSet result = stmt.executeQuery();

            if (result.next()) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public synchronized long getDate(UUID uuid) {

        try {

            PreparedStatement stmt = getConnection().prepareStatement("SELECT DATE FROM playerData WHERE UUID=?");
            stmt.setString(1, uuid.toString());

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();

            return resultSet.getLong("DATE");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }


}
