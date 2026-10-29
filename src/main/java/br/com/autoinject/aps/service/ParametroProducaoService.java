package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ParametroProducaoRequest;
import br.com.autoinject.aps.dto.ParametroProducaoResponse;
import br.com.autoinject.aps.model.ParametroProducao;
import br.com.autoinject.aps.model.Produto;
import br.com.autoinject.aps.model.Maquina;
import br.com.autoinject.aps.model.Molde;
import br.com.autoinject.aps.repository.ParametroProducaoRepository;
import br.com.autoinject.aps.repository.ProdutoRepository;
import br.com.autoinject.aps.repository.MaquinaRepository;
import br.com.autoinject.aps.repository.MoldeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParametroProducaoService {

    private final ParametroProducaoRepository parametroProducaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MaquinaRepository maquinaRepository;
    private final MoldeRepository moldeRepository;

    public Page<ParametroProducaoResponse> listar(Pageable pageable) {
        return parametroProducaoRepository.findAll(pageable).map(this::toResponse);
    }

    public ParametroProducaoResponse buscarPorId(Long id) {
        ParametroProducao parametro = parametroProducaoRepository.findById(id)
                .orElseThrow(() -> new ParametroProducaoNaoEncontradoException(id));
        return toResponse(parametro);
    }

    @Transactional
    public ParametroProducaoResponse criar(ParametroProducaoRequest request) {
        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        Maquina maquina = maquinaRepository.findById(request.getMaquinaId())
                .orElseThrow(() -> new IllegalArgumentException("Máquina não encontrada"));

        Molde molde = moldeRepository.findById(request.getMoldeId())
                .orElseThrow(() -> new IllegalArgumentException("Molde não encontrado"));

        if (parametroProducaoRepository.existsByProdutoAndMaquinaAndMolde(produto, maquina, molde)) {
            throw new CodigoDuplicadoException("Já existe um parâmetro para esta combinação produto/máquina/molde");
        }

        ParametroProducao parametro = toEntity(request, produto, maquina, molde);
        ParametroProducao salvo = parametroProducaoRepository.save(parametro);
        return toResponse(salvo);
    }

    @Transactional
    public ParametroProducaoResponse atualizar(Long id, ParametroProducaoRequest request) {
        ParametroProducao parametro = parametroProducaoRepository.findById(id)
                .orElseThrow(() -> new ParametroProducaoNaoEncontradoException(id));

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        Maquina maquina = maquinaRepository.findById(request.getMaquinaId())
                .orElseThrow(() -> new IllegalArgumentException("Máquina não encontrada"));

        Molde molde = moldeRepository.findById(request.getMoldeId())
                .orElseThrow(() -> new IllegalArgumentException("Molde não encontrado"));

        if (parametroProducaoRepository.existsByProdutoAndMaquinaAndMoldeAndIdNot(produto, maquina, molde, id)) {
            throw new CodigoDuplicadoException("Já existe um parâmetro para esta combinação produto/máquina/molde");
        }

        atualizarEntidade(parametro, request, produto, maquina, molde);
        ParametroProducao salvo = parametroProducaoRepository.save(parametro);
        return toResponse(salvo);
    }

    @Transactional
    public ParametroProducaoResponse alterarStatus(Long id, Boolean ativo) {
        ParametroProducao parametro = parametroProducaoRepository.findById(id)
                .orElseThrow(() -> new ParametroProducaoNaoEncontradoException(id));

        parametro.setAtivo(ativo);
        ParametroProducao salvo = parametroProducaoRepository.save(parametro);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        ParametroProducao parametro = parametroProducaoRepository.findById(id)
                .orElseThrow(() -> new ParametroProducaoNaoEncontradoException(id));

        parametroProducaoRepository.delete(parametro);
    }

    private ParametroProducao toEntity(ParametroProducaoRequest request, Produto produto, Maquina maquina, Molde molde) {
        return ParametroProducao.builder()
                .produto(produto)
                .maquina(maquina)
                .molde(molde)
                .cicloSegundos(request.getCicloSegundos())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(ParametroProducao parametro, ParametroProducaoRequest request,
                                   Produto produto, Maquina maquina, Molde molde) {
        parametro.setProduto(produto);
        parametro.setMaquina(maquina);
        parametro.setMolde(molde);
        parametro.setCicloSegundos(request.getCicloSegundos());
        parametro.setAtivo(request.getAtivo());
    }

    private ParametroProducaoResponse toResponse(ParametroProducao parametro) {
        return ParametroProducaoResponse.builder()
                .id(parametro.getId())
                .produtoId(parametro.getProduto() != null ? parametro.getProduto().getId() : null)
                .produtoCodigo(parametro.getProduto() != null ? parametro.getProduto().getCodigo() : null)
                .produtoDescricao(parametro.getProduto() != null ? parametro.getProduto().getDescricao() : null)
                .maquinaId(parametro.getMaquina() != null ? parametro.getMaquina().getId() : null)
                .maquinaCodigo(parametro.getMaquina() != null ? parametro.getMaquina().getCodigo() : null)
                .maquinaNome(parametro.getMaquina() != null ? parametro.getMaquina().getNome() : null)
                .moldeId(parametro.getMolde() != null ? parametro.getMolde().getId() : null)
                .moldeCodigo(parametro.getMolde() != null ? parametro.getMolde().getCodigo() : null)
                .moldeDescricao(parametro.getMolde() != null ? parametro.getMolde().getDescricao() : null)
                .cicloSegundos(parametro.getCicloSegundos())
                .ativo(parametro.getAtivo())
                .build();
    }
}