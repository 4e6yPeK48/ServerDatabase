package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.UUID;

import static org.liny.Main.*;

public class PasswordManager {


    public static @Nullable String getPassword(@NotNull UUID player) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT password FROM passwords WHERE player_name = ?")) {

            statement.setString(1, player.toString());

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("password");
                }

            }

        } catch (@NotNull SQLException ignored) {

        }

        return null;

    }

    public static void setPassword(@NotNull UUID player, @NotNull String pass) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE passwords SET password = ? WHERE player_name = ?")) {

            statement.setString(1, pass);
            statement.setString(2, player.toString());
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static void removePassword(@NotNull UUID player) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("UPDATE passwords SET password = NULL WHERE player_name = ?")) {

            statement.setString(1, player.toString());

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static void addPassword(String playerName) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement existStatement = connection.prepareStatement("SELECT * FROM passwords WHERE player_name = ?");
             @NotNull PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO passwords (player_name, password) VALUES (?, NULL)")) {

            existStatement.setString(1, playerName);
            ResultSet existResultSet = existStatement.executeQuery();

            if (existResultSet.next()) return;

            insertStatement.setString(1, playerName);
            insertStatement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }
}
