package org.liny.DataPacks;

import org.jetbrains.annotations.NotNull;

public class Home {

    @NotNull public final String name;
    @NotNull public final String world_name;
    @NotNull public final Integer x;
    @NotNull public final Integer y;
    @NotNull public final Integer z;

    public Home(@NotNull String name, @NotNull String world_name, @NotNull Integer x, @NotNull Integer y, @NotNull Integer z) {

        this.name = name;
        this.world_name = world_name;
        this.x = x;
        this.y = y;
        this.z = z;

    }

}
