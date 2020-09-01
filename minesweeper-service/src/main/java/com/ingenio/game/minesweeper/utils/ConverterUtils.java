package com.ingenio.game.minesweeper.utils;

import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.entity.GameEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConverterUtils {

    public GameInfo toGameInfo(final GameEntity gameEntity) {

        return GameInfo.builder()
                .gameId(gameEntity.getGameId())
                .status(GameStatusEnum.get(gameEntity.getStatus()).getStatusName())
                .board(JsonUtils.convert(gameEntity.getBoard(), String[][].class))
                .dateCreated(gameEntity.getDateCreated().getEpochSecond())
                .lastUpdated(gameEntity.getLastUpdated().getEpochSecond())
                .timeDurationSeconds(gameEntity.getTimeDurationSeconds())
                .build();
    }

    public GameInfo toGameInfo(final GameEntity gameEntity, final Long timeDurationSeconds) {

        return GameInfo.builder()
                .gameId(gameEntity.getGameId())
                .status(GameStatusEnum.get(gameEntity.getStatus()).getStatusName())
                .board(JsonUtils.convert(gameEntity.getBoard(), String[][].class))
                .dateCreated(gameEntity.getDateCreated().getEpochSecond())
                .lastUpdated(gameEntity.getLastUpdated().getEpochSecond())
                .timeDurationSeconds(timeDurationSeconds)
                .build();
    }

}
