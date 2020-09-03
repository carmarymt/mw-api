package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.exception.BoardOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService implements BoardOperation {

    public static final String CELL_MINE = "M";
    public static final String CELL_UNREVEALED = "X";
    public static final String CELL_FLAG_MARK = "?";
    public static final int MINE_VALUE = -1;
    public static final int INITIAL_VALUE = 0;

    @Override
    public GameBoard init(final GameRequest game) {

        var nRows = game.getNumberRows();
        var nColumns = game.getNumberColumns();
        var nMines = game.getNumberMines();

        if (nRows == 0 || nColumns == 0 || game.getNumberMines() > (nRows * nColumns))
            throw new BoardOperationException();

        return GameBoard.builder()
                .status(GameStatusEnum.IN_PROGRESS)
                .numberRows(nRows)
                .numberColumns(nColumns)
                .board(boardInitialization(nRows, nColumns))
                .originalBoard(originalBoardInitialization(nRows, nColumns, nMines))
                .build();
    }

    @Override
    public GameBoard move(final UserMoveRequest move, final GameBoard gameBoard) {

        var row = move.getRow();
        var col = move.getColumn();
        var board = Arrays.stream(gameBoard.getBoard()).map(String[]::clone).toArray(String[][]::new);
        var originalBoard = gameBoard.getOriginalBoard();
        var gameStatusEnum = gameBoard.getStatus();

        if (row < 0 || row >= gameBoard.getNumberRows()
                || col < 0 || col >= gameBoard.getNumberColumns())
            throw new BoardOperationException();

        if (board[row][col].equals(CELL_UNREVEALED)) {
            if (move.isFlag()) {
                board[row][col] = CELL_FLAG_MARK;
            } else {
                if (originalBoard[row][col] == MINE_VALUE) {
                    board[row][col] = CELL_MINE;
                    gameStatusEnum = GameStatusEnum.GAME_OVER;
                } else {
                    var nRows = gameBoard.getNumberRows();
                    var nColumns = gameBoard.getNumberColumns();
                    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                    depthFirstSearch(originalBoard, board, row, col, nRows, nColumns, dirs);
                }
            }
        }

        return GameBoard.builder()
                .status(gameStatusEnum)
                .numberRows(gameBoard.getNumberRows())
                .numberColumns(gameBoard.getNumberColumns())
                .board(board)
                .build();
    }

    private void depthFirstSearch(int[][] originalBoard, String[][] board, int row, int col, int nRows, int nColumns, int[][] dirs) {

        if (row < 0 || row >= nRows || col < 0 || col >= nColumns || !board[row][col].equals(CELL_UNREVEALED)) return;

        board[row][col] = String.valueOf(originalBoard[row][col]);
        if (originalBoard[row][col] == 0) {
            for (int[] d : dirs) {
                depthFirstSearch(originalBoard, board, row + d[0], col + d[1], nRows, nColumns, dirs);
            }
        }
    }

    private String[][] boardInitialization(int nRows, int nColumns) {

        var initBoard = new String[nRows][nColumns];

        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nColumns; ++j) {
                initBoard[i][j] = CELL_UNREVEALED;
            }
        }
        return initBoard;
    }

    private int[][] originalBoardInitialization(int nRows, int nColumns, int numberMines) {

        var totalMines = numberMines;
        var initBoard = new int[nRows][nColumns];

        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nColumns; ++j) {
                initBoard[i][j] = INITIAL_VALUE;
            }
        }

        while (totalMines > 0) {
            var randomPosition = Math.round(Math.random() * ((nRows * nColumns) - 1));
            var row = (int) Math.floor(randomPosition / nColumns);
            var col = (int) randomPosition % nColumns;

            if (initBoard[row][col] != MINE_VALUE) {
                initBoard[row][col] = MINE_VALUE;
                updateAdjacentCellContMines(initBoard, row, col, nRows, nColumns);
                totalMines--;
            }
        }
        return initBoard;
    }

    private void updateAdjacentCellContMines(int[][] originalBoard, int row, int col, int nRows, int nColumns) {

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (0 <= i && i < nRows && 0 <= j && j < nColumns && originalBoard[i][j] != MINE_VALUE)
                    originalBoard[i][j] = originalBoard[i][j] + 1;
            }
        }
    }

}
