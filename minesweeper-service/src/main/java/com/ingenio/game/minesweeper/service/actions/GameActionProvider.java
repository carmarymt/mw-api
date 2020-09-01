package com.ingenio.game.minesweeper.service.actions;

import com.google.common.collect.ImmutableMap;
import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameActionProvider {

    private final Map<GameActionEnum, GameAction> gameOperations;

    public GameActionProvider(final List<GameAction> gameActions) {

        this.gameOperations = ImmutableMap.copyOf(gameActions.stream()
                .collect(Collectors.toMap(
                        GameAction::getIdentifier,
                        Function.identity())));
    }

    public GameAction getGameAction(final GameActionEnum operation) {

        return Optional.ofNullable(gameOperations.get(operation))
                .orElseThrow(() -> {
                    log.error("Invalid operation: " + operation);
                    return new UserNotFoundException();
                });
    }
}
