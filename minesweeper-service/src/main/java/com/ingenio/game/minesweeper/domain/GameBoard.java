package com.ingenio.game.minesweeper.domain;

import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder()
public class GameBoard {

    GameStatusEnum status;

    int numberRows;

    int numberColumns;

    String[][] board;

    int[][] originalBoard;
}
