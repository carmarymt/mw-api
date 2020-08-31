package com.ingenio.game.minesweeper.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
