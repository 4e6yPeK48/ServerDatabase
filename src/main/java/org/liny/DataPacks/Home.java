package org.liny.DataPacks;

import org.jetbrains.annotations.NotNull;

@Deprecated
public record Home(@NotNull String name, @NotNull String world_name, @NotNull Integer x, @NotNull Integer y,
                   @NotNull Integer z) {
}
