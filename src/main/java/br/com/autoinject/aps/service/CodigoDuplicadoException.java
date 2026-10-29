package br.com.autoinject.aps.service;

public class CodigoDuplicadoException extends RuntimeException {
    public CodigoDuplicadoException(String message) {
        super(message);
    }
}