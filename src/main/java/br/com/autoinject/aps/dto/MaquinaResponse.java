package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaquinaResponse {

    private Long id;
    private String codigo;
    private String nome;
    private String setor;
    private Double tonelagem;
    private Double capacidade;
    private String situacao;
    private Boolean maquinaFixa;
    private Boolean ativo;
}