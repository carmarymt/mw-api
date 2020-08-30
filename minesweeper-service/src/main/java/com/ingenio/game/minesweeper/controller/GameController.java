package com.ingenio.game.minesweeper.controller;

import com.ingenio.game.minesweeper.dto.GameInfo;
import com.ingenio.game.minesweeper.dto.request.GameRequest;
import com.ingenio.game.minesweeper.dto.request.UserMoveRequest;
import com.ingenio.game.minesweeper.utils.TestApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Minesweeper Game API", description = "Minesweeper Game endpoints")
@RequestMapping("/minesweeper/game")
public class GameController {

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
    public GameInfo createGame(@RequestParam("userId") Long userId,
                               @RequestBody final GameRequest user) {

        return TestApiUtils.gameStarted();
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
    public GameInfo getGame(@PathVariable("gameId") String gameId) {

        return TestApiUtils.gameUpdated("IN_PROGRESS");
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
    public GameInfo triggerUserMove(@PathVariable("gameId") String gameId,
                                    @RequestBody final UserMoveRequest user) {

        return TestApiUtils.gameUpdated("GAME_OVER");
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
    public GameInfo pauseGame(@PathVariable("gameId") String gameId) {

        return TestApiUtils.gameUpdated("PAUSE");
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
    public GameInfo resumeGame(@PathVariable("gameId") String gameId) {

        return TestApiUtils.gameUpdated("IN_PROGRESS");
    }

}
