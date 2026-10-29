package br.com.autoinject.aps.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametroProducaoRequest {

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Máquina é obrigatória")
    private Long maquinaId;

    @NotNull(message = "Molde é obrigatório")
    private Long moldeId;

    @NotNull(message = "Ciclo em segundos é obrigatório")
    @jakarta.validation.constraints.Min(value = 1, message = "Ciclo deve ser maior que zero")
    private Integer cicloSegundos;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}