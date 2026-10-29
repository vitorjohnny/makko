package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.MaquinaRequest;
import br.com.autoinject.aps.dto.MaquinaResponse;
import br.com.autoinject.aps.service.MaquinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maquinas")
@RequiredArgsConstructor
public class MaquinaController {

    private final MaquinaService maquinaService;

    @GetMapping
    public ResponseEntity<Page<MaquinaResponse>> listar(
            @PageableDefault(size = 10, sort = "codigo") Pageable pageable) {
        return ResponseEntity.ok(maquinaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaquinaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(maquinaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MaquinaResponse> criar(@Valid @RequestBody MaquinaRequest request) {
        MaquinaResponse response = maquinaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaquinaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaquinaRequest request) {
        return ResponseEntity.ok(maquinaService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MaquinaResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(maquinaService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        maquinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}