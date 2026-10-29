package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.PlanejamentoRequest;
import br.com.autoinject.aps.dto.PlanejamentoResponse;
import br.com.autoinject.aps.model.Planejamento;
import br.com.autoinject.aps.repository.PlanejamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;

    public Page<PlanejamentoResponse> listar(Pageable pageable) {
        return planejamentoRepository.findAll(pageable).map(this::toResponse);
    }

    public PlanejamentoResponse buscarPorId(Long id) {
        Planejamento planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new PlanejamentoNaoEncontradoException(id));
        return toResponse(planejamento);
    }

    @Transactional
    public PlanejamentoResponse criar(PlanejamentoRequest request) {
        if (planejamentoRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe um planejamento com este código");
        }

        Planejamento planejamento = toEntity(request);
        Planejamento salvo = planejamentoRepository.save(planejamento);
        return toResponse(salvo);
    }

    @Transactional
    public PlanejamentoResponse atualizar(Long id, PlanejamentoRequest request) {
        Planejamento planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new PlanejamentoNaoEncontradoException(id));

        if (planejamentoRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
            throw new CodigoDuplicadoException("Já existe um planejamento com este código");
        }

        atualizarEntidade(planejamento, request);
        Planejamento salvo = planejamentoRepository.save(planejamento);
        return toResponse(salvo);
    }

    @Transactional
    public PlanejamentoResponse alterarStatus(Long id, Boolean ativo) {
        Planejamento planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new PlanejamentoNaoEncontradoException(id));

        planejamento.setAtivo(ativo);
        Planejamento salvo = planejamentoRepository.save(planejamento);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Planejamento planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new PlanejamentoNaoEncontradoException(id));

        planejamentoRepository.delete(planejamento);
    }

    private Planejamento toEntity(PlanejamentoRequest request) {
        return Planejamento.builder()
                .codigo(request.getCodigo())
                .descricao(request.getDescricao())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(Planejamento planejamento, PlanejamentoRequest request) {
        planejamento.setDescricao(request.getDescricao());
        planejamento.setDataInicio(request.getDataInicio());
        planejamento.setDataFim(request.getDataFim());
        planejamento.setAtivo(request.getAtivo());
    }

    private PlanejamentoResponse toResponse(Planejamento planejamento) {
        return PlanejamentoResponse.builder()
                .id(planejamento.getId())
                .codigo(planejamento.getCodigo())
                .descricao(planejamento.getDescricao())
                .dataInicio(planejamento.getDataInicio())
                .dataFim(planejamento.getDataFim())
                .ativo(planejamento.getAtivo())
                .build();
    }
}