package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.liny.ConnectionManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class PlayerManager {

    public static @NotNull Boolean playerExists(@NotNull UUID player) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT * FROM players WHERE uuid = ?")) {

            statement.setString(1, player.toString());

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (@NotNull SQLException ignored) {

        }

        return false;

    }

    public static void addPlayer(@NotNull UUID player, @NotNull String playerName, @NotNull String playerIp) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("INSERT INTO players (uuid, name, ip) VALUES (?, ?, ?)")) {

            statement.setString(1, player.toString());
            statement.setString(2, playerName);
            statement.setString(3, playerIp);

            statement.executeUpdate();

            try (@NotNull PreparedStatement passwordStatement = ConnectionManager.getConnection().prepareStatement("INSERT INTO passwords (player_name) VALUES (?)")) {
                passwordStatement.setString(1, playerName);
                passwordStatement.executeUpdate();
            }

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static @Nullable String getIp(@NotNull String playerUUID) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT ip FROM players WHERE uuid = ?")) {

            statement.setString(1, playerUUID);

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("ip");
                }

            }

        } catch (@NotNull SQLException ignored) {

        }

        return null;

    }

    public static @NotNull LinkedList<String> getAllNames(@NotNull String playerIp) {

        LinkedList<String> playerNames = new LinkedList<>();

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT name FROM players WHERE ip = ?")) {

            statement.setString(1, playerIp);
            @NotNull ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                playerNames.add(resultSet.getString("name"));
            }
        } catch (@NotNull SQLException ignored) {

        }

        return playerNames;

    }

}
