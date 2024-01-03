package org.liny.DataPacks;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public record Point(@NotNull String name, @NotNull String worldName, @NotNull Integer x, @NotNull Integer y, @NotNull Integer z) {
    public static @NotNull Point fromLocation (@NotNull Location xyz, @NotNull String name) {
        return new Point(name, xyz.getWorld().getName(), xyz.getBlockX(), xyz.getBlockY(), xyz.getBlockZ());
    }

    @Deprecated
    public static @NotNull Point fromHome (@NotNull Home xyz, @NotNull String name) {
        return new Point(name, xyz.world_name(), xyz.x(), xyz.y(), xyz.z());
    }
}
