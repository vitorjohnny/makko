
package br.com.autoinject.aps.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPlanejamentoRequest {

    @NotNull(message = "Planejamento é obrigatório")
    private Long planejamentoId;

    @NotNull(message = "Ordem de produção é obrigatória")
    private Long ordemProducaoId;

    @NotNull(message = "Máquina é obrigatória")
    private Long maquinaId;

    private Long moldeId;

    @NotNull(message = "Início previsto é obrigatório")
    private LocalDateTime inicioPrevisto;

    @NotNull(message = "Fim previsto é obrigatório")
    private LocalDateTime fimPrevisto;

    private LocalDateTime inicioReal;

    private LocalDateTime fimReal;

    @NotNull(message = "Quantidade programada é obrigatória")
    @Min(value = 1, message = "Quantidade programada deve ser maior que zero")
    private Integer quantidadeProgramada;

    @NotNull(message = "Sequência é obrigatória")
    @Min(value = 1, message = "Sequência deve ser maior que zero")
    private Integer sequencia;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    private String status;

    @Builder.Default
    private Boolean ativo = true;

    @Builder.Default
    private Boolean riscoAtraso = false;
}
