package org.liny.Managers;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.liny.Main.*;

public class SpectatorManager {

    public static void addSpec(@NotNull String object, @NotNull String subject, @NotNull String message, @NotNull String date) {

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("INSERT INTO specs (spectator, subject, message, date) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, object);
            statement.setString(2, subject);
            statement.setString(3, message);
            statement.setString(4, date);

            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

}
