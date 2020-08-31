package com.ingenio.game.minesweeper.exception;

public class UserNotFountException extends BaseException {

    public UserNotFountException(Throwable cause) {
        super(cause, 1004, "User not found.");
    }
}
