package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.exception.GameException;
import com.ingenio.game.minesweeper.exception.GameNotFoundException;
import com.ingenio.game.minesweeper.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.ingenio.game.minesweeper.executor.SchedulerProvider.DB_USER_SCHEDULER;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Mono<GameEntity> getGameById(Long gameId) {

        log.info("Find game {}:", gameId);

        return Mono.fromCallable(() -> gameRepository.findById(gameId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .onErrorResume(error -> {
                    log.error("Unable to access database for gameId: {}", gameId, error);
                    return Mono.error(new GameException(error));
                })
                .switchIfEmpty(Mono.error(new GameNotFoundException()))
                .subscribeOn(DB_USER_SCHEDULER);
    }

    public Mono<GameEntity> saveOrUpdateGame(GameEntity gameEntity) {

        log.info("Save a game entity: {}", gameEntity);

        return Mono.fromCallable(() -> gameRepository.saveAndFlush(gameEntity))
                .doOnNext(game -> log.info("Data with gameId: {} persisted successfully", game.getGameId()))
                .onErrorResume(error -> {
                    log.error("Unable to persist game {}", gameEntity, error);
                    return Mono.error(new GameException(error));
                })
                .subscribeOn(DB_USER_SCHEDULER);
    }

}
