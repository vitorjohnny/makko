package br.com.autoinject.aps.service;

public class ApontamentoProducaoNaoEncontradoException extends RuntimeException {
    public ApontamentoProducaoNaoEncontradoException(Long id) {
        super("Apontamento de produção não encontrado com id: " + id);
    }
}