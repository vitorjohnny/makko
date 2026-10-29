package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.dto.ItemPlanejamentoRequest;
import br.com.autoinject.aps.dto.ItemPlanejamentoResponse;
import br.com.autoinject.aps.service.ItemPlanejamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-planejamento")
@RequiredArgsConstructor
public class ItemPlanejamentoController {

    private final ItemPlanejamentoService itemPlanejamentoService;

    @GetMapping
    public ResponseEntity<List<ItemPlanejamentoResponse>> listar(
            @RequestParam String planejamento) {
        return ResponseEntity.ok(itemPlanejamentoService.listarPorPlanejamento(planejamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPlanejamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemPlanejamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemPlanejamentoResponse> criar(@Valid @RequestBody ItemPlanejamentoRequest request) {
        ItemPlanejamentoResponse response = itemPlanejamentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPlanejamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemPlanejamentoRequest request) {
        return ResponseEntity.ok(itemPlanejamentoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ItemPlanejamentoResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        return ResponseEntity.ok(itemPlanejamentoService.alterarStatus(id, ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        itemPlanejamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}