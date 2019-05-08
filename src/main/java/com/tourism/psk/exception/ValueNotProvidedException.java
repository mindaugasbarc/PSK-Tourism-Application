package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValueNotProvidedException extends RuntimeException {
    public ValueNotProvidedException(String message) {
        super(message);
    }
}
