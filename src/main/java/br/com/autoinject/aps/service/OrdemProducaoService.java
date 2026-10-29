package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.OrdemProducaoRequest;
import br.com.autoinject.aps.dto.OrdemProducaoResponse;
import br.com.autoinject.aps.model.OrdemProducao;
import br.com.autoinject.aps.model.Cliente;
import br.com.autoinject.aps.model.Produto;
import br.com.autoinject.aps.repository.OrdemProducaoRepository;
import br.com.autoinject.aps.repository.ClienteRepository;
import br.com.autoinject.aps.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemProducaoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public Page<OrdemProducaoResponse> listar(Pageable pageable) {
        return ordemProducaoRepository.findAll(pageable).map(this::toResponse);
    }

    public OrdemProducaoResponse buscarPorId(Long id) {
        OrdemProducao ordem = ordemProducaoRepository.findById(id)
                .orElseThrow(() -> new OrdemProducaoNaoEncontradoException(id));
        return toResponse(ordem);
    }

    @Transactional
    public OrdemProducaoResponse criar(OrdemProducaoRequest request) {
        if (ordemProducaoRepository.existsByNumero(request.getNumero())) {
            throw new CodigoDuplicadoException("Já existe uma ordem com este número");
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        OrdemProducao ordem = toEntity(request, cliente, produto);
        OrdemProducao salvo = ordemProducaoRepository.save(ordem);
        return toResponse(salvo);
    }

    @Transactional
    public OrdemProducaoResponse atualizar(Long id, OrdemProducaoRequest request) {
        OrdemProducao ordem = ordemProducaoRepository.findById(id)
                .orElseThrow(() -> new OrdemProducaoNaoEncontradoException(id));

        if (ordemProducaoRepository.existsByNumeroAndIdNot(request.getNumero(), id)) {
            throw new CodigoDuplicadoException("Já existe uma ordem com este número");
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        atualizarEntidade(ordem, request, cliente, produto);
        OrdemProducao salvo = ordemProducaoRepository.save(ordem);
        return toResponse(salvo);
    }

    @Transactional
    public OrdemProducaoResponse alterarStatus(Long id, Boolean ativo) {
        OrdemProducao ordem = ordemProducaoRepository.findById(id)
                .orElseThrow(() -> new OrdemProducaoNaoEncontradoException(id));

        ordem.setAtivo(ativo);
        OrdemProducao salvo = ordemProducaoRepository.save(ordem);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        OrdemProducao ordem = ordemProducaoRepository.findById(id)
                .orElseThrow(() -> new OrdemProducaoNaoEncontradoException(id));

        ordemProducaoRepository.delete(ordem);
    }

    private OrdemProducao toEntity(OrdemProducaoRequest request, Cliente cliente, Produto produto) {
        return OrdemProducao.builder()
                .numero(request.getNumero())
                .cliente(cliente)
                .produto(produto)
                .quantidadePlanejada(request.getQuantidadePlanejada())
                .quantidadeProduzida(request.getQuantidadeProduzida())
                .dataEntrega(request.getDataEntrega())
                .prioridade(request.getPrioridade())
                .status(request.getStatus())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(OrdemProducao ordem, OrdemProducaoRequest request,
                                   Cliente cliente, Produto produto) {
        ordem.setCliente(cliente);
        ordem.setProduto(produto);
        ordem.setQuantidadePlanejada(request.getQuantidadePlanejada());
        ordem.setQuantidadeProduzida(request.getQuantidadeProduzida());
        ordem.setDataEntrega(request.getDataEntrega());
        ordem.setPrioridade(request.getPrioridade());
        ordem.setStatus(request.getStatus());
        ordem.setAtivo(request.getAtivo());
    }

    private OrdemProducaoResponse toResponse(OrdemProducao ordem) {
        return OrdemProducaoResponse.builder()
                .id(ordem.getId())
                .numero(ordem.getNumero())
                .clienteId(ordem.getCliente() != null ? ordem.getCliente().getId() : null)
                .clienteCodigo(ordem.getCliente() != null ? ordem.getCliente().getCodigo() : null)
                .clienteRazaoSocial(ordem.getCliente() != null ? ordem.getCliente().getRazaoSocial() : null)
                .produtoId(ordem.getProduto() != null ? ordem.getProduto().getId() : null)
                .produtoCodigo(ordem.getProduto() != null ? ordem.getProduto().getCodigo() : null)
                .produtoDescricao(ordem.getProduto() != null ? ordem.getProduto().getDescricao() : null)
                .quantidadePlanejada(ordem.getQuantidadePlanejada())
                .quantidadeProduzida(ordem.getQuantidadeProduzida())
                .dataEntrega(ordem.getDataEntrega())
                .prioridade(ordem.getPrioridade())
                .status(ordem.getStatus())
                .ativo(ordem.getAtivo())
                .build();
    }
}