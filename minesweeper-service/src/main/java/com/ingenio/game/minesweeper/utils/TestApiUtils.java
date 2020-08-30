package com.ingenio.game.minesweeper.utils;

import com.ingenio.game.minesweeper.dto.GameInfo;
import com.ingenio.game.minesweeper.dto.UserInfo;
import com.ingenio.game.minesweeper.dto.request.UserRequest;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TestApiUtils {

    String[][] BOARD = {{"X", "X", "X"}, {"X", "M", "X"}, {"X", "X", "X"}};

    public UserInfo createNewUser(Long userId) {

        return UserInfo.builder()
                .userId(userId)
                .userName("userName1")
                .name("name1")
                .surname("surname1")
                .email("user1@gmail")
                .dateCreated(Instant.now().getEpochSecond()).build();
    }

    public UserInfo createUser(UserRequest user) {

        return UserInfo.builder()
                .userId(1L)
                .userName(user.getUserName())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getName())
                .dateCreated(Instant.now().getEpochSecond()).build();
    }

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
