package br.com.autoinject.aps.service;

public class ItemPlanejamentoNaoEncontradoException extends RuntimeException {
    public ItemPlanejamentoNaoEncontradoException(Long id) {
        super("Item de planejamento não encontrado com id: " + id);
    }
}