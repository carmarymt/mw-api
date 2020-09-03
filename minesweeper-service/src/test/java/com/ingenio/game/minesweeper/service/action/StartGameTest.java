package com.ingenio.game.minesweeper.service.action;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.constants.GameStatusEnum;
import com.ingenio.game.minesweeper.domain.GameBoard;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.BoardOperationException;
import com.ingenio.game.minesweeper.exception.GameActionException;
import com.ingenio.game.minesweeper.exception.UserNotFoundException;
import com.ingenio.game.minesweeper.service.BoardOperation;
import com.ingenio.game.minesweeper.service.GameService;
import com.ingenio.game.minesweeper.service.UserService;
import com.ingenio.game.minesweeper.service.UserServiceTest;
import com.ingenio.game.minesweeper.service.actions.StartGame;
import com.ingenio.game.minesweeper.utils.ConverterUtils;
import com.ingenio.game.minesweeper.utils.JsonUtils;
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
public class StartGameTest {

    public static final Instant INSTANT_DATE = Instant.now();
    public static final Long USER_ID = 1L;
    public static final Long GAME_ID = 1L;

    public static final String[][] BOARD = {{"X", "X"}, {"X", "X"}};
    public static final int[][] ORIGINAL_BOARD = {{1, 1}, {1, -1}};

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
            .user(UserServiceTest.USER_ENTITY)
            .build();

    public static final GameInfo GAME_INFO_EXPECTED = ConverterUtils.toGameInfo(GAME_ENTITY);

    public static final GameRequest GAME_REQUEST = GameRequest.builder()
            .numberRows(2)
            .numberColumns(2)
            .numberMines(1)
            .build();

    public static final GameBoard GAME_BOARD = GameBoard.builder()
            .status(GameStatusEnum.IN_PROGRESS)
            .board(BOARD)
            .originalBoard(ORIGINAL_BOARD)
            .build();

    public static final MessageAction MESSAGE_ACTION = MessageAction.builder()
            .id(USER_ID)
            .gameAction(GameActionEnum.START)
            .gameRequest(GAME_REQUEST)
            .build();

    @Mock
    private UserService userService;

    @Mock
    private GameService gameService;

    @Mock
    private BoardOperation boardOperation;

    @InjectMocks
    private StartGame underTest;

    @Test
    public void testStartGameRunSuccess() {

        when(userService.getUserById(USER_ID)).thenReturn(Mono.just(UserServiceTest.USER_ENTITY));

        when(gameService.saveOrUpdateGame(any())).thenReturn(Mono.just(GAME_ENTITY));

        when(boardOperation.init(any(GameRequest.class))).thenReturn(GAME_BOARD);

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectNext(GAME_INFO_EXPECTED)
                .verifyComplete();
    }

    @Test
    public void testStartGameRunErrorInitThrowGameActionException() {

        when(userService.getUserById(USER_ID)).thenReturn(Mono.just(UserServiceTest.USER_ENTITY));

        when(boardOperation.init(any(GameRequest.class))).thenThrow(new BoardOperationException());

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(GameActionException.class)
                .verify();
    }

    @Test
    public void testStartGameRunEmptyUserThrowUserNotFoundException() {

        when(userService.getUserById(USER_ID)).thenReturn(Mono.error(new UserNotFoundException()));

        StepVerifier.create(underTest.run(MESSAGE_ACTION))
                .expectError(UserNotFoundException.class)
                .verify();
    }
}
