package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class TripsNotCompatibleException extends RuntimeException {
    public TripsNotCompatibleException(String message) {
        super(message);
    }
}
