package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ApontamentoProducaoRequest;
import br.com.autoinject.aps.dto.ApontamentoProducaoResponse;
import br.com.autoinject.aps.model.ApontamentoProducao;
import br.com.autoinject.aps.repository.ApontamentoProducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApontamentoProducaoService {

    private final ApontamentoProducaoRepository apontamentoProducaoRepository;

    public Page<ApontamentoProducaoResponse> listar(Pageable pageable) {
        return apontamentoProducaoRepository.findAll(pageable).map(this::toResponse);
    }

    public ApontamentoProducaoResponse buscarPorId(Long id) {
        ApontamentoProducao apontamento = apontamentoProducaoRepository.findById(id)
                .orElseThrow(() -> new ApontamentoProducaoNaoEncontradoException(id));
        return toResponse(apontamento);
    }

    @Transactional
    public ApontamentoProducaoResponse criar(ApontamentoProducaoRequest request) {
        if (apontamentoProducaoRepository.existsByOrdem(request.getOrdem())) {
            throw new CodigoDuplicadoException("Já existe um apontamento com esta ordem");
        }

        ApontamentoProducao apontamento = toEntity(request);
        ApontamentoProducao salvo = apontamentoProducaoRepository.save(apontamento);
        return toResponse(salvo);
    }

    @Transactional
    public ApontamentoProducaoResponse atualizar(Long id, ApontamentoProducaoRequest request) {
        ApontamentoProducao apontamento = apontamentoProducaoRepository.findById(id)
                .orElseThrow(() -> new ApontamentoProducaoNaoEncontradoException(id));

        if (apontamentoProducaoRepository.existsByOrdemAndIdNot(request.getOrdem(), id)) {
            throw new CodigoDuplicadoException("Já existe um apontamento com esta ordem");
        }

        atualizarEntidade(apontamento, request);
        ApontamentoProducao salvo = apontamentoProducaoRepository.save(apontamento);
        return toResponse(salvo);
    }

    @Transactional
    public ApontamentoProducaoResponse alterarStatus(Long id, Boolean ativo) {
        ApontamentoProducao apontamento = apontamentoProducaoRepository.findById(id)
                .orElseThrow(() -> new ApontamentoProducaoNaoEncontradoException(id));

        apontamento.setAtivo(ativo);
        ApontamentoProducao salvo = apontamentoProducaoRepository.save(apontamento);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        ApontamentoProducao apontamento = apontamentoProducaoRepository.findById(id)
                .orElseThrow(() -> new ApontamentoProducaoNaoEncontradoException(id));

        apontamentoProducaoRepository.delete(apontamento);
    }

    private ApontamentoProducao toEntity(ApontamentoProducaoRequest request) {
        return ApontamentoProducao.builder()
                .ordem(request.getOrdem())
                .operador(request.getOperador())
                .maquina(request.getMaquina())
                .quantidade(request.getQuantidade())
                .turno(request.getTurno())
                .dataHora(request.getDataHora())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(ApontamentoProducao apontamento, ApontamentoProducaoRequest request) {
        apontamento.setOperador(request.getOperador());
        apontamento.setMaquina(request.getMaquina());
        apontamento.setQuantidade(request.getQuantidade());
        apontamento.setTurno(request.getTurno());
        apontamento.setDataHora(request.getDataHora());
        apontamento.setAtivo(request.getAtivo());
    }

    private ApontamentoProducaoResponse toResponse(ApontamentoProducao apontamento) {
        return ApontamentoProducaoResponse.builder()
                .id(apontamento.getId())
                .ordem(apontamento.getOrdem())
                .operador(apontamento.getOperador())
                .maquina(apontamento.getMaquina())
                .quantidade(apontamento.getQuantidade())
                .turno(apontamento.getTurno())
                .dataHora(apontamento.getDataHora())
                .ativo(apontamento.getAtivo())
                .build();
    }
}