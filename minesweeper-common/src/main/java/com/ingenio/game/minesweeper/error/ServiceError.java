package com.ingenio.game.minesweeper.error;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ServiceError {

    @NonNull
    Integer code;

    @NonNull
    String message;
}
