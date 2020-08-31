package com.ingenio.game.minesweeper.exception;

public class GameException extends BaseException {

    public GameException(Throwable cause) {
        super(cause, 1003, "Unable to persist user.");
    }
}
