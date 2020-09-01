package com.ingenio.game.minesweeper.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserInfo {

    @NonNull
    Long userId;

    @NonNull
    String userName;

    @NonNull
    String name;

    String surname;

    @NonNull
    String email;

    @NonNull
    Long dateCreated;

}
