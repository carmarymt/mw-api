package com.ingenio.game.minesweeper.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum GameActionEnum {

    START(1),
    PLAY(2),
    PAUSE(3),
    RESUME(4),
    STATUS(5);

    private static final Map<Integer, GameActionEnum> OPERATION_MAP = new HashMap<>();

    private final Integer id;

    static {
        for (var gameOperation : GameActionEnum.values()) {
            OPERATION_MAP.put(gameOperation.getId(), gameOperation);
        }
    }

    public static GameActionEnum get(Integer type) {
        return OPERATION_MAP.get(type);
    }
}
