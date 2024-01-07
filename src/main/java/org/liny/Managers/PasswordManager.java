package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.liny.ConnectionManager;

import java.sql.*;

import static org.liny.Main.*;

public class PasswordManager {

    public static @Nullable String getPassword(@NotNull String player_name) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT password FROM passwords WHERE player_name = ?")) {

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
        try (@NotNull PreparedStatement existStatement = ConnectionManager.getConnection().prepareStatement("SELECT 1 FROM passwords WHERE player_name = ?");
             @NotNull PreparedStatement updateStatement = ConnectionManager.getConnection().prepareStatement("UPDATE passwords SET password = ? WHERE player_name = ?")) {

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

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("DELETE FROM passwords WHERE player_name = ?")) {

            statement.setString(1, player_name.toString());

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static void addPassword(String playerName) {

        try (@NotNull PreparedStatement existStatement = ConnectionManager.getConnection().prepareStatement("SELECT * FROM passwords WHERE player_name = ?");
             @NotNull PreparedStatement insertStatement = ConnectionManager.getConnection().prepareStatement("INSERT INTO passwords (player_name, password) VALUES (?, NULL)")) {

            existStatement.setString(1, playerName);
            ResultSet existResultSet = existStatement.executeQuery();

            if (existResultSet.next()) return;

            insertStatement.setString(1, playerName);
            insertStatement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

}
