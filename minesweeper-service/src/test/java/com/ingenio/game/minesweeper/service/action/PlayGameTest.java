package com.ingenio.game.minesweeper.service.action;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.GameActionException;
import com.ingenio.game.minesweeper.exception.GameNotFoundException;
import com.ingenio.game.minesweeper.service.BoardOperation;
import com.ingenio.game.minesweeper.service.GameService;
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
public class PlayGameTest {

    public static final Instant INSTANT_DATE = Instant.now();
    public static final Long GAME_ID = 1L;
    public static final String[][] BOARD = {{"X", "X"}, {"X", "X"}};
    public static final String[][] MOVE_BOARD = {{"1", "1"}, {"1", "M"}};

    public static final GameEntity GAME_ENTITY = GameEntity.builder()
            .gameId(GAME_ID)
            .status(GameStatusEnum.IN_PROGRESS.getId())
            .numberRows(2)
            .numberColumns(2)
            .board(JsonUtils.convertToString(BOARD))
            .originalBoard("[[1,1],[1,-1]]")
            .dateCreated(INSTANT_DATE)
            .lastUpdated(INSTANT_DATE)
            .timeDurationSeconds(300L)
            .build();

    public static final GameEntity GAME_ENTITY_EXPECTED = GameEntity.builder()
            .gameId(GAME_ID)
            .status(1)
            .board(JsonUtils.convertToString(MOVE_BOARD))
            .dateCreated(INSTANT_DATE)
            .lastUpdated(INSTANT_DATE)
            .timeDurationSeconds(300L)
            .build();

    public static final GameEntity INVALID_GAME_ENTITY = GameEntity.builder()
            .status(GameStatusEnum.PAUSED.getId())
            .build();

    public static final UserMoveRequest USER_MOVE_REQUEST = UserMoveRequest.builder()
            .row(1)
            .column(1)
            .isFlag(false)
            .build();

    public static final GameBoard GAME_BOARD = GameBoard.builder()
            .status(GameStatusEnum.GAME_OVER)
            .board(MOVE_BOARD)
            .build();

    public static final MessageAction MESSAGE_ACTION = MessageAction.builder()
            .id(GAME_ID)
            .gameAction(GameActionEnum.PLAY)
            .userMoveRequest(USER_MOVE_REQUEST)
            .build();

    @Mock
    private GameService gameService;

    @Mock
    private BoardOperation boardOperation;

    @InjectMocks
    private PlayGame underTest;

    @BeforeEach
    public void setup() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.just(GAME_ENTITY));
    }

    @Test
    public void testPlayGameRunSuccess() {

        when(gameService.saveOrUpdateGame(any())).thenReturn(Mono.just(GAME_ENTITY_EXPECTED));

        when(boardOperation.move(any(UserMoveRequest.class), any(GameBoard.class))).thenReturn(GAME_BOARD);

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectNext(ConverterUtils.toGameInfo(GAME_ENTITY_EXPECTED))
                .verifyComplete();
    }

    @Test
    public void testPlayGameRunErrorMoveThrowGameActionException() {

        when(boardOperation.move(any(UserMoveRequest.class), any(GameBoard.class))).thenThrow(new RuntimeException());

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameActionException.class)
                .verify();
    }

    @Test
    public void testPlayGameRunInvalidGameStatusThrowGameActionException() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.just(INVALID_GAME_ENTITY));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameActionException.class)
                .verify();
    }

    @Test
    public void testPlayGameRunEmptyGameThrowGameNotFoundException() {

        when(gameService.getGameById(GAME_ID)).thenReturn(Mono.error(new GameNotFoundException()));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameNotFoundException.class)
                .verify();
    }
}
