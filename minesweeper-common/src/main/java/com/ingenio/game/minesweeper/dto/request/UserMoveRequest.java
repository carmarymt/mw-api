package com.ingenio.game.minesweeper.dto.request;

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

    @Builder.Default
    boolean isFlag = false;
}
