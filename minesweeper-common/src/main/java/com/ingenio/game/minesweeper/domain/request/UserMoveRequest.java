package com.ingenio.game.minesweeper.domain.request;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserMoveRequest {

    @NonNull
    int row;

    @NonNull
    int column;

    @NonNull
    boolean isFlag;
}
