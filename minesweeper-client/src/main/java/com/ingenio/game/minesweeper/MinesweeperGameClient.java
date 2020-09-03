package com.ingenio.game.minesweeper;

import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.request.GameRequest;
import com.ingenio.game.minesweeper.domain.request.UserMoveRequest;
import com.ingenio.game.minesweeper.error.ServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "${minesweeper-service.client.name:minesweeper-service}",
        path = "${minesweeper-service.base.path:minesweeper}/minesweeper/game")
public interface MinesweeperGameClient {

    /**
     * Call to create a new game for given user.
     * <ul>
     * <li>HTTP 200 - Game activated {@link GameInfo} object</li>
     * <li>HTTP 404 - {@code null} Could not create a game, user not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param userId  {@code Long}
     * @param request {@code GameRequest}
     * @return Game Info {@code GameInfo} for the user or {@code ServiceError} on error
     */
    @PostMapping(value = "/start")
    GameInfo createGame(@RequestParam("userId") Long userId,
                        @RequestBody GameRequest request);

    /**
     * Call to get a game for given gameId.
     * <ul>
     * <li>HTTP 200 - Game status {@link GameInfo} object</li>
     * <li>HTTP 404 - {@code null} Game not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param gameId {@code Long}
     * @return Game Info {@code GameInfo} for the user or {@code ServiceError} on error
     */
    @GetMapping(value = "/{gameId}")
    GameInfo getGame(@PathVariable("gameId") Long gameId);


    /**
     * Call to process a move for given game.
     * <ul>
     * <li>HTTP 200 - Game with the result of the move {@link GameInfo} object</li>
     * <li>HTTP 404 - {@code null} Game not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param gameId  {@code Long}
     * @param request {@code UserMoveRequest}
     * @return Game Info {@code GameInfo} for the user or {@code ServiceError} on error
     */
    @PostMapping(value = "/{gameId}/move")
    GameInfo moveGame(@PathVariable("gameId") Long gameId,
                      @RequestBody UserMoveRequest request);

    /**
     * Call to pause a given game.
     * <ul>
     * <li>HTTP 200 - Game with the result of the pause {@link GameInfo} object</li>
     * <li>HTTP 404 - {@code null} Game not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param gameId  {@code Long}
     * @param request {@code UserMoveRequest}
     * @return Game Info {@code GameInfo} for the user or {@code ServiceError} on error
     */
    @PostMapping(value = "/{gameId}/pause")
    GameInfo pauseGame(@PathVariable("gameId") Long gameId,
                       @RequestBody UserMoveRequest request);

    /**
     * Call to resume a given game.
     * <ul>
     * <li>HTTP 200 - Game with the result of the resume {@link GameInfo} object</li>
     * <li>HTTP 404 - {@code null} Game not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param gameId  {@code Long}
     * @param request {@code UserMoveRequest}
     * @return Game Info {@code GameInfo} for the user or {@code ServiceError} on error
     */
    @PostMapping(value = "/{gameId}/resume")
    GameInfo resumeGame(@PathVariable("gameId") Long gameId,
                        @RequestBody UserMoveRequest request);

}
