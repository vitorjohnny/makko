
package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ItemPlanejamentoRequest;
import br.com.autoinject.aps.dto.ItemPlanejamentoResponse;
import br.com.autoinject.aps.model.*;
import br.com.autoinject.aps.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemPlanejamentoService {

    private final ItemPlanejamentoRepository itemPlanejamentoRepository;
    private final PlanejamentoRepository planejamentoRepository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final MaquinaRepository maquinaRepository;
    private final MoldeRepository moldeRepository;

    public List<ItemPlanejamentoResponse> listarPorPlanejamento(Long planejamentoId) {

        return itemPlanejamentoRepository
                .findByPlanejamento_IdAndAtivoTrue(planejamentoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ItemPlanejamentoResponse> listarTodos() {

        return itemPlanejamentoRepository
                .findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ItemPlanejamentoResponse buscarPorId(Long id) {

        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));

        return toResponse(item);
    }

    @Transactional
    public ItemPlanejamentoResponse criar(ItemPlanejamentoRequest request) {

        validarDuplicidade(request, null);

        ItemPlanejamento item = toEntity(request);

        ItemPlanejamento salvo = itemPlanejamentoRepository.save(item);

        return toResponse(salvo);
    }

    @Transactional
    public ItemPlanejamentoResponse atualizar(Long id, ItemPlanejamentoRequest request) {

        ItemPlanejamento item = itemPlanejamentoRepository.findById(id)
                .orElseThrow(() -> new ItemPlanejamentoNaoEncontradoException(id));

        validarDuplicidade(request, id);

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

    private void validarDuplicidade(ItemPlanejamentoRequest request, Long id) {

        Long moldeId = request.getMoldeId();

        boolean existe;

        if (id == null) {

            existe = itemPlanejamentoRepository
                    .existsByPlanejamento_IdAndOrdemProducao_IdAndMaquina_IdAndMolde_Id(
                            request.getPlanejamentoId(),
                            request.getOrdemProducaoId(),
                            request.getMaquinaId(),
                            moldeId
                    );

        } else {

            existe = itemPlanejamentoRepository
                    .existsByPlanejamento_IdAndOrdemProducao_IdAndMaquina_IdAndMolde_IdAndIdNot(
                            request.getPlanejamentoId(),
                            request.getOrdemProducaoId(),
                            request.getMaquinaId(),
                            moldeId,
                            id
                    );
        }

        if (existe) {
            throw new CodigoDuplicadoException(
                    "Já existe um item de planejamento com estes dados"
            );
        }
    }

    private ItemPlanejamento toEntity(ItemPlanejamentoRequest request) {

        Planejamento planejamento = planejamentoRepository.findById(
                request.getPlanejamentoId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Planejamento não encontrado"
        ));

        OrdemProducao ordemProducao = ordemProducaoRepository.findById(
                request.getOrdemProducaoId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Ordem de produção não encontrada"
        ));

        Maquina maquina = maquinaRepository.findById(
                request.getMaquinaId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Máquina não encontrada"
        ));

        Molde molde = null;

        if (request.getMoldeId() != null) {
            molde = moldeRepository.findById(request.getMoldeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Molde não encontrado"
                    ));
        }

        return ItemPlanejamento.builder()
                .planejamento(planejamento)
                .ordemProducao(ordemProducao)
                .maquina(maquina)
                .molde(molde)
                .inicioPrevisto(request.getInicioPrevisto())
                .fimPrevisto(request.getFimPrevisto())
                .inicioReal(request.getInicioReal())
                .fimReal(request.getFimReal())
                .quantidadeProgramada(request.getQuantidadeProgramada())
                .sequencia(request.getSequencia())
                .status(request.getStatus())
                .ativo(request.getAtivo() != null ? request.getAtivo() : true)
                .riscoAtraso(request.getRiscoAtraso() != null
                        ? request.getRiscoAtraso() : false)
                .build();
    }

    private void atualizarEntidade(
            ItemPlanejamento item,
            ItemPlanejamentoRequest request
    ) {

        Planejamento planejamento = planejamentoRepository.findById(
                request.getPlanejamentoId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Planejamento não encontrado"
        ));

        OrdemProducao ordemProducao = ordemProducaoRepository.findById(
                request.getOrdemProducaoId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Ordem de produção não encontrada"
        ));

        Maquina maquina = maquinaRepository.findById(
                request.getMaquinaId()
        ).orElseThrow(() -> new IllegalArgumentException(
                "Máquina não encontrada"
        ));

        Molde molde = null;

        if (request.getMoldeId() != null) {
            molde = moldeRepository.findById(request.getMoldeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Molde não encontrado"
                    ));
        }

        item.setPlanejamento(planejamento);
        item.setOrdemProducao(ordemProducao);
        item.setMaquina(maquina);
        item.setMolde(molde);

        item.setInicioPrevisto(request.getInicioPrevisto());
        item.setFimPrevisto(request.getFimPrevisto());
        item.setInicioReal(request.getInicioReal());
        item.setFimReal(request.getFimReal());

        item.setQuantidadeProgramada(request.getQuantidadeProgramada());
        item.setSequencia(request.getSequencia());
        item.setStatus(request.getStatus());

        item.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        item.setRiscoAtraso(request.getRiscoAtraso() != null
                ? request.getRiscoAtraso() : false);
    }

    private ItemPlanejamentoResponse toResponse(ItemPlanejamento item) {

        return ItemPlanejamentoResponse.builder()
                .id(item.getId())

                .planejamentoId(item.getPlanejamento().getId())
                .planejamento(item.getPlanejamento().getCodigo())

                .ordemProducaoId(item.getOrdemProducao().getId())
                .ordemProducao(item.getOrdemProducao().getNumero())

                .maquinaId(item.getMaquina().getId())
                .maquina(item.getMaquina().getNome())

                .moldeId(item.getMolde() != null
                        ? item.getMolde().getId() : null)

                .molde(item.getMolde() != null
                        ? item.getMolde().getCodigo() : null)

                .inicioPrevisto(item.getInicioPrevisto())
                .fimPrevisto(item.getFimPrevisto())
                .inicioReal(item.getInicioReal())
                .fimReal(item.getFimReal())

                .quantidadeProgramada(item.getQuantidadeProgramada())
                .sequencia(item.getSequencia())

                .status(item.getStatus())
                .riscoAtraso(item.getRiscoAtraso())
                .ativo(item.getAtivo())

                .build();
    }
}
