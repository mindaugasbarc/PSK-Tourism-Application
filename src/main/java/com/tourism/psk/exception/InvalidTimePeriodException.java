package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
        reason = "Invalid time period specified. Starting date must be earlier than ending date.")
public class InvalidTimePeriodException extends RuntimeException {
}
