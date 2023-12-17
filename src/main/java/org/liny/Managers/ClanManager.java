package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.liny.DataPacks.Clan;

import java.sql.*;

import static org.liny.Main.*;

public class ClanManager {

    /*

    ClanManager.setClanField("MyClan", "level", 5);

    int clanLevel = ClanManager.getClanField("MyClan", "level");

    ClanManager.addClanField("MyClan", "exp", 100);

    */

    public static void setClanField(@NotNull String clanName, @NotNull String fieldName, int value) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement(
                     "UPDATE clans SET " + fieldName + " = ? WHERE name = ?")) {

            statement.setInt(1, value);
            statement.setString(2, clanName);

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {
        }
    }

    public static int getClanField(@NotNull String clanName, @NotNull String fieldName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement(
                     "SELECT " + fieldName + " FROM clans WHERE name = ?")) {

            statement.setString(1, clanName);

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) return resultSet.getInt(fieldName);

            }

        } catch (@NotNull SQLException ignored) {
        }

        return 0;

    }

    public static void addClanField(@NotNull String clanName, @NotNull String fieldName, Integer value) {

        Integer currentValue = getClanField(clanName, fieldName);
        Integer newValue = currentValue + value;

        setClanField(clanName, fieldName, newValue);

    }

    public static @NotNull Boolean clanExists(@NotNull String clanName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT * FROM clans WHERE name = ?")) {

            statement.setString(1, clanName);

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {

                return resultSet.next();

            }

        } catch (@NotNull SQLException ignored) {
        }

        return false;
    }

    public static void createClan(@NotNull Clan clan) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO clans (name, prefix, owner, level, balance, exp) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, clan.name());
            statement.setString(2, clan.prefix());
            statement.setString(3, clan.owner());
            statement.setInt(4, clan.level());
            statement.setInt(5, clan.balance());
            statement.setInt(6, clan.exp());

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {
        }
    }


    public static void removeClan(@NotNull String clanName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM clans WHERE name = ?")) {

            statement.setString(1, clanName);

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {
        }

    }

    public static @Nullable Clan getClan(@NotNull String clanName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT * FROM clans WHERE name = ?")) {

            statement.setString(1, clanName);

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Clan(

                            resultSet.getString("name"),
                            resultSet.getString("prefix"),
                            resultSet.getString("owner"),
                            resultSet.getInt("level"),
                            resultSet.getInt("balance"),
                            resultSet.getInt("exp")

                    );

                }

            }

        } catch (@NotNull SQLException ignored) {
        }

        return null;
    }

    public static @Nullable Clan getClanByPlayer(@NotNull String playerName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE name = ?")) {

            statement.setString(1, playerName);

            try (@NotNull ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String clanName = resultSet.getString("clan_name");

                    if (clanName == null || clanName.isEmpty()) return null;

                    return getClan(clanName);

                }

            }

        } catch (@NotNull SQLException ignored) {
        }

        return null;
    }


}
