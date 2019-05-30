package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class ActionNotAuthorizedException extends RuntimeException {
    public ActionNotAuthorizedException() {
    }

    public ActionNotAuthorizedException(String message) {
        super(message);
    }
}
