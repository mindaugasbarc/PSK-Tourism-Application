package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "values for email, fullname and role cannot be null or empty")
public class ValueNotProvidedException extends RuntimeException {
}
