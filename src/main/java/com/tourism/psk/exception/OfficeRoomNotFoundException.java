package com.tourism.psk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Office room with the provided id doesn't exist or doesn't belong to specified office")
public class OfficeRoomNotFoundException extends RuntimeException {
}
