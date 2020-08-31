package com.ingenio.game.minesweeper.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseException extends RuntimeException {

    private Integer code;

    private String message;

    /**
     * Constructor with cause.
     * Generates an unexpected error.
     */
    protected BaseException(Throwable cause, Integer code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    /**
     * Constructor with cause.
     * Generates an unexpected error.
     */
    protected BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
