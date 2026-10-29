package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.ApontamentoProducaoRequest;
import br.com.autoinject.aps.dto.ApontamentoProducaoResponse;
import br.com.autoinject.aps.service.ApontamentoProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apontamentos-producao")
@RequiredArgsConstructor
public class ApontamentoProducaoController {

    private final ApontamentoProducaoService apontamentoProducaoService;

    @GetMapping
    public ResponseEntity<Page<ApontamentoProducaoResponse>> listar(
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(apontamentoProducaoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApontamentoProducaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(apontamentoProducaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApontamentoProducaoResponse> criar(@Valid @RequestBody ApontamentoProducaoRequest request) {
        ApontamentoProducaoResponse response = apontamentoProducaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApontamentoProducaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ApontamentoProducaoRequest request) {
        return ResponseEntity.ok(apontamentoProducaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApontamentoProducaoResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(apontamentoProducaoService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        apontamentoProducaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}