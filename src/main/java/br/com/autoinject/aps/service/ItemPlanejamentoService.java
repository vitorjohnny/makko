package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ItemPlanejamentoRequest;
import br.com.autoinject.aps.dto.ItemPlanejamentoResponse;
import br.com.autoinject.aps.model.ItemPlanejamento;
import br.com.autoinject.aps.repository.ItemPlanejamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemPlanejamentoService {

    private final ItemPlanejamentoRepository itemPlanejamentoRepository;

    public List<ItemPlanejamentoResponse> listarPorPlanejamento(String planejamento) {
        return itemPlanejamentoRepository.findAll().stream()
                .filter(item -> planejamento.equals(item.getPlanejamento()) && item.getAtivo())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ItemPlanejamentoResponse buscarPorId(Long id) {
        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));
        return toResponse(item);
    }

    @Transactional
    public ItemPlanejamentoResponse criar(ItemPlanejamentoRequest request) {
        if (itemPlanejamentoRepository.existsByPlanejamentoAndOrdemProducaoAndMaquinaAndMolde(
                request.getPlanejamento(), request.getOrdemProducao(), request.getMaquina(), request.getMolde())) {
            throw new CodigoDuplicadoException("Já existe um item de planejamento com estes dados");
        }

        ItemPlanejamento item = toEntity(request);
        ItemPlanejamento salvo = itemPlanejamentoRepository.save(item);
        return toResponse(salvo);
    }

    @Transactional
    public ItemPlanejamentoResponse atualizar(Long id, ItemPlanejamentoRequest request) {
        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));

        if (itemPlanejamentoRepository.existsByPlanejamentoAndOrdemProducaoAndMaquinaAndMolde(
                request.getPlanejamento(), request.getOrdemProducao(), request.getMaquina(), request.getMolde())
                && !item.getId().equals(id)) {
            throw new CodigoDuplicadoException("Já existe um item de planejamento com estes dados");
        }

        atualizarEntidade(item, request);
        ItemPlanejamento salvo = itemPlanejamentoRepository.save(item);
        return toResponse(salvo);
    }

    @Transactional
    public ItemPlanejamentoResponse alterarStatus(Long id, Boolean ativo) {
        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));

        item.setAtivo(ativo);
        ItemPlanejamento salvo = itemPlanejamentoRepository.save(item);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));

        item.setAtivo(false);
        itemPlanejamentoRepository.save(item);
    }

    private ItemPlanejamento toEntity(ItemPlanejamentoRequest request) {
        return ItemPlanejamento.builder()
                .planejamento(request.getPlanejamento())
                .ordemProducao(request.getOrdemProducao())
                .maquina(request.getMaquina())
                .molde(request.getMolde())
                .inicioPrevisto(request.getInicioPrevisto())
                .fimPrevisto(request.getFimPrevisto())
                .inicioReal(request.getInicioReal())
                .fimReal(request.getFimReal())
                .quantidadeProgramada(request.getQuantidadeProgramada())
                .sequencia(request.getSequencia())
                .status(request.getStatus())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(ItemPlanejamento item, ItemPlanejamentoRequest request) {
        item.setOrdemProducao(request.getOrdemProducao());
        item.setMaquina(request.getMaquina());
        item.setMolde(request.getMolde());
        item.setInicioPrevisto(request.getInicioPrevisto());
        item.setFimPrevisto(request.getFimPrevisto());
        item.setInicioReal(request.getInicioReal());
        item.setFimReal(request.getFimReal());
        item.setQuantidadeProgramada(request.getQuantidadeProgramada());
        item.setSequencia(request.getSequencia());
        item.setStatus(request.getStatus());
        item.setAtivo(request.getAtivo());
    }

    private ItemPlanejamentoResponse toResponse(ItemPlanejamento item) {
        return ItemPlanejamentoResponse.builder()
                .id(item.getId())
                .planejamento(item.getPlanejamento())
                .ordemProducao(item.getOrdemProducao())
                .maquina(item.getMaquina())
                .molde(item.getMolde())
                .inicioPrevisto(item.getInicioPrevisto())
                .fimPrevisto(item.getFimPrevisto())
                .inicioReal(item.getInicioReal())
                .fimReal(item.getFimReal())
                .quantidadeProgramada(item.getQuantidadeProgramada())
                .sequencia(item.getSequencia())
                .status(item.getStatus())
                .ativo(item.getAtivo())
                .build();
    }
}