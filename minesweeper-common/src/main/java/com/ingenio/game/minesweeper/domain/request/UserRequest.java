package com.ingenio.game.minesweeper.domain.request;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserRequest {

    @NonNull
    String userName;

    @NonNull
    String name;

    String surname;

    @NonNull
    String email;
}
