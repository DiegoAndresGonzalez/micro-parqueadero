package com.api.parqueadero.domain.exceptions;

public class BadUserRequestException extends RuntimeException {
    public BadUserRequestException(String message) {
        super(message);
    }
}
