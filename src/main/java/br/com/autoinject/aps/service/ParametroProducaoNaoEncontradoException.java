package br.com.autoinject.aps.service;

public class ParametroProducaoNaoEncontradoException extends RuntimeException {
    public ParametroProducaoNaoEncontradoException(Long id) {
        super("Parâmetro de produção não encontrado com id: " + id);
    }
}