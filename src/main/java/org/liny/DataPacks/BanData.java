package org.liny.DataPacks;

import org.jetbrains.annotations.NotNull;

public class BanData {

    @NotNull public final String data;
    @NotNull public final String who;
    @NotNull public final String id;
    @NotNull public final String message;
    @NotNull public final String nickname;

    public BanData(@NotNull String data, @NotNull String who, @NotNull String id, @NotNull String message, @NotNull String nickname) {

        this.data = data;
        this.who = who;
        this.id = id;
        this.message = message;
        this.nickname = nickname;

    }

}
