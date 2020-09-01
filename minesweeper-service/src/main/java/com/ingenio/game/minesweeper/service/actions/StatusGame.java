package com.ingenio.game.minesweeper.service.actions;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.utils.ConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class StatusGame implements GameAction {

    private final GameService gameService;

    @Override
    public GameActionEnum getIdentifier() {
        return GameActionEnum.STATUS;
    }

    @Override
    public Mono<GameInfo> run(MessageAction message) {

        return gameService.getGameById(message.getId())
                .map(gameEntity -> {
                    Long timeDuration = gameEntity.getStatus().equals(GameStatusEnum.IN_PROGRESS.getId()) ?
                            gameEntity.getTimeDurationSeconds()
                                    + (Instant.now().getEpochSecond() - gameEntity.getLastUpdated().getEpochSecond())
                            : gameEntity.getTimeDurationSeconds();
                    return ConverterUtils.toGameInfo(gameEntity, timeDuration);
                });
    }
}
