package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.dto.UserInfo;
import com.ingenio.game.minesweeper.dto.request.UserRequest;
import com.ingenio.game.minesweeper.entity.UserEntity;
import com.ingenio.game.minesweeper.exception.UserException;
import com.ingenio.game.minesweeper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

import static com.ingenio.game.minesweeper.executor.SchedulerProvider.DB_USER_SCHEDULER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserInfo> getUserById(Long userId) {

        log.info("Find user for userId {}:", userId);

        return Mono.fromCallable(() -> userRepository.findById(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::toUserInfo)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(error -> {
                    log.error("Unable to access database for userId: {}", userId, error);
                    return Mono.error(error);
                })
                .subscribeOn(DB_USER_SCHEDULER);
    }

    public Mono<UserInfo> createUser(UserRequest userRequest) {

        log.info("Save a user request: {}", userRequest);

        return Mono.fromCallable(() -> userRepository.saveAndFlush(toUserEntity(userRequest)))
                .map(this::toUserInfo)
                .doOnNext(userInfo -> log.info("Data with userId: {} persisted successfully", userInfo.getUserId()))
                .onErrorResume(error -> {
                    log.error("Unable to persist user request {}", userRequest, error);
                    return Mono.error(new UserException(error));
                })
                .subscribeOn(DB_USER_SCHEDULER);
    }

    private UserInfo toUserInfo(UserEntity userEntity) {

        return UserInfo.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .dateCreated(userEntity.getDateCreated().getEpochSecond())
                .build();
    }

    private UserEntity toUserEntity(UserRequest userRequest) {

        return UserEntity.builder()
                .userName(userRequest.getUserName())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .dateCreated(Instant.now())
                .build();
    }
}
