package com.ingenio.game.minesweeper.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserGameHistory {

    List<GameInfo> games;
}
