package com.ingenio.game.minesweeper.exception;

public class UserException extends BaseException {

    public UserException(Throwable cause) {
        super(cause, 1001, "Unable to persist user.");
    }
}
