package com.ingenio.game.minesweeper.service.actions;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import reactor.core.publisher.Mono;

public interface GameAction {

    GameActionEnum getIdentifier();

    Mono<GameInfo> run(MessageAction message);
}
