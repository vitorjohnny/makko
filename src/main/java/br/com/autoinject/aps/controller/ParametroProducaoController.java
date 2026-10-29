package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.ParametroProducaoRequest;
import br.com.autoinject.aps.dto.ParametroProducaoResponse;
import br.com.autoinject.aps.service.ParametroProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parametros-producao")
@RequiredArgsConstructor
public class ParametroProducaoController {

    private final ParametroProducaoService parametroProducaoService;

    @GetMapping
    public ResponseEntity<Page<ParametroProducaoResponse>> listar(
            @PageableDefault(size = 10, sort = "produtoId") Pageable pageable) {
        return ResponseEntity.ok(parametroProducaoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParametroProducaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(parametroProducaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ParametroProducaoResponse> criar(@Valid @RequestBody ParametroProducaoRequest request) {
        ParametroProducaoResponse response = parametroProducaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParametroProducaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ParametroProducaoRequest request) {
        return ResponseEntity.ok(parametroProducaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParametroProducaoResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(parametroProducaoService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        parametroProducaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}