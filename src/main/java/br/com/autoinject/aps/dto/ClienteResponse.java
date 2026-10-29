package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {

    private Long id;
    private String codigo;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String cidade;
    private String estado;
    private Boolean ativo;
}