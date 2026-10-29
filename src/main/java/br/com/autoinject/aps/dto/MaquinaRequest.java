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
public class MaquinaRequest {

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 20, message = "Código deve ter no máximo 20 caracteres")
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "Setor é obrigatório")
    @Size(max = 100, message = "Setor deve ter no máximo 100 caracteres")
    private String setor;

    @NotNull(message = "Tonelagem é obrigatória")
    private Double tonelagem;

    @NotNull(message = "Capacidade é obrigatória")
    private Double capacidade;

    @NotBlank(message = "Situação é obrigatória")
    @Size(max = 20, message = "Situação deve ter no máximo 20 caracteres")
    private String situacao;

    @NotNull(message = "Campo máquina fixa é obrigatório")
    private Boolean maquinaFixa;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}