package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.liny.ConnectionManager;
import org.liny.DataPacks.Home;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.liny.Main.*;

public class HomeManager {


    public static void setHome(@NotNull UUID player, @NotNull Home home) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("INSERT INTO homes (player_uuid, name, world_name, x, y, z) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, player.toString());
            statement.setString(2, home.name());
            statement.setString(3, home.world_name());
            statement.setInt(4, home.x());
            statement.setInt(5, home.y());
            statement.setInt(6, home.z());
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }

    public static @Nullable Home getHome(@NotNull UUID player, @NotNull String homeName) {

        Home home = null;

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT * FROM homes WHERE player_uuid = ? AND name = ?")) {

            statement.setString(1, player.toString());
            statement.setString(2, homeName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String worldName = resultSet.getString("world_name");

                Integer x = resultSet.getInt("x");
                Integer y = resultSet.getInt("y");
                Integer z = resultSet.getInt("z");

                home = new Home(homeName, worldName, x, y, z);

            }

        } catch (@NotNull SQLException ignored) {

        }

        return home;
    }

    public static @NotNull List<Home> getAllHomes(@NotNull UUID player) {
        List<Home> homes = new ArrayList<>();
        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("SELECT * FROM homes WHERE player_uuid = ?")) {

            statement.setString(1, player.toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String homeName = resultSet.getString("name");
                String worldName = resultSet.getString("world_name");

                Integer x = resultSet.getInt("x");
                Integer y = resultSet.getInt("y");
                Integer z = resultSet.getInt("z");

                homes.add(new Home(homeName, worldName, x, y, z));

            }
        } catch (@NotNull SQLException ignored) {

        }

        return homes;
    }

    public static void removeHome(@NotNull UUID player, @NotNull String name) {

        try (@NotNull PreparedStatement statement = ConnectionManager.getConnection().prepareStatement("DELETE FROM homes WHERE player_uuid = ? AND name = ?")) {

            statement.setString(1, player.toString());
            statement.setString(2, name);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }
    }
}