package com.api.parqueadero.infrastructure.exceptions;

public class UserAlreadyAssociated extends RuntimeException {
    public UserAlreadyAssociated(String message) {super(message);}
}
