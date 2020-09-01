package com.ingenio.game.minesweeper.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum GameStatusEnum {

    IN_PROGRESS(1, "IN_PROGRESS"),
    PAUSED(2, "PAUSED"),
    GAME_OVER(3, "GAME_OVER"),
    FINISHED(4, "FINISHED");

    private static final Map<Integer, GameStatusEnum> GAME_STATUS_ENUM_MAP = new HashMap<>();

    private final Integer id;

    private final String statusName;

    static {
        for (var gameStatusEnum : GameStatusEnum.values()) {
            GAME_STATUS_ENUM_MAP.put(gameStatusEnum.getId(), gameStatusEnum);
        }
    }

    public static GameStatusEnum get(Integer id) {
        return GAME_STATUS_ENUM_MAP.get(id);
    }
}
