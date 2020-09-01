package com.ingenio.game.minesweeper.controller;

import com.ingenio.game.minesweeper.constants.GameActionEnum;
import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.dto.MessageAction;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.service.MinesweeperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Minesweeper Game API", description = "Minesweeper Game endpoints")
@RequestMapping("/minesweeper/game")
public class GameController {

    private final MinesweeperService minesweeperService;

    @Operation(
            description = "Start a new game according parameters provided: number of rows, columns, and mines",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Game could be started for given user, returns game info object.",
                            content = @Content(schema = @Schema(implementation = GameInfo.class)))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = GameRequest.class)))
    )
    @PostMapping(value = "/start",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GameInfo> createGame(@RequestParam("userId") Long userId,
                                     @RequestBody final GameRequest gameRequest) {

        return minesweeperService.process(MessageAction.builder()
                .id(userId)
                .gameAction(GameActionEnum.START)
                .gameRequest(gameRequest)
                .build());
    }

    @Operation(
            description = "Get a game status",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Game could be paused, returns game info object.",
                            content = @Content(schema = @Schema(implementation = GameInfo.class)))
            }
    )
    @GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GameInfo> getGame(@PathVariable("gameId") Long gameId) {

        return minesweeperService.process(MessageAction.builder()
                .id(gameId)
                .gameAction(GameActionEnum.STATUS)
                .build());
    }

    @Operation(
            description = "User can make a move according parameters provided: row, column, and isFlag",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Move could be updated, returns game info object.",
                            content = @Content(schema = @Schema(implementation = GameInfo.class)))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UserMoveRequest.class)))
    )
    @PostMapping(value = "/{gameId}/move",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GameInfo> triggerUserMove(@PathVariable("gameId") Long gameId,
                                          @RequestBody final UserMoveRequest userMoveRequest) {

        return minesweeperService.process(MessageAction.builder()
                .id(gameId)
                .gameAction(GameActionEnum.PLAY)
                .userMoveRequest(userMoveRequest)
                .build());
    }

    @Operation(
            description = "Pause a started game, the game should be IN_PROGRESS status",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Game could be paused, returns game info object.",
                            content = @Content(schema = @Schema(implementation = GameInfo.class)))
            }
    )
    @PostMapping(value = "/{gameId}/pause", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GameInfo> pauseGame(@PathVariable("gameId") Long gameId) {

        return minesweeperService.process(MessageAction.builder()
                .id(gameId)
                .gameAction(GameActionEnum.PAUSE)
                .build());
    }

    @Operation(
            description = "Resume a paused game, the game should be PAUSE status",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Game could be paused, returns game info object.",
                            content = @Content(schema = @Schema(implementation = GameInfo.class)))
            }
    )
    @PostMapping(value = "/{gameId}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GameInfo> resumeGame(@PathVariable("gameId") Long gameId) {

        return minesweeperService.process(MessageAction.builder()
                .id(gameId)
                .gameAction(GameActionEnum.RESUME)
                .build());
    }

}
