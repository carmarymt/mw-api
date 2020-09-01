package com.ingenio.game.minesweeper.service.actions;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.entity.UserEntity;
import com.ingenio.game.minesweeper.exception.GameException;
import com.ingenio.game.minesweeper.service.BoardOperation;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.service.UserService;
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
public class StartGame implements GameAction {

    private final UserService userService;

    private final GameService gameService;

    private final BoardOperation boardOperation;

    @Override
    public GameActionEnum getIdentifier() {
        return GameActionEnum.START;
    }

    @Override
    public Mono<GameInfo> run(MessageAction message) {

        return userService.getUserById(message.getId())
                .flatMap(userEntity -> startGame(message, userEntity));
    }

    private Mono<GameInfo> startGame(final MessageAction message, final UserEntity userEntity) {

        return Mono.fromCallable(() -> boardOperation.init(message.getGameRequest()))
                .map(gameBoard -> toGameEntity(userEntity, gameBoard, message))
                .flatMap(gameEntity -> gameService.saveOrUpdateGame(gameEntity))
                .map(entity -> ConverterUtils.toGameInfo(entity))
                .onErrorResume(error -> {
                    log.error("Unable to start a game for message: {}", message, error);
                    return Mono.error(new GameException(error));
                });
    }

    private GameEntity toGameEntity(final UserEntity userEntity,
                                    final GameBoard gameBoard,
                                    final MessageAction messageAction) {

        Instant created = Instant.now();

        return GameEntity.builder()
                .status(GameStatusEnum.IN_PROGRESS.getId())
                .board(JsonUtils.convertToString(gameBoard.getField()))
                .numberRows(messageAction.getGameRequest().getNumberRows())
                .numberColumns(messageAction.getGameRequest().getNumberColumns())
                .numberMines(messageAction.getGameRequest().getNumberMines())
                .minesLeft(messageAction.getGameRequest().getNumberMines())
                .dateCreated(created)
                .lastUpdated(created)
                .timeDurationSeconds(0L)
                .user(userEntity)
                .build();
    }

    private GameInfo toGameInfo(final GameEntity gameEntity) {

        return GameInfo.builder()
                .gameId(gameEntity.getGameId())
                .status(GameStatusEnum.get(gameEntity.getStatus()).getStatusName())
                .board(JsonUtils.convert(gameEntity.getBoard(), String[][].class))
                .dateCreated(gameEntity.getDateCreated().getEpochSecond())
                .lastUpdated(gameEntity.getLastUpdated().getEpochSecond())
                .timeDurationSeconds(gameEntity.getTimeDurationSeconds())
                .build();
    }
}
