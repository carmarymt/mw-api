package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.utils.TestApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService implements BoardOperation {

    @Override
    public GameBoard init(GameRequest gameRequest) {

        return GameBoard.builder()
                .field(TestApiUtils.BOARD)
                .status(GameStatusEnum.IN_PROGRESS.getId())
                .minesLeft(gameRequest.getNumberMines())
                .build();
    }

    @Override
    public GameBoard move(UserMoveRequest userMoveRequest, GameBoard gameBoard) {

        return GameBoard.builder()
                .field(TestApiUtils.BOARD_MOVE)
                .status(GameStatusEnum.IN_PROGRESS.getId())
                .minesLeft(12)
                .build();
    }
}
