package com.ingenio.game.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GameInfo {

    String gameId;

    String status;

    String[][] board;

    Long dateCreated;

    Long lastUpdated;

    Long timeDurationSeconds;
}
