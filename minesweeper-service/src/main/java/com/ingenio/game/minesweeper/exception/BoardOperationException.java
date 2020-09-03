package com.ingenio.game.minesweeper.exception;

public class BoardOperationException extends BaseException {

    public BoardOperationException() {
        super(1006, "Unable to process the operation requested on board.");
    }
}
