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
public class ApontamentoProducaoRequest {

    @NotBlank(message = "Ordem é obrigatória")
    @Size(max = 50, message = "Ordem deve ter no máximo 50 caracteres")
    private String ordem;

    @NotBlank(message = "Operador é obrigatório")
    @Size(max = 150, message = "Operador deve ter no máximo 150 caracteres")
    private String operador;

    @NotBlank(message = "Máquina é obrigatória")
    @Size(max = 100, message = "Máquina deve ter no máximo 100 caracteres")
    private String maquina;

    @NotNull(message = "Quantidade é obrigatória")
    @jakarta.validation.constraints.Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
    private Integer quantidade;

    @NotBlank(message = "Turno é obrigatório")
    @Size(max = 20, message = "Turno deve ter no máximo 20 caracteres")
    private String turno;

    @NotNull(message = "Data/hora é obrigatória")
    private LocalDateTime dataHora;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}