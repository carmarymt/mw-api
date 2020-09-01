package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.GameException;
import com.ingenio.game.minesweeper.exception.GameNotFoundException;
import com.ingenio.game.minesweeper.repository.GameRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    public static final Instant INSTANT_DATE = Instant.now();
    public static final Long GAME_ID = 1L;

    public static final GameEntity GAME_ENTITY = GameEntity.builder()
            .gameId(GAME_ID)
            .status(1)
            .board("[[BOARD]]")
            .dateCreated(INSTANT_DATE)
            .lastUpdated(INSTANT_DATE)
            .timeDurationSeconds(300L)
            .build();

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService underTest;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testCreateGameSuccess() {

        when(gameRepository.saveAndFlush(any())).thenReturn(GAME_ENTITY);

        StepVerifier.create(underTest.saveOrUpdateGame(GAME_ENTITY))
                .expectNext(GAME_ENTITY)
                .verifyComplete();
    }

    @Test
    public void testSaveGameFailureDatabaseUnreachable() {

        when(gameRepository.saveAndFlush(any())).thenReturn(new Exception());

        StepVerifier.create(underTest.saveOrUpdateGame(GAME_ENTITY))
                .expectError(GameException.class)
                .verify();
    }

    @Test
    public void testGetGameByIdSuccess() {

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(GAME_ENTITY));

        StepVerifier.create(underTest.getGameById(GAME_ID))
                .expectNext(GAME_ENTITY)
                .verifyComplete();
    }

    @Test
    public void testGetGameByIdEmptyThrowGameNotFoundException() {

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());

        StepVerifier.create(underTest.getGameById(GAME_ID))
                .expectError(GameNotFoundException.class)
                .verify();
    }

    @Test
    public void testAllGameByIdSuccess() {

        when(gameRepository.findGamesByUser(UserServiceTest.USER_ENTITY)).thenReturn(Lists.newArrayList(GAME_ENTITY));

        StepVerifier.create(underTest.getAllGameByUser(UserServiceTest.USER_ENTITY))
                .expectNext(GAME_ENTITY)
                .verifyComplete();
    }


}
