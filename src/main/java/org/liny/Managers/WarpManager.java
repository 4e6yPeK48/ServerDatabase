package org.liny.Managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.liny.DataPacks.Home;

import java.sql.*;

import static org.liny.Main.*;

public class WarpManager {

    public static void setWarp(@NotNull String player, @NotNull Home warp) {

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("INSERT INTO warps (player_uuid, name, world_name, x, y, z) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, player);
            statement.setString(2, warp.name());
            statement.setString(3, warp.world_name());
            statement.setInt(4, warp.x());
            statement.setInt(5, warp.y());
            statement.setInt(6, warp.z());
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

    public static @Nullable Home getWarp(@NotNull String warpName) {

        Home home = null;
        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("SELECT * FROM warps WHERE name = ?")) {

            statement.setString(1, warpName);

            @NotNull ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                @NotNull String worldName = resultSet.getString("world_name");
                @NotNull Integer x = resultSet.getInt("x");
                @NotNull Integer y = resultSet.getInt("y");
                @NotNull Integer z = resultSet.getInt("z");
                home = new Home(warpName, worldName, x, y, z);

            }

        } catch (@NotNull SQLException ignored) {

        }

        return home;

    }

    public void removeWarp(@NotNull String player, @NotNull String name) {

        try (@NotNull Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             @NotNull PreparedStatement statement = connection.prepareStatement("DELETE FROM warps WHERE player_uuid = ? AND name = ?")) {

            statement.setString(1, player);
            statement.setString(2, name);
            statement.executeUpdate();

        } catch (@NotNull SQLException ignored) {

        }

    }

}
