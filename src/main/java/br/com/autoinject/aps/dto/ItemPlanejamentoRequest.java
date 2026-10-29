package br.com.autoinject.aps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPlanejamentoRequest {

    @NotBlank(message = "Planejamento é obrigatório")
    @Size(max = 20, message = "Planejamento deve ter no máximo 20 caracteres")
    private String planejamento;

    @NotBlank(message = "Ordem de produção é obrigatória")
    @Size(max = 20, message = "Ordem de produção deve ter no máximo 20 caracteres")
    private String ordemProducao;

    @NotBlank(message = "Máquina é obrigatória")
    @Size(max = 50, message = "Máquina deve ter no máximo 50 caracteres")
    private String maquina;

    private String molde;

    @NotNull(message = "Quantidade programada é obrigatória")
    @jakarta.validation.constraints.Min(value = 1, message = "Quantidade programada deve ser maior que zero")
    private Integer quantidadeProgramada;

    @NotNull(message = "Sequência é obrigatória")
    @jakarta.validation.constraints.Min(value = 1, message = "Sequência deve ser maior que zero")
    private Integer sequencia;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    private String status;

    @Builder.Default
    private Boolean ativo = true;

    private String inicioPrevisto;
    private String fimPrevisto;
    private String inicioReal;
    private String fimReal;
}