package com.microservice.proyect.exception;

public class UsuarioAlreadyAddedException extends RuntimeException {

    public UsuarioAlreadyAddedException(String message) {
        super(message);
    }
}
