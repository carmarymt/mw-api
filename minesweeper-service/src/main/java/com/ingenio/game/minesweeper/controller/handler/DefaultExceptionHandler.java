package com.ingenio.game.minesweeper.controller.handler;


import com.ingenio.game.minesweeper.error.ServiceError;
import com.ingenio.game.minesweeper.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceError> onUnexpectedException(final Throwable ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ServiceError.builder()
                        .code(1099)
                        .message(ex.getMessage())
                        .build());
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<ServiceError> onUserNotFoundException(final UserNotFoundException userNotFoundException) {

        log.error("User not found exception.", userNotFoundException);

        return getServiceErrorInternalServerError(userNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<ServiceError> onUserException(final UserException userException) {

        return getServiceErrorInternalServerError(userException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<ServiceError> onGameNotFoundException(final GameNotFoundException gameNotFoundException) {

        log.error("Game not found exception.", gameNotFoundException);

        return getServiceErrorInternalServerError(gameNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<ServiceError> onGameException(final GameException gameException) {

        return getServiceErrorInternalServerError(gameException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<ServiceError> onGameException(final BoardOperationException boardOperationException) {

        return getServiceErrorInternalServerError(boardOperationException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ServiceError> getServiceErrorInternalServerError(final BaseException baseException,
                                                                           HttpStatus status) {

        return ResponseEntity
                .status(status)
                .body(ServiceError.builder()
                        .code(baseException.getCode())
                        .message(baseException.getMessage())
                        .build());
    }
}
