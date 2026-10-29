package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.OrdemProducaoRequest;
import br.com.autoinject.aps.dto.OrdemProducaoResponse;
import br.com.autoinject.aps.service.OrdemProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-producao")
@RequiredArgsConstructor
public class OrdemProducaoController {

    private final OrdemProducaoService ordemProducaoService;

    @GetMapping
    public ResponseEntity<Page<OrdemProducaoResponse>> listar(
            @PageableDefault(size = 10, sort = "numero") Pageable pageable) {
        return ResponseEntity.ok(ordemProducaoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemProducaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordemProducaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrdemProducaoResponse> criar(@Valid @RequestBody OrdemProducaoRequest request) {
        OrdemProducaoResponse response = ordemProducaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemProducaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrdemProducaoRequest request) {
        return ResponseEntity.ok(ordemProducaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrdemProducaoResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(ordemProducaoService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        ordemProducaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}