package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.service.actions.GameActionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinesweeperService {

    private final GameActionProvider gameActionProvider;

    public Mono<GameInfo> process(final MessageAction message) {

        log.info("Running minesweeper service with message: {}", message);

        return Mono.fromCallable(() -> gameActionProvider.getGameAction(message.getGameAction()))
                .flatMap(gameAction -> gameAction.run(message));
    }
}
