package com.ingenio.game.minesweeper.utils;

import com.ingenio.game.minesweeper.dto.GameInfo;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TestApiUtils {

    String[][] BOARD = {{"X", "X", "X"}, {"X", "M", "X"}, {"X", "X", "X"}};

    public GameInfo createGameHistory() {

        return GameInfo.builder()
                .dateCreated(Instant.now().getEpochSecond())
                .lastUpdated(Instant.now().getEpochSecond())
                .timeDurationSeconds(300L)
                .build();
    }

    public GameInfo gameStarted() {

        return GameInfo.builder()
                .gameId("asd123")
                .status("IN_PROGRESS")
                .board(BOARD)
                .dateCreated(Instant.now().getEpochSecond())
                .lastUpdated(Instant.now().getEpochSecond())
                .timeDurationSeconds(300L)
                .build();
    }

    public GameInfo gameUpdated(String status) {

        return GameInfo.builder()
                .status(status)
                .board(BOARD)
                .dateCreated(Instant.now().getEpochSecond())
                .lastUpdated(Instant.now().getEpochSecond())
                .timeDurationSeconds(360L)
                .build();
    }
}
