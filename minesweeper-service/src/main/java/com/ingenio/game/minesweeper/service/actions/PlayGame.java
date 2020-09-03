package com.ingenio.game.minesweeper.service.actions;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.GameActionException;
import com.ingenio.game.minesweeper.service.BoardOperation;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.utils.ConverterUtils;
import com.ingenio.game.minesweeper.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayGame implements GameAction {

    private final GameService gameService;

    private final BoardOperation boardOperation;

    @Override
    public GameActionEnum getIdentifier() {
        return GameActionEnum.PLAY;
    }

    @Override
    public Mono<GameInfo> run(MessageAction message) {

        return gameService.getGameById(message.getId())
                .filter(gameEntity -> message.getUserMoveRequest() != null)
                .filter(gameEntity -> gameEntity.getStatus().equals(GameStatusEnum.IN_PROGRESS.getId()))
                .flatMap(gameEntity -> playGame(message, gameEntity))
                .switchIfEmpty(Mono.error(new GameActionException()));
    }

    private Mono<GameInfo> playGame(final MessageAction message, final GameEntity gameEntity) {

        return Mono.fromCallable(() -> boardOperation.move(message.getUserMoveRequest(), toGameBoard(gameEntity)))
                .map(gameBoard -> toGameEntityUpdated(gameBoard, gameEntity))
                .flatMap(gameUpdated -> gameService.saveOrUpdateGame(gameUpdated))
                .map(entity -> ConverterUtils.toGameInfo(entity))
                .onErrorResume(error -> {
                    log.error("Unable to make the action requested: {}", message, error);
                    return Mono.error(new GameActionException());
                });
    }

    private GameEntity toGameEntityUpdated(final GameBoard gameBoard,
                                           final GameEntity gameEntity) {

        Instant updated = Instant.now();

        return gameEntity.toBuilder()
                .board(JsonUtils.convertToString(gameBoard.getBoard()))
                .status(gameBoard.getStatus().getId())
                .lastUpdated(updated)
                .timeDurationSeconds((updated.getEpochSecond() - gameEntity.getLastUpdated().getEpochSecond())
                        + gameEntity.getTimeDurationSeconds())
                .build();
    }

    private GameBoard toGameBoard(final GameEntity gameEntity) {

        return GameBoard.builder()
                .status(GameStatusEnum.get(gameEntity.getStatus()))
                .numberRows(gameEntity.getNumberRows())
                .numberColumns(gameEntity.getNumberColumns())
                .board(JsonUtils.convert(gameEntity.getBoard(), String[][].class))
                .originalBoard(JsonUtils.convert(gameEntity.getOriginalBoard(), int[][].class))
                .build();
    }
}
