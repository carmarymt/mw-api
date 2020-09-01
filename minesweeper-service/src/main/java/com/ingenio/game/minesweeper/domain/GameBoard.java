package com.ingenio.game.minesweeper.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameBoard {

    String[][] field;

    Integer status;

    Integer minesLeft;
}
