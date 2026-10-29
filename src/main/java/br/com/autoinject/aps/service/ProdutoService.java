package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ProdutoRequest;
import br.com.autoinject.aps.dto.ProdutoResponse;
import br.com.autoinject.aps.model.Cliente;
import br.com.autoinject.aps.model.Produto;
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
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(this::toResponse);
    }

    public Page<ProdutoResponse> listarPorCliente(Long clienteId, Pageable pageable) {
        return produtoRepository.findByClienteId(clienteId, pageable).map(this::toResponse);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        return toResponse(produto);
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow();

        if (produtoRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe um produto com este código");
        }

        Produto produto = toEntity(request, cliente);
        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow();

        if (produtoRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
            throw new CodigoDuplicadoException("Já existe um produto com este código");
        }

        atualizarEntidade(produto, request, cliente);
        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Transactional
    public ProdutoResponse alterarStatus(Long id, Boolean ativo) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produto.setAtivo(ativo);
        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produtoRepository.delete(produto);
    }

    private Produto toEntity(ProdutoRequest request, Cliente cliente) {
        return Produto.builder()
                .codigo(request.getCodigo())
                .descricao(request.getDescricao())
                .unidadeMedida(request.getUnidadeMedida())
                .montado(request.getMontado())
                .ativo(request.getAtivo())
                .cliente(cliente)
                .build();
    }

    private void atualizarEntidade(Produto produto, ProdutoRequest request, Cliente cliente) {
        produto.setDescricao(request.getDescricao());
        produto.setUnidadeMedida(request.getUnidadeMedida());
        produto.setMontado(request.getMontado());
        produto.setAtivo(request.getAtivo());
        produto.setCliente(cliente);
    }

    private ProdutoResponse toResponse(Produto produto) {
        ProdutoResponse.ClienteResumido clienteResumido = null;
        if (produto.getCliente() != null) {
            clienteResumido = ProdutoResponse.ClienteResumido.builder()
                    .id(produto.getCliente().getId())
                    .codigo(produto.getCliente().getCodigo())
                    .razaoSocial(produto.getCliente().getRazaoSocial())
                    .build();
        }

        return ProdutoResponse.builder()
                .id(produto.getId())
                .codigo(produto.getCodigo())
                .descricao(produto.getDescricao())
                .unidadeMedida(produto.getUnidadeMedida())
                .montado(produto.getMontado())
                .ativo(produto.getAtivo())
                .clienteId(produto.getCliente() != null ? produto.getCliente().getId() : null)
                .build();
    }
}