package br.com.autoinject.aps.service;

import br.com.autoinject.aps.dto.ClienteRequest;
import br.com.autoinject.aps.dto.ClienteResponse;
import br.com.autoinject.aps.model.Cliente;
import br.com.autoinject.aps.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Page<ClienteResponse> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(this::toResponse);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return toResponse(cliente);
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {

        Cliente cliente = toEntity(request);
        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        atualizarEntidade(cliente, request);
        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Transactional
    public ClienteResponse alterarStatus(Long id, Boolean ativo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        cliente.setAtivo(ativo);
        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        clienteRepository.delete(cliente);
    }

    private Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .codigo(request.getCodigo())
                .razaoSocial(request.getRazaoSocial())
                .nomeFantasia(request.getNomeFantasia())
                .cnpj(request.getCnpj())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .ativo(request.getAtivo())
                .build();
    }

    private void atualizarEntidade(Cliente cliente, ClienteRequest request) {
        cliente.setRazaoSocial(request.getRazaoSocial());
        cliente.setNomeFantasia(request.getNomeFantasia());
        cliente.setCnpj(request.getCnpj());
        cliente.setCidade(request.getCidade());
        cliente.setEstado(request.getEstado());
        cliente.setAtivo(request.getAtivo());
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .codigo(cliente.getCodigo())
                .razaoSocial(cliente.getRazaoSocial())
                .nomeFantasia(cliente.getNomeFantasia())
                .cnpj(cliente.getCnpj())
                .cidade(cliente.getCidade())
                .estado(cliente.getEstado())
                .ativo(cliente.getAtivo())
                .build();
    }
}