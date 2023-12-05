package org.liny.Managers;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.liny.Main.*;

public class CaseManager {

    public static void addCase(@NotNull String name, @NotNull String type) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("INSERT INTO cases (player_name, case_type) VALUES (?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, type);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }

    public static @NotNull List<String> getAllCases(@NotNull String playerName) {

        List<String> caseTypes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT case_type FROM cases WHERE player_name = ?")) {
            statement.setString(1, playerName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String caseType = resultSet.getString("case_type");
                    caseTypes.add(caseType);
                }
            }
        } catch (@NotNull SQLException ignored) {

        }

        return caseTypes;
    }


    public static void removeCase(@NotNull String name, @NotNull String type) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM cases WHERE player_name = ? AND case_type = ? LIMIT 1")) {
            statement.setString(1, name);
            statement.setString(2, type);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }

}