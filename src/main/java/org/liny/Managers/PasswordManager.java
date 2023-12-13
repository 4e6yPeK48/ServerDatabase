package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

import static org.liny.Main.*;

public class PasswordManager {

    public static @Nullable String getPassword(@NotNull String player_name) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT password FROM passwords WHERE player_name = ?")) {

            statement.setString(1, player_name);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("password");
                }

            }

        } catch (@NotNull SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setPassword(@NotNull String player_name, @NotNull String pass) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement existStatement = connection.prepareStatement("SELECT 1 FROM passwords WHERE player_name = ?");
             PreparedStatement updateStatement = connection.prepareStatement("UPDATE passwords SET password = ? WHERE player_name = ?")) {

            existStatement.setString(1, player_name);

            try (ResultSet existResultSet = existStatement.executeQuery()) {
                if (existResultSet.next()) {
                    // Пользователь существует, обновим его пароль
                    updateStatement.setString(1, pass);
                    updateStatement.setString(2, player_name);
                    updateStatement.executeUpdate();
                }
            }

        } catch (SQLException ignored) {
            // Обработка исключений, если необходимо
        }
    }


    public static void removePassword(@NotNull String player_name) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM passwords WHERE player_name = ?")) {

            statement.setString(1, player_name.toString());

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
