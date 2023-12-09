package org.liny.DataPacks;

import org.jetbrains.annotations.NotNull;

public record BanData(@NotNull String data, @NotNull String who, @NotNull String id, @NotNull String message,
                      @NotNull String nickname) {

    public @NotNull String getData() {
        return data;
    }

    public @NotNull String getWho() {
        return who;
    }

    public @NotNull String getid() {
        return id;
    }

    public @NotNull String getMessage() {
        return message;
    }

    public @NotNull String getNickname() {
        return nickname;
    }

}
