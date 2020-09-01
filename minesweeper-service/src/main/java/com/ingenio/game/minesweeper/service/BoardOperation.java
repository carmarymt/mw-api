package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;

public interface BoardOperation {

    GameBoard init(GameRequest gameRequest);

    GameBoard move(UserMoveRequest userMoveRequest, GameBoard gameBoard);
}
