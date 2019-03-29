package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User already exists")
public class UserAlreadyExistsException extends RuntimeException {
}
