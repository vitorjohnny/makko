package br.com.autoinject.aps.service;

public class OrdemProducaoNaoEncontradoException extends RuntimeException {
    public OrdemProducaoNaoEncontradoException(Long id) {
        super("Ordem de produção não encontrada com id: " + id);
    }
}