package com.ingenio.game.minesweeper.domain.dto;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class MessageAction {

    @NotNull
    Long id;

    @NotNull
    GameActionEnum gameAction;

    GameRequest gameRequest;

    UserMoveRequest userMoveRequest;
}
