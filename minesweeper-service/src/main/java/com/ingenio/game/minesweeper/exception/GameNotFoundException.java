package com.ingenio.game.minesweeper.exception;

public class GameNotFoundException extends BaseException {

    public GameNotFoundException() {
        super(1004, "Game not found.");
    }
}
