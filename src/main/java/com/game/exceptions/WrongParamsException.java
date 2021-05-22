package com.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongParamsException extends RuntimeException{
    public WrongParamsException(String message) {
        super(message);
    }
}
