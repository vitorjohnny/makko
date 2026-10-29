package br.com.autoinject.aps.service;

public class PlanejamentoNaoEncontradoException extends RuntimeException {
    public PlanejamentoNaoEncontradoException(Long id) {
        super("Planejamento não encontrado com id: " + id);
    }
}