package org.liny.DataPacks;

import org.jetbrains.annotations.NotNull;

public record Clan(@NotNull String name, @NotNull String prefix, @NotNull String owner, @NotNull Integer level) {
}
