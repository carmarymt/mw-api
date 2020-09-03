package com.ingenio.game.minesweeper;

import com.ingenio.game.minesweeper.domain.GameInfo;
import com.ingenio.game.minesweeper.domain.UserInfo;
import com.ingenio.game.minesweeper.domain.request.UserRequest;
import com.ingenio.game.minesweeper.error.ServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${minesweeper-service.client.name:minesweeper-service}",
        path = "${minesweeper-service.base.path:minesweeper}/minesweeper/user")
public interface MinesweeperUserClient {

    /**
     * Call to create a user for given userId.
     * <ul>
     * <li>HTTP 200 - Game status {@link UserInfo} object</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param userRequest {@code UserMoveRequest}
     * @return User Info {@code UserInfo} or {@code ServiceError} on error
     */
    @PostMapping(value = "")
    GameInfo createUser(@RequestBody UserRequest userRequest);

    /**
     * Call to get a user for given user.
     * <ul>
     * <li>HTTP 200 - Game status {@link UserInfo} object</li>
     * <li>HTTP 404 - {@code null} User not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param userId {@code Long}
     * @return User Info {@code UserInfo} or {@code ServiceError} on error
     */
    @GetMapping(value = "/{userId}")
    GameInfo getUser(@PathVariable("userId") Long userId);

    /**
     * Call to get a game history for given user.
     * <ul>
     * <li>HTTP 200 - Game status {@link UserInfo} object</li>
     * <li>HTTP 404 - {@code null} User not found</li>
     * <li>HTTP 500 - An error has occurred in the service (see {@link ServiceError})</li>
     * </ul>
     *
     * @param userId {@code Long}
     * @return User Info {@code UserInfo} or {@code ServiceError} on error
     */
    @GetMapping(value = "/{userId}/history")
    GameInfo getUserGameHistory(@PathVariable("userId") Long userId);
}
