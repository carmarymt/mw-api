package com.ingenio.game.minesweeper.utils;

import com.ingenio.game.minesweeper.domain.GameInfo;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TestApiUtils {

    public String[][] BOARD = {{"X", "X", "X"}, {"X", "M", "X"}, {"X", "X", "X"}};
    public String[][] BOARD_MOVE = {{"C", "C", "C"}, {"M", "M", "M"}, {"C", "C", "C"}};

    public GameInfo createGameHistory() {

        return GameInfo.builder()
                .dateCreated(Instant.now().getEpochSecond())
                .lastUpdated(Instant.now().getEpochSecond())
                .timeDurationSeconds(300L)
                .build();
    }

}
