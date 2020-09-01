package com.ingenio.game.minesweeper.service;

import com.ingenio.game.minesweeper.domain.UserInfo;
import com.ingenio.game.minesweeper.domain.request.UserRequest;
import com.ingenio.game.minesweeper.entity.UserEntity;
import com.ingenio.game.minesweeper.exception.UserException;
import com.ingenio.game.minesweeper.exception.UserNotFoundException;
import com.ingenio.game.minesweeper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final Instant INSTANT_DATE = Instant.now();
    public static final Long USER_ID = 1L;

    public static final UserInfo USER_INFO = UserInfo.builder()
            .userId(USER_ID)
            .userName("user name")
            .name("name")
            .surname("surname")
            .email("email@corpo.com")
            .dateCreated(INSTANT_DATE.getEpochSecond()).build();

    public static final UserRequest USER_REQUEST = UserRequest.builder()
            .userName("user name")
            .name("name")
            .surname("surname")
            .email("email@corpo.com").build();

    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .userId(USER_ID)
            .userName("user name")
            .name("name")
            .surname("surname")
            .email("email@corpo.com")
            .dateCreated(INSTANT_DATE).build();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testCreateUserSuccess() {

        when(userRepository.saveAndFlush(any())).thenReturn(USER_ENTITY);

        StepVerifier.create(underTest.createUser(USER_REQUEST))
                .expectNext(USER_INFO)
                .verifyComplete();
    }

    @Test
    public void testCreateUSerFailureDatabaseUnreachable() {

        when(userRepository.saveAndFlush(any())).thenReturn(new Exception());

        StepVerifier.create(underTest.createUser(USER_REQUEST))
                .expectError(UserException.class)
                .verify();
    }

    @Test
    public void testGetUserByIdSuccess() {

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER_ENTITY));

        StepVerifier.create(underTest.getUserById(USER_ID))
                .expectNext(USER_ENTITY)
                .verifyComplete();
    }

    @Test
    public void testGetUserByIdEmptySuccess() {

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        StepVerifier.create(underTest.getUserById(USER_ID))
                .expectError(UserNotFoundException.class)
                .verify();
    }
}
