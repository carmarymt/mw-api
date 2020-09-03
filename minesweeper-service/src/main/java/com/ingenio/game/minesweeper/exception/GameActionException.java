package com.ingenio.game.minesweeper.exception;

public class GameActionException extends BaseException {

    public GameActionException() {
        super(1005, "Unable to make the action requested.");
    }
}
