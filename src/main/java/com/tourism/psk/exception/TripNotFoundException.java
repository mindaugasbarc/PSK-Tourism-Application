package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Trip or GroupTrip with the provided id doesn't exist")
public class TripNotFoundException extends RuntimeException {
}
