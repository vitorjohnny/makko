package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.MoldeRequest;
import br.com.autoinject.aps.dto.MoldeResponse;
import br.com.autoinject.aps.service.MoldeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moldes")
@RequiredArgsConstructor
public class MoldeController {

    private final MoldeService moldeService;

    @GetMapping
    public ResponseEntity<Page<MoldeResponse>> listar(
            @PageableDefault(size = 10, sort = "codigo") Pageable pageable) {
        return ResponseEntity.ok(moldeService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoldeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(moldeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MoldeResponse> criar(@Valid @RequestBody MoldeRequest request) {
        MoldeResponse response = moldeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoldeResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MoldeRequest request) {
        return ResponseEntity.ok(moldeService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MoldeResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(moldeService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        moldeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}