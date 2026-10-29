package br.com.autoinject.aps.service;

public class MoldeNaoEncontradoException extends RuntimeException {
    public MoldeNaoEncontradoException(Long id) {
        super("Molde não encontrado com id: " + id);
    }
}