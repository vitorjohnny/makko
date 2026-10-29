package br.com.autoinject.aps.service;

public class MaquinaNaoEncontradoException extends RuntimeException {
    public MaquinaNaoEncontradoException(Long id) {
        super("Máquina não encontrada com id: " + id);
    }
}