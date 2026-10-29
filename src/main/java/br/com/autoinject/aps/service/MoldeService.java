package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.MoldeRequest;
import br.com.autoinject.aps.dto.MoldeResponse;
import br.com.autoinject.aps.model.Molde;
import br.com.autoinject.aps.repository.MoldeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoldeService {

    private final MoldeRepository moldeRepository;

    public Page<MoldeResponse> listar(Pageable pageable) {
        return moldeRepository.findAll(pageable).map(this::toResponse);
    }

    public MoldeResponse buscarPorId(Long id) {
        Molde molde = moldeRepository.findById(id)
                .orElseThrow(() -> new MoldeNaoEncontradoException(id));
        return toResponse(molde);
    }

    @Transactional
    public MoldeResponse criar(MoldeRequest request) {
        if (moldeRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe um molde com este código");
        }

        Molde molde = toEntity(request);
        Molde salvo = moldeRepository.save(molde);
        return toResponse(salvo);
    }

    @Transactional
    public MoldeResponse atualizar(Long id, MoldeRequest request) {
        Molde molde = moldeRepository.findById(id)
                .orElseThrow(() -> new MoldeNaoEncontradoException(id));

        if (moldeRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
            throw new CodigoDuplicadoException("Já existe um molde com este código");
        }

        atualizarEntidade(molde, request);
        Molde salvo = moldeRepository.save(molde);
        return toResponse(salvo);
    }

    @Transactional
    public MoldeResponse alterarStatus(Long id, Boolean ativo) {
        Molde molde = moldeRepository.findById(id)
                .orElseThrow(() -> new MoldeNaoEncontradoException(id));

        molde.setAtivo(ativo);
        Molde salvo = moldeRepository.save(molde);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Molde molde = moldeRepository.findById(id)
                .orElseThrow(() -> new MoldeNaoEncontradoException(id));

        moldeRepository.delete(molde);
    }

    private Molde toEntity(MoldeRequest request) {
        return Molde.builder()
                .codigo(request.getCodigo())
                .descricao(request.getDescricao())
                .cavidadesPadrao(request.getCavidadesPadrao())
                .situacao(request.getSituacao())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(Molde molde, MoldeRequest request) {
        molde.setDescricao(request.getDescricao());
        molde.setCavidadesPadrao(request.getCavidadesPadrao());
        molde.setSituacao(request.getSituacao());
        molde.setAtivo(request.getAtivo());
    }

    private MoldeResponse toResponse(Molde molde) {
        return MoldeResponse.builder()
                .id(molde.getId())
                .codigo(molde.getCodigo())
                .descricao(molde.getDescricao())
                .cavidadesPadrao(molde.getCavidadesPadrao())
                .situacao(molde.getSituacao())
                .ativo(molde.getAtivo())
                .build();
    }
}