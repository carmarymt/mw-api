package com.ingenio.game.minesweeper.dto.request;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GameRequest {

    @NonNull
    int numberRows;

    @NonNull
    int numberColumns;

    @NonNull
    int numberMines;
}
