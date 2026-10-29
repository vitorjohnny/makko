package br.com.autoinject.aps.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdemProducaoRequest {

    @NotBlank(message = "Número da ordem é obrigatório")
    @Size(max = 50, message = "Número deve ter no máximo 50 caracteres")
    private String numero;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade planejada é obrigatória")
    @jakarta.validation.constraints.Min(value = 1, message = "Quantidade planejada deve ser maior que zero")
    private Integer quantidadePlanejada;

    @jakarta.validation.constraints.Min(value = 0, message = "Quantidade produzida não pode ser negativa")
    private Integer quantidadeProduzida = 0;

    @NotNull(message = "Data de entrega é obrigatória")
    private LocalDateTime dataEntrega;

    @NotNull(message = "Prioridade é obrigatória")
    @jakarta.validation.constraints.Min(value = 1, message = "Prioridade deve ser maior que zero")
    private Integer prioridade;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    private String status;

    private String arquivoOrigem;

    private LocalDateTime dataImportacao;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}