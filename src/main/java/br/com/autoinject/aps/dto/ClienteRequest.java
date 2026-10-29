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
public class ClienteRequest {

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 20, message = "Código deve ter no máximo 20 caracteres")
    private String codigo;

    @NotBlank(message = "Razão Social é obrigatória")
    @Size(max = 150, message = "Razão Social deve ter no máximo 150 caracteres")
    private String razaoSocial;

    @Size(max = 150, message = "Nome Fantasia deve ter no máximo 150 caracteres")
    private String nomeFantasia;

    @Size(max = 18, message = "CNPJ deve ter no máximo 18 caracteres")
    private String cnpj;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
    private String estado;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}