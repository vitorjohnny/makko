package br.com.autoinject.aps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoldeRequest {

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 20, message = "Código deve ter no máximo 20 caracteres")
    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 150, message = "Descrição deve ter entre 3 e 150 caracteres")
    private String descricao;

    @NotNull(message = "Cavidades padrão é obrigatório")
    @jakarta.validation.constraints.Min(value = 1, message = "Cavidades padrão deve ser maior que zero")
    private Integer cavidadesPadrao;

    @NotBlank(message = "Situação é obrigatória")
    @Size(max = 20, message = "Situação deve ter no máximo 20 caracteres")
    private String situacao;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}