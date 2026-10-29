package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.PlanejamentoRequest;
import br.com.autoinject.aps.dto.PlanejamentoResponse;
import br.com.autoinject.aps.service.PlanejamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planejamentos")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    @GetMapping
    public ResponseEntity<Page<PlanejamentoResponse>> listar(
            @PageableDefault(size = 10, sort = "codigo") Pageable pageable) {
        return ResponseEntity.ok(planejamentoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanejamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planejamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanejamentoResponse> criar(@Valid @RequestBody PlanejamentoRequest request) {
        PlanejamentoResponse response = planejamentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanejamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanejamentoRequest request) {
        return ResponseEntity.ok(planejamentoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PlanejamentoResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(planejamentoService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        planejamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}