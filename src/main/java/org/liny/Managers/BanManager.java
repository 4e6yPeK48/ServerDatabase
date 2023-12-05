package org.liny.Managers;

import org.jetbrains.annotations.Nullable;
import org.liny.DataPacks.BanData;

import java.sql.*;

import org.jetbrains.annotations.NotNull;

import static org.liny.Main.*;

public class BanManager {

    public static void addBan(@NotNull String message, @NotNull String date, @NotNull String playerIP, @NotNull String who, @NotNull String nickname) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("INSERT INTO bans (message, date, player_ip, who, nickname) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, message);
            statement.setString(2, date);
            statement.setString(3, playerIP);
            statement.setString(4, who);
            statement.setString(5, nickname);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }

    public static @NotNull Boolean removeBan(@NotNull String nickname) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM bans WHERE nickname = ?")) {
            statement.setString(1, nickname);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (@NotNull SQLException ignored) {

        }
        return false;
    }


    public static @NotNull Boolean isPlayerBanned(@NotNull String playerIP) {

        boolean banExists = false;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT id FROM bans WHERE player_ip = ?")) {
            statement.setString(1, playerIP);
            ResultSet resultSet = statement.executeQuery();
            banExists = resultSet.next();

        } catch (@NotNull SQLException ignored) {

        }

        return banExists;
    }

    public static @Nullable BanData getBanDataL1(@NotNull String playerIP) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT id, message, date, player_ip, who, nickname FROM bans WHERE player_ip = ?")) {
            statement.setString(1, playerIP);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new BanData(resultSet.getString("date"),
                        resultSet.getString("who"),
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("message"),
                        resultSet.getString("nickname"));

            }

        } catch (@NotNull SQLException ignored) {

        }

        return null;
    }

    public static @Nullable BanData getBanDataL2(@NotNull String playerNickname) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT id, message, date, player_ip, who, nickname FROM bans WHERE nickname = ?")) {
            statement.setString(1, playerNickname);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new BanData(resultSet.getString("date"), resultSet.getString("who"), String.valueOf(resultSet.getInt("id")), resultSet.getString("message"), resultSet.getString("nickname"));

            }

        } catch (@NotNull SQLException ignored) {

        }

        return null;

    }

}