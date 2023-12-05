package org.liny.Managers;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

import static org.liny.Main.*;

public class RegionManager {

    public static void addRegion(@NotNull UUID player, @NotNull String name) {

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("INSERT INTO regions (player_uuid, name) VALUES (?, ?)")) {

            statement.setString(1, player.toString());
            statement.setString(2, name);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static void removeRegion(@NotNull String name) {

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM regions WHERE name = ?")) {

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static @NotNull LinkedList<String> getAllRegions(UUID player) {

        LinkedList<String> protectionIds = new LinkedList<>();

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT name FROM regions WHERE player_uuid = ?")) {

            statement.setString(1, player.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                protectionIds.add(name);
            }

        } catch (@NotNull SQLException ignored) {

        }

        return protectionIds;

    }

    public static @NotNull Boolean isRegionExists(@NotNull UUID player, @NotNull String name) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM regions WHERE (name, player_uuid) = (?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, player.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (@NotNull SQLException ignored) {

        }

        return false;

    }

}