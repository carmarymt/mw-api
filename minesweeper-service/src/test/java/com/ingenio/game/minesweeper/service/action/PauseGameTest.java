package com.ingenio.game.minesweeper.service.action;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.GameActionException;
import com.ingenio.game.minesweeper.exception.GameNotFoundException;
import com.ingenio.game.minesweeper.service.BoardOperation;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.service.UserServiceTest;
import com.ingenio.game.minesweeper.service.actions.PauseGame;
import com.ingenio.game.minesweeper.service.actions.PlayGame;
import com.ingenio.game.minesweeper.utils.ConverterUtils;
import com.ingenio.game.minesweeper.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PauseGameTest {

    public static final Instant INSTANT_DATE = Instant.now();
    public static final Long GAME_ID = 1L;
    public static final String[][] BOARD = {{"X", "X"}, {"X", "X"}};
    public static final String[][] ORIGINAL_BOARD = {{"1", "1"}, {"1", "M"}};

    public static final GameEntity GAME_ENTITY = GameEntity.builder()
            .gameId(GAME_ID)
            .status(GameStatusEnum.IN_PROGRESS.getId())
            .board(JsonUtils.convertToString(BOARD))
            .originalBoard(JsonUtils.convertToString(ORIGINAL_BOARD))
            .numberRows(2)
            .numberColumns(2)
            .numberMines(2)
            .dateCreated(INSTANT_DATE)
            .lastUpdated(INSTANT_DATE)
            .timeDurationSeconds(0L)
            .build();

    public static final GameEntity GAME_ENTITY_EXPECTED = GameEntity.builder()
            .gameId(GAME_ID)
            .status(GameStatusEnum.PAUSED.getId())
            .board(JsonUtils.convertToString(BOARD))
            .originalBoard(JsonUtils.convertToString(ORIGINAL_BOARD))
            .numberRows(2)
            .numberColumns(2)
            .numberMines(2)
            .dateCreated(INSTANT_DATE)
            .lastUpdated(INSTANT_DATE)
            .timeDurationSeconds(0L)
            .build();

    public static final GameEntity INVALID_GAME_ENTITY = GameEntity.builder()
            .status(GameStatusEnum.GAME_OVER.getId())
            .build();

    public static final MessageAction MESSAGE_ACTION = MessageAction.builder()
            .id(GAME_ID)
            .gameAction(GameActionEnum.PAUSE)
            .build();

    @Mock
    private GameService gameService;

    @InjectMocks
    private PauseGame underTest;

    @Test
    public void testPauseGameRunSuccess() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.just(GAME_ENTITY));

        when(gameService.saveOrUpdateGame(any())).thenReturn(Mono.just(GAME_ENTITY_EXPECTED));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectNext(ConverterUtils.toGameInfo(GAME_ENTITY_EXPECTED))
                .verifyComplete();
    }

    @Test
    public void testPauseGameRunInvalidGameStatusThrowGameActionException() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.just(INVALID_GAME_ENTITY));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameActionException.class)
                .verify();
    }

    @Test
    public void testPauseGameRunEmptyGameThrowGameNotFoundException() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.error(new GameNotFoundException()));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameNotFoundException.class)
                .verify();
    }
}
