package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.exception.BoardOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    public static final String[][] BOARD = {{"X", "X"}, {"X", "X"}};
    public static final int[][] ORIGINAL_BOARD = {{1, 1}, {1, -1}};
    public static final String[][] BOARD_GAME_OVER = {{"X", "X"}, {"X", "M"}};
    public static final String[][] BOARD_MOVE_1 = {{"1", "X"}, {"X", "X"}};
    public static final String[][] BOARD_MOVE_FLAG = {{"X", "?"}, {"X", "X"}};

    public final GameBoard GAME_BOARD = GameBoard.builder()
            .status(GameStatusEnum.IN_PROGRESS)
            .numberRows(2)
            .numberColumns(2)
            .board(BOARD)
            .originalBoard(ORIGINAL_BOARD)
            .build();

    public static final GameBoard GAME_BOARD_GAME_OVER_EXPECTED = GameBoard.builder()
            .status(GameStatusEnum.GAME_OVER)
            .numberRows(2)
            .numberColumns(2)
            .board(BOARD_GAME_OVER)
            .build();

    public static final GameBoard GAME_BOARD_MOVE_1_EXPECTED = GameBoard.builder()
            .status(GameStatusEnum.IN_PROGRESS)
            .numberRows(2)
            .numberColumns(2)
            .board(BOARD_MOVE_1)
            .build();

    public static final GameBoard GAME_BOARD_MOVE_FLAG_EXPECTED = GameBoard.builder()
            .status(GameStatusEnum.IN_PROGRESS)
            .numberRows(2)
            .numberColumns(2)
            .board(BOARD_MOVE_FLAG)
            .build();

    public static final UserMoveRequest USER_MOVE_GAME_OVER = UserMoveRequest.builder()
            .row(1)
            .column(1)
            .isFlag(false)
            .build();

    public static final UserMoveRequest USER_MOVE_1 = UserMoveRequest.builder()
            .row(0)
            .column(0)
            .isFlag(false)
            .build();

    public static final UserMoveRequest USER_MOVE_FLAG = UserMoveRequest.builder()
            .row(0)
            .column(1)
            .isFlag(true)
            .build();

    public static final UserMoveRequest INVALID_USER_MOVE = UserMoveRequest.builder()
            .row(-1)
            .column(0)
            .isFlag(false)
            .build();

    public static final GameRequest INVALID_GAME_REQUEST = GameRequest.builder()
            .numberRows(2)
            .numberColumns(2)
            .numberMines(10)
            .build();

    @InjectMocks
    private BoardService underTest;

    @Test
    public void testMoveBoardOperationGameOverSuccess() {

        var actualGameBoard = underTest.move(USER_MOVE_GAME_OVER, GAME_BOARD);

        Assertions.assertEquals(GAME_BOARD_GAME_OVER_EXPECTED, actualGameBoard);
    }

    @Test
    public void testMoveBoardOperationSuccess() {

        var actualGameBoard = underTest.move(USER_MOVE_1, GAME_BOARD);

        Assertions.assertEquals(GAME_BOARD_MOVE_1_EXPECTED, actualGameBoard);
    }

    @Test
    public void testMoveBoardOperationMoveWithFlagSuccess() {

        var actualGameBoard = underTest.move(USER_MOVE_FLAG, GAME_BOARD);

        Assertions.assertEquals(GAME_BOARD_MOVE_FLAG_EXPECTED, actualGameBoard);
    }

    @Test
    public void testMoveBoardOperationInvalidMoveThrowBoardOperationException() {

        Exception exception = Assertions.assertThrows(BoardOperationException.class, () -> {
            underTest.move(INVALID_USER_MOVE, GAME_BOARD);
        });
    }

    @Test
    public void testInitBoardOperationInvalidGameRequestThrowBoardOperationException() {

        Exception exception = Assertions.assertThrows(BoardOperationException.class, () -> {
            underTest.init(INVALID_GAME_REQUEST);
        });

    }
}
