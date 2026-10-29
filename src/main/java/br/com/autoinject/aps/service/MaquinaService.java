package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.MaquinaRequest;
import br.com.autoinject.aps.dto.MaquinaResponse;
import br.com.autoinject.aps.model.Maquina;
import br.com.autoinject.aps.repository.MaquinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaquinaService {

    private final MaquinaRepository maquinaRepository;

    public Page<MaquinaResponse> listar(Pageable pageable) {
        return maquinaRepository.findAll(pageable).map(this::toResponse);
    }

    public MaquinaResponse buscarPorId(Long id) {
        Maquina maquina = maquinaRepository.findById(id)
                .orElseThrow(() -> new MaquinaNaoEncontradoException(id));
        return toResponse(maquina);
    }

    @Transactional
    public MaquinaResponse criar(MaquinaRequest request) {
        if (maquinaRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe uma máquina com este código");
        }

        Maquina maquina = toEntity(request);
        Maquina salvo = maquinaRepository.save(maquina);
        return toResponse(salvo);
    }

    @Transactional
    public MaquinaResponse atualizar(Long id, MaquinaRequest request) {
        Maquina maquina = maquinaRepository.findById(id)
                .orElseThrow(() -> new MaquinaNaoEncontradoException(id));

        if (maquinaRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
            throw new CodigoDuplicadoException("Já existe uma máquina com este código");
        }

        atualizarEntidade(maquina, request);
        Maquina salvo = maquinaRepository.save(maquina);
        return toResponse(salvo);
    }

    @Transactional
    public MaquinaResponse alterarStatus(Long id, Boolean ativo) {
        Maquina maquina = maquinaRepository.findById(id)
                .orElseThrow(() -> new MaquinaNaoEncontradoException(id));

        maquina.setAtivo(ativo);
        Maquina salvo = maquinaRepository.save(maquina);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Maquina maquina = maquinaRepository.findById(id)
                .orElseThrow(() -> new MaquinaNaoEncontradoException(id));

        maquinaRepository.delete(maquina);
    }

    private Maquina toEntity(MaquinaRequest request) {
        return Maquina.builder()
                .codigo(request.getCodigo())
                .nome(request.getNome())
                .setor(request.getSetor())
                .tonelagem(request.getTonelagem())
                .capacidade(request.getCapacidade())
                .situacao(request.getSituacao())
                .maquinaFixa(request.getMaquinaFixa())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(Maquina maquina, MaquinaRequest request) {
        maquina.setNome(request.getNome());
        maquina.setSetor(request.getSetor());
        maquina.setTonelagem(request.getTonelagem());
        maquina.setCapacidade(request.getCapacidade());
        maquina.setSituacao(request.getSituacao());
        maquina.setMaquinaFixa(request.getMaquinaFixa());
        maquina.setAtivo(request.getAtivo());
    }

    private MaquinaResponse toResponse(Maquina maquina) {
        return MaquinaResponse.builder()
                .id(maquina.getId())
                .codigo(maquina.getCodigo())
                .nome(maquina.getNome())
                .setor(maquina.getSetor())
                .tonelagem(maquina.getTonelagem())
                .capacidade(maquina.getCapacidade())
                .situacao(maquina.getSituacao())
                .maquinaFixa(maquina.getMaquinaFixa())
                .ativo(maquina.getAtivo())
                .build();
    }
}