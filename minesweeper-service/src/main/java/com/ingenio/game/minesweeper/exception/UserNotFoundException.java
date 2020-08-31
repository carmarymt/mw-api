package com.ingenio.game.minesweeper.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(1002, "User not found.");
    }
}
