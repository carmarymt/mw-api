package com.ingenio.game.minesweeper.controller;

import com.google.common.collect.Lists;
import com.ingenio.game.minesweeper.dto.UserGameHistory;
import com.ingenio.game.minesweeper.dto.UserInfo;
import com.ingenio.game.minesweeper.dto.request.UserRequest;
import com.ingenio.game.minesweeper.error.ServiceError;
import com.ingenio.game.minesweeper.utils.TestApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Minesweeper User API", description = "Minesweeper User endpoints")
@RequestMapping("/minesweeper/user")
@ApiResponse(responseCode = "500", description = "Internal server error",
        content = @Content(schema = @Schema(implementation = ServiceError.class)))
public class UserController {

    @Operation(
            description = "Create users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User could be created, returns user object.",
                            content = @Content(schema = @Schema(implementation = UserInfo.class)))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UserRequest.class)))
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo createUser(@RequestBody final UserRequest userRequest) {

        return TestApiUtils.createUser(userRequest);
    }

    @Operation(
            description = "Get user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User object.",
                            content = @Content(schema = @Schema(implementation = UserInfo.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo getUser(@PathVariable("userId") Long userId) {

        return TestApiUtils.createNewUser(userId);
    }

    @Operation(
            description = "Get all users.",
            responses = @ApiResponse(responseCode = "200", description = "List of User object.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserInfo.class))))
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserInfo> getAllUsers() {

        return Lists.newArrayList(
                TestApiUtils.createNewUser(1L),
                TestApiUtils.createNewUser(2L));
    }

    @Operation(
            description = "Get user game history.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game history of the user.",
                            content = @Content(schema = @Schema(implementation = UserInfo.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping(value = "/{userId}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserGameHistory getUserGameHistory(@PathVariable("userId") Long userId) {

        return UserGameHistory.builder()
                .games(Lists.newArrayList(TestApiUtils.createGameHistory()))
                .build();
    }
}
