package br.com.autoinject.aps.controller;

import br.com.autoinject.aps.service.ClienteNaoEncontradoException;
import br.com.autoinject.aps.service.ApontamentoProducaoNaoEncontradoException;
import br.com.autoinject.aps.service.CodigoDuplicadoException;
import br.com.autoinject.aps.service.ItemPlanejamentoNaoEncontradoException;
import br.com.autoinject.aps.service.MaquinaNaoEncontradoException;
import br.com.autoinject.aps.service.MoldeNaoEncontradoException;
import br.com.autoinject.aps.service.OrdemProducaoNaoEncontradoException;
import br.com.autoinject.aps.service.ParametroProducaoNaoEncontradoException;
import br.com.autoinject.aps.service.PlanejamentoNaoEncontradoException;
import br.com.autoinject.aps.service.ProdutoNaoEncontradoException;
import br.com.autoinject.aps.service.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MaquinaNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleMaquinaNaoEncontrado(MaquinaNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MoldeNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleMoldeNaoEncontrado(MoldeNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ApontamentoProducaoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleApontamentoProducaoNaoEncontrado(ApontamentoProducaoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

        @ExceptionHandler(ParametroProducaoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleParametroProducaoNaoEncontrado(ParametroProducaoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PlanejamentoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePlanejamentoNaoEncontrado(PlanejamentoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OrdemProducaoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleOrdemProducaoNaoEncontrado(OrdemProducaoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ItemPlanejamentoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleItemPlanejamentoNaoEncontrado(ItemPlanejamentoNaoEncontradoException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CodigoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleCodigoDuplicado(CodigoDuplicadoException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        if (message != null && message.contains("unique") || message != null && message.contains("duplicate")) {
            return buildErrorResponse(HttpStatus.CONFLICT, "Registro duplicado: já existe um registro com estes dados");
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de integridade dos dados");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        return ResponseEntity.status(status).body(error);
    }
}