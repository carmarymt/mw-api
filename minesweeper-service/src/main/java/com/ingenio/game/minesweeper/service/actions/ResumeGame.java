package com.ingenio.game.minesweeper.service.actions;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.utils.ConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class ResumeGame implements GameAction {

    private final GameService gameService;

    @Override
    public GameActionEnum getIdentifier() {
        return GameActionEnum.RESUME;
    }

    @Override
    public Mono<GameInfo> run(MessageAction message) {

        return gameService.getGameById(message.getId())
                .filter(gameEntity -> gameEntity.getStatus().equals(GameStatusEnum.PAUSED.getId()))
                .map(this::toGameEntityUpdated)
                .flatMap(gameUpdated -> gameService.saveOrUpdateGame(gameUpdated))
                .map(entity -> ConverterUtils.toGameInfo(entity));
    }

    private GameEntity toGameEntityUpdated(final GameEntity gameEntity) {
        
        return gameEntity.toBuilder()
                .status(GameStatusEnum.IN_PROGRESS.getId())
                .lastUpdated(Instant.now())
                .build();
    }
}
